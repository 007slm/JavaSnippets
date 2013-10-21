package com.justep.justepExt.biz.core;

public class BizEngineImpl implements BizEngine {
	
	private static final BizEngine instance = new BizEngineImpl();
	private ThreadLocal<RequestContext> requestContextList = new ThreadLocal<RequestContext>();
	
	public static BizEngine getInstance() {
		return instance;
	}
	
	public RequestContext getRequestContext() {
		return requestContextList.get();
	}
	
	public void setRequestContext(RequestContext rc) {
		requestContextList.set(rc);
	}

	public String doAction() {
		System.out.println("执行action:"+getRequestContext().get("actionName"));
		return getRequestContext().get("actionName").toString();
	}
	
	public Object service(){
		return null;
	}

}
