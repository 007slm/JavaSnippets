package com.justep.slm;

import java.util.HashSet;

public class Mirror {
	static HashSet<Class<?>> numberClassSet=new HashSet<Class<?>>();
	static {
		numberClassSet.add(long.class);
		numberClassSet.add(Long.class);
		numberClassSet.add(int.class);
		numberClassSet.add(Integer.class);
		numberClassSet.add(float.class);
		numberClassSet.add(Float.class);
		numberClassSet.add(double.class);
		numberClassSet.add(Double.class);
		numberClassSet.add(byte.class);
		numberClassSet.add(Byte.class);
		numberClassSet.add(short.class);
		numberClassSet.add(Short.class);
	}
	
	public static Class<?>[] evalToTypes(Object... args) {
		Class<?>[] types = new Class[args.length];
		int i = 0;
		for (Object arg : args)
			types[i++] = null == arg ? Object.class : arg.getClass();
		return types;
	}

	
	public static MatchType matchParamTypes(Class<?>[] paramTypes, Class<?>[] argTypes) {
		int len = argTypes == null ? 0 : argTypes.length;
		if (len == 0 && paramTypes.length == 0)
			return MatchType.YES;
		if (paramTypes.length == len) {
			for (int i = 0; i < len; i++)
				if (!canCastToDirectly(paramTypes[i],argTypes[i]))
					return MatchType.NO;
			return MatchType.YES;
		} else if (len + 1 == paramTypes.length) {
			if (!paramTypes[len].isArray())
				return MatchType.NO;
			for (int i = 0; i < len; i++)
				if (!canCastToDirectly(paramTypes[i],argTypes[i]))
					return MatchType.NO;
			return MatchType.LACK;
		}
		return MatchType.NO;
	}
	
	public static boolean canCastToDirectly(Class<?> sur ,Class<?> desc) {
		
		try {
			if (sur == desc|| desc.isAssignableFrom(sur))
				return true;
			if (sur.isPrimitive() && desc.isPrimitive()) {
				if (isPrimitiveNumber(sur) && isPrimitiveNumber(desc))
					return true;
				
			}else if (sur.getName().equals(desc.getName())){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean isPrimitiveNumber(Class<?> klass) {
		return numberClassSet.contains(klass);
	}

}
