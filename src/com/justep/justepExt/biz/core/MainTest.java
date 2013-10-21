package com.justep.justepExt.biz.core;

import java.lang.reflect.Method;

public class MainTest {
	public static void main(String[] args) {
		/**
		 * 假设有个请求是    \actionName1
		 */
		int i =10;
		for (int j = 0; j < i; j++) {
			new Thread(
					new Runnable() {
						public void run() {
							BizEngine engine = BizEngineImpl.getInstance();
							RequestContext rc = new RequestContext();
							rc.put("actionName", Thread.currentThread().getName());
							engine.doAction();
						}
					}).start();
		}
		
	}
}


