package com.justep.javaExt.dao;


import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.findFieldName;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.findIdName;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.findTableName;
import static com.justep.javaExt.dao.poAnnotation.PoAnnoUtils.listFieldsWithoutId;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 增删改查和获取id常用sql通用生成工具,对于同一个vo类,sql语句会缓存
 */
public final class SqlMarker {

	private static Map<String, String> sqlCaches;

	private static class InstanceHolder {
		private static SqlMarker instance = new SqlMarker();
	}

	private SqlMarker() {
		sqlCaches = new ConcurrentHashMap<String, String>();
	}

	public static SqlMarker getInstance() {
		return InstanceHolder.instance;
	}

	/**
	 * 获取插入vo对象的预编译sql语句,无论如何,id总是排在第一个
	 * 
	 * @param c
	 *            vo类,依照注解进行拼接
	 * @return sql语句
	 */
	public String insert(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#insert";
		String sql = sqlCaches.get(key);
		if (sql != null) {
			return sql;
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer values = new StringBuffer();
		sb.append("insert into ");
		sb.append(findTableName(c));
		sb.append("(");
		sb.append(findIdName(c)).append(",");
		values.append("?,");
		for (Field f : listFieldsWithoutId(c)) {
			sb.append(findFieldName(f)).append(",");
			values.append("?,");
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		if ((index = values.lastIndexOf(",")) == values.length() - 1) {
			values.deleteCharAt(index);
		}
		sb.append(") values (");
		sb.append(values);
		sb.append(")");
		sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 更新vo所有标识字段的预编译语句,无论如何,id总是排在最后一个.
	 * 
	 * @param c
	 *            vo类型
	 * @return sql语句
	 */
	public String update(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#update";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(findTableName(c));
		sb.append(" set ");
		for (Field f : listFieldsWithoutId(c)) {
			sb.append(findFieldName(f)).append("=?,");
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		sb.append(" where ");
		sb.append(findIdName(c));
		sb.append("=?");
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 删除vo的预编译语句.只有一个参数,就是id
	 * 
	 * @param c
	 *            vo类型
	 * @return sql语句
	 */
	public String delete(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#delete";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(findTableName(c));
		sb.append(" where ");
		sb.append(findIdName(c)).append("=?");
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 获取一个vo的预编译语句.只有一个参数,id
	 * 
	 * @param c
	 *            vo类型
	 * @return sql语句
	 */
	public String fetch(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#fetch";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(findTableName(c));
		sb.append(" where ");
		sb.append(findIdName(c)).append("=?");
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 查询所有的vo的预编译语句.会列出数据表中所有记录.没有参数.
	 * 
	 * @param c
	 *            vo类型
	 * @return sql语句
	 */
	public String query(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#query";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(findTableName(c));
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 根据传入的vo类型生成id的sql,sql中id的列名为id,步长为1
	 * 
	 * @param c
	 *            vo类
	 * @return sql
	 */
	public String nextId(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#nextId";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select (max(");
		sb.append(findIdName(c));
		sb.append(") + 1) as id");
		sb.append(" from ");
		sb.append(findTableName(c));
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	
}
