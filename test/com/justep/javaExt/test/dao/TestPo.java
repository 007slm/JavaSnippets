package com.justep.javaExt.test.dao;


import java.lang.reflect.Field;
import java.util.Date;

import com.justep.javaExt.dao.SqlMarker;
import com.justep.javaExt.dao.poAnnotation.DataSource;
import com.justep.javaExt.dao.poAnnotation.Id;
import com.justep.javaExt.dao.poAnnotation.None;
import com.justep.javaExt.dao.poAnnotation.Table;

@Table(name = "demo")
@DataSource("oracle.jdbc.driver.OracleDriver;jdbc:oracle:thin:@192.168.0.114:1521:justep;x5system1690;justep")
public class TestPo {

	@Id("id")
	private String biaoShi;

	private int count;

	private char flag;

	private Date time;
	@None
	private String other;

	public String getBiaoShi() {
		return biaoShi;
	}

	public void setBiaoShi(String biaoShi) {
		this.biaoShi = biaoShi;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public static void main(String[] args) {
		System.out.println(SqlMarker.getInstance().insert(TestPo.class));
		System.out.println(SqlMarker.getInstance().update(TestPo.class));
		System.out.println(SqlMarker.getInstance().delete(TestPo.class));
		System.out.println(SqlMarker.getInstance().fetch(TestPo.class));
		System.out.println(SqlMarker.getInstance().query(TestPo.class));
		TestPo vo = new TestPo();
		try {
			Field f = vo.getClass().getDeclaredField("flag");
			f.setAccessible(true);
			System.out.println(f.get(vo));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
