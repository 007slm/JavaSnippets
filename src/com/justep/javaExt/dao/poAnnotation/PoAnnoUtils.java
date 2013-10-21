package com.justep.javaExt.dao.poAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;


public final class PoAnnoUtils {

	public static String findTableName(Class<?> c) {
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new NullPointerException("Unfound Table annotation");
		}
		String name = t.name();
		if (name.equals("")) {
			name = c.getSimpleName().toLowerCase();
		}
		return name;
	}

	public static String findIdName(Class<?> c) {
		Field id = findTableId(c);
		if (id == null) {
			throw new NullPointerException(c.getName()
					+ " Unfound Table id field!");
		}
		Id i = id.getAnnotation(Id.class);
		String name = i.value();
		if (name.equals("")) {
			name = id.getName().toLowerCase();
		}
		return name;
	}

	public static String findFieldName(Field f) {
		Column field = f.getAnnotation(Column.class);
		String n = f.getName().toLowerCase();
		if (field == null) {
			Id id = f.getAnnotation(Id.class);
			if (id != null) {
				n = id.value();
			}
		} else {
			n = field.value();
		}
		if (n.equals("")) {
			n = f.getName().toLowerCase();
		}
		return n;
	}

	public static Field[] listTableFields(Class<?> c) {
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new NullPointerException("Unfound Table annotation");
		}
		Field[] fields = c.getDeclaredFields();
		if (fields == null) {
			return null;
		}
		boolean full = t.full();
		ArrayList<Field> list = new ArrayList<Field>();
		for (Field f : fields) {
			if (full) {
				if (!f.isAnnotationPresent(None.class)) {
					list.add(f);
				}
			} else {
				if (f.isAnnotationPresent(Id.class)
						|| f.isAnnotationPresent(Column.class)) {
					list.add(f);
				}
			}
		}
		return list.toArray(new Field[list.size()]);
	}

	public static Field findTableField(Class<?> c, String name) {
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new NullPointerException("Unfound Table annotation");
		}
		Field[] fields = c.getDeclaredFields();
		if (fields == null) {
			return null;
		}
		boolean full = t.full();
		for (Field f : fields) {
			if (full) {
				if (!f.isAnnotationPresent(None.class)) {
					if (f.getName().equalsIgnoreCase(name)) {
						return f;
					}
				}
			} else {
				if (f.isAnnotationPresent(Id.class)
						|| f.isAnnotationPresent(Column.class)) {
					if (f.getName().equalsIgnoreCase(name)) {
						return f;
					}
				}
			}
		}
		return null;
	}

	public static Field findTableId(Class<?> c) {
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new NullPointerException("Unfound Table annotation");
		}
		Field[] fields = c.getDeclaredFields();
		if (fields == null) {
			return null;
		}
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				return f;
			}
		}
		return null;
	}

	public static Field[] listFieldsWithoutId(Class<?> c) {
		Field id = findTableId(c);
		Field[] fields = listTableFields(c);
		Field[] array = new Field[fields.length - 1];
		for (int i = 0, j = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.equals(id)) {
				array[j++] = f;
			}
		}
		return array;
	}

	public static boolean isInt(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == int.class || f.getType() == Integer.class;
	}

	public static boolean isBoolean(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == boolean.class || f.getType() == Boolean.class;
	}

	public static boolean isByte(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == byte.class || f.getType() == Byte.class;
	}

	public static boolean isChar(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == char.class || f.getType() == Character.class;
	}

	public static boolean isDouble(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == double.class || f.getType() == Double.class;
	}

	public static boolean isFloat(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == float.class || f.getType() == Float.class;
	}

	public static boolean isLong(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == long.class || f.getType() == Long.class;
	}

	public static boolean isString(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == String.class;
	}

	public static boolean isDate(Field f) {
		if (f == null) {
			return false;
		}
		return f.getType() == Date.class;
	}
}
