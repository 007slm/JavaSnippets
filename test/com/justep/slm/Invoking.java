package com.justep.slm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 函数调用方式
 * 
 * 
 */
public class Invoking {
	/*------------------------------------------------------------------------*/
	private static abstract class Invoker {

		protected Method method;

		public Invoker(Method method) {
			this.method = method;
		}

		abstract Object invoke(Object obj) throws Exception;
	}

	/*------------------------------------------------------------------------*/
	private static class DefaultInvoker extends Invoker {

		private Object[] args;

		public DefaultInvoker(Method method, Object[] args) {
			super(method);
			this.args = args;
		}

		@Override
		Object invoke(Object obj) throws Exception {
			return method.invoke(obj, args);
		}
	}

	/*------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private static class DynamicArgsInvoker extends Invoker {

		private Object args;

		public DynamicArgsInvoker(Method method, Object args) {
			super(method);
			this.args = args;
		}

		@Override
		Object invoke(Object obj) throws Exception {
			return method.invoke(obj, args);
		}
	}

	/*------------------------------------------------------------------------*/
	private static class NullArgInvoker extends Invoker {

		public NullArgInvoker(Method method) {
			super(method);
		}

		@Override
		Object invoke(Object obj) throws Exception {
			return method.invoke(obj);
		}

	}

	/*------------------------------------------------------------------------*/

	public Invoking(Class<?> type, String methodName, Object... args) {
		try {
			// get directoy
			if (null == args || args.length == 0) {
				invoker = new NullArgInvoker(type.getMethod(methodName));
			} else {
				// get all same name methods
				Method[] all = type.getMethods();
				List<Method> candidates = new ArrayList<Method>(all.length);
				for (Method m : all)
					if (m.getName().equals(methodName)) {
						// int mod =
						// m.getParameterTypes().length -
						// args.length;
						// if (mod == 0 || mod == 1)
						candidates.add(m);
					}
				// get argTypes
				Class<?>[] argTypes = Mirror.evalToTypes(args);
				// check the candidate methods can be
				// match or not
				for (Iterator<Method> it = candidates.iterator(); it.hasNext();) {
					Method m = it.next();
					Class<?>[] pts = m.getParameterTypes();
					MatchType mr = Mirror.matchParamTypes(pts, argTypes);
					if (MatchType.YES == mr) {
						invoker = new DefaultInvoker(m, args);
						break;
					} else if (MatchType.LACK == mr) {
						
						break;
					} 
				}
				// if fail to match, try to cast args
				// to same length param method
				// ro to last param is "T...", length+1
				// method
				if (null == invoker)
					try {
						for (Iterator<Method> it = candidates.iterator(); it
								.hasNext();) {
							Method m = it.next();
							Class<?>[] pts = m.getParameterTypes();
							if (pts.length == args.length) {
								throw new RuntimeException(
										"没有找到合适的函数，但是有参数个数相同，但是类型不匹配的函数，请检查你传递的参数顺序是否有误");
							}
						}
					} catch (Exception e) {
					}
				// to same length + last is dynamic
				// argument method
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (null == invoker)
			throw new RuntimeException("Don't know how to invoke ["
					+ type.getName() + "]." + methodName + "() by args:\n "
					+ args);
	}

	private Invoker invoker;

	public Object invoke(Object obj) throws Exception {
		return invoker.invoke(obj);
	}

}
