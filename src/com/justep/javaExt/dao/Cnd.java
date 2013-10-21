package com.justep.javaExt.dao;

import java.lang.reflect.Field;

import com.justep.javaExt.dao.poAnnotation.PoAnnoUtils;

public class Cnd {

	private Class<?> clazz;
	private StringBuffer sb;
	private boolean hasWhere = false;
	private boolean hasOrder = false;

	public Cnd(Class<?> clazz) {
		this.clazz = clazz;
		this.sb = new StringBuffer();
	}

	private String getFieldName(String name) {
		Field f = PoAnnoUtils.findTableField(clazz, name);
		if (f == null) {
			throw new NullPointerException(clazz.getName() + "中找不到名称为" + name
					+ "的字段");
		}
		String n = PoAnnoUtils.findFieldName(f);
		return n;
	}

	public Cnd where(String name, OP op, Object value) {
		if (this.hasOrder) {
			throw new IllegalStateException("已经order!");
		}
		sb.append(" where ");
		sb.append(op.toSql(this.getFieldName(name), value));
		this.hasWhere = true;
		return this;
	}

	public Cnd and(String name, OP op, Object value) {
		if (this.hasOrder) {
			throw new IllegalStateException("已经order!");
		}
		if (!this.hasWhere) {
			throw new IllegalStateException("没有where!");
		}
		sb.append(" and ");
		sb.append(op.toSql(this.getFieldName(name), value));
		return this;
	}

	public Cnd or(String name, OP op, Object value) {
		if (this.hasOrder) {
			throw new IllegalStateException("已经order!");
		}
		if (!this.hasWhere) {
			throw new IllegalStateException("没有where!");
		}
		sb.append(" or ");
		sb.append(op.toSql(this.getFieldName(name), value));
		return this;
	}

	public Cnd orderBy(String name, boolean asc) {
		if (!this.hasOrder) {
			sb.append(" order by ");
		} else {
			sb.append(",");
		}
		sb.append(this.getFieldName(name));
		if (asc) {
			sb.append(" asc");
		} else {
			sb.append(" desc");
		}
		this.hasOrder = true;
		return this;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}

}
