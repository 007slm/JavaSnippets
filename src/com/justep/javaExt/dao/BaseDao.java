package com.justep.javaExt.dao;



import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.findFieldName;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.findTableId;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isBoolean;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isByte;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isChar;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isDate;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isDouble;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isFloat;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isInt;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isLong;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.isString;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.listFieldsWithoutId;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.listTableFields;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.justep.javaExt.trans.Transaction;


/**
 * dao的基本实现
 * 
 */
public abstract class BaseDao<T> {


	private SqlMarker generator = SqlMarker.getInstance();
	
	private Transaction tran;
	private String jndiName;
	public BaseDao(Transaction tran,String jndiName){
		this.jndiName=jndiName;
		this.tran=tran;
	}
	
	/**
	 * 获取此dao操作的vo的类型
	 * 
	 * @return 类型
	 */
	public abstract Class<T> getPoType();

	

	/**
	 * 插入vo的所有字段.如果vo的id有值并且值大于0,则使用此值插入,否则生成一个id插入.
	 * 此方法不依赖于数据库id的自增,使用max(id)+1的方式生成id.所以此方法会开启一个事务.如果失败,会放弃添加并回滚.
	 * 
	 * @param t
	 *            vo
	 */
	public void insert(T t) throws Exception{
		String sql = this.generator.insert(t.getClass());
		Connection con = tran.getConnection(jndiName);
		
			int id = this.getId(t);
			if (id <= 0) {
				String next = this.generator.nextId(t.getClass());
				ResultSet rs = con.createStatement().executeQuery(next);
				if (rs.next()) {
					Object o = rs.getObject(1);
					if (o == null) {// 可能还没有记录
						id = 1;
					} else {
						id = Integer.valueOf(String.valueOf(o));
					}
				}
			}
			if (id <= 0) {
				return;
			}
			PreparedStatement ps = con.prepareStatement(sql);
			Field[] fields = listFieldsWithoutId(t.getClass());
			int index = 1;
			ps.setInt(index++, id);
			for (Field f : fields) {
				this.setParamter(ps, index++, t, f);
			}
			ps.executeUpdate();
			
	}

	/**
	 * 更新vo,使用vo的id作为条件,更新vo的所有字段.<br>
	 * 慎用此方法,有可能更新到你不愿意更新的字段
	 * 
	 * @param t
	 *            vo
	 */
	public void update(T t) throws Exception{
		String sql = this.generator.update(t.getClass());
		Connection con = tran.getConnection(jndiName);
			PreparedStatement ps = con.prepareStatement(sql);
			int index = 1;
			Field[] fields = listFieldsWithoutId(t.getClass());
			for (Field f : fields) {
				this.setParamter(ps, index++, t, f);
			}
			ps.setInt(index, this.getId(t));
			ps.executeUpdate();
	}

	/**
	 * 删除一个vo
	 * 
	 * @param o
	 *            vo或者id
	 */
	public void delete(Object o) throws Exception{
		if (o == null) {
			return;
		}
		Connection con = tran.getConnection(jndiName);
		int id = this.getVOId(o);
		if (id > 0) {
			String sql = this.generator.delete(this.getPoType());

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, id);
			ps.executeUpdate();
		} else {
			new RuntimeException("传入的参数:" + o + ",无法正确得到id!");
		}
		
	}

	/**
	 * 获取一个vo
	 * 
	 * @param id
	 *            vo的id
	 * @return vo或者null
	 */
	public T fetch(Object id) throws Exception{
		Class<T> type = this.getPoType();
		String sql = this.generator.fetch(type);
		Connection con = tran.getConnection(jndiName);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setObject(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			T t = type.cast(type.newInstance());
			this.fillVO(rs, t);
			return t;
		}
		return null;
	}

	/**
	 * 查询所有的vo,没有考虑分页
	 * 
	 * @return vo的list,没有任何可用值返回emptylist
	 */
	public List<T> query(Cnd cnd) throws Exception{
		Class<T> type = this.getPoType();
		String sql = this.generator.query(type);
		if (cnd != null) {
			sql += cnd.toString();
		}
		Connection con = tran.getConnection(jndiName);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<T> list = new ArrayList<T>();
		while (rs.next()) {
			T t = type.cast(type.newInstance());
			this.fillVO(rs, t);
			list.add(t);
		}
		return list;
	}

	/**
	 * 判断字段类型,如果是int和string,返回int值.如果是vo,返回id的int值.
	 * 
	 * @param o
	 *            vo或者id
	 * @return id的int值或者0
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private int getVOId(Object o) throws IllegalArgumentException,
			IllegalAccessException {
		if (o == null) {
			return 0;
		}
		if (this.getPoType().isAssignableFrom(o.getClass())) {
			return this.getId(this.getPoType().cast(o));
		} else {
			String s = String.valueOf(o);
			try {
				return Integer.valueOf(s);
			} catch (Exception e) {
				return 0;
			}
		}
	}

	/**
	 * 获取vo的id字段的值
	 * 
	 * @param t
	 *            vo
	 * @return id值或者0
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private int getId(T t) throws IllegalArgumentException,
			IllegalAccessException {
		Field id = findTableId(t.getClass());
		if (!id.isAccessible()) {
			id.setAccessible(true);
		}
		if (id.getType() == String.class) {
			String v = (String) id.get(t);
			if (v == null) {
				return 0;
			}
			try {
				return Integer.valueOf(v);
			} catch (Exception e) {
				return 0;
			}
		}
		return id.getInt(t);
	}

	/**
	 * 把查询结果填充到vo中
	 * 
	 * @param rs
	 *            查询结果,由调用负责游标
	 * @param t
	 *            vo
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	private void fillVO(ResultSet rs, T t) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		for (Field f : listTableFields(t.getClass())) {
			String name = findFieldName(f);
			Object object = rs.getObject(name);
			if (object != null) {
				if (!f.isAccessible()) {
					f.setAccessible(true);
				}
				if (isBoolean(f)) {
					f.setBoolean(t, rs.getBoolean(name));
				} else if (isInt(f)) {
					f.setInt(t, rs.getInt(name));
				} else if (isLong(f)) {
					f.setLong(t, rs.getLong(name));
				} else if (isString(f)) {
					f.set(t, rs.getString(name));
				} else if (isDate(f)) {
					f.set(t, rs.getTimestamp(name));
				} else if (isByte(f)) {
					f.setByte(t, rs.getByte(name));
				} else if (isChar(f)) {
					f.setChar(t, rs.getString(name).charAt(0));
				} else if (isDouble(f)) {
					f.setDouble(t, rs.getDouble(name));
				} else if (isFloat(f)) {
					f.setFloat(t, rs.getFloat(name));
				} else {
					f.set(t, object);
				}
			}
		}
	}

	/**
	 * 设置预编译参数
	 * 
	 * @param ps
	 *            预编译
	 * @param index
	 *            序号
	 * @param t
	 *            vo模型
	 * @param f
	 *            字段
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @throws IllegalAccessException
	 */
	private void setParamter(PreparedStatement ps, int index, T t, Field f)
			throws IllegalArgumentException, SQLException,
			IllegalAccessException {
		if (!f.isAccessible()) {
			f.setAccessible(true);
		}
		if (isBoolean(f)) {
			ps.setBoolean(index, f.getBoolean(t));
		} else if (isInt(f)) {
			ps.setInt(index, f.getInt(t));
		} else if (isLong(f)) {
			ps.setLong(index, f.getLong(t));
		} else if (isString(f)) {
			ps.setString(index, (String) f.get(t));
		} else if (isDate(f)) {
			Object o = f.get(t);
			if (o == null) {
				ps.setDate(index, null);
			} else {
				ps.setTimestamp(index, new java.sql.Timestamp(((Date) o)
						.getTime()));
			}
		} else if (isByte(f)) {
			ps.setByte(index, f.getByte(t));
		} else if (isChar(f)) {
			ps.setInt(index, f.getChar(t));
		} else if (isDouble(f)) {
			ps.setDouble(index, f.getDouble(t));
		} else if (isFloat(f)) {
			ps.setFloat(index, f.getFloat(t));
		} else {
			ps.setObject(index, f.get(t));
		}
	}
}
