package com.justep.javaExt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DbUtils {
	/**
	 * 通过传进来的表达式获取一个连接，jdbc的直连或者jndi的资源
	 * 
	 * @param jndiName 
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static Connection getConnection(String jndiName) throws SQLException,NamingException {
		if (jndiName.contains(";")) {
			String[] prop = jndiName.split(";");
			try {
				Class.forName(prop[0]);
			} catch (ClassNotFoundException e) {
				throw new SQLException("获取" + jndiName + "指定的数据库连接时报错！" + e.getMessage());
			}
			return DriverManager.getConnection(prop[1], prop[2], prop[3]);
		} else {
			try {
				InitialContext c = new InitialContext();
				DataSource ds = (DataSource) c.lookup(jndiName);
				return ds.getConnection();
			} catch (NamingException e) {
				throw new NamingException("获取" + jndiName + "指定的数据库连接时报错！" + e.getMessage());
			}
		}
	}
	
	
}
