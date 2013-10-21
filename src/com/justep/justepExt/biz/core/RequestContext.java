package com.justep.justepExt.biz.core;

import java.util.HashMap;
import java.util.Map;

public class RequestContext implements Context{
	private Map<String,Object> params = new HashMap<String,Object>();
	
	public void initialValue(){
	}
	
	public Object get(String key) {
		return params.get(key);
	}

	public Object put(String key, Object value) {
		return params.put(key, value);
	}

	public boolean contains(String key) {
		return params.containsKey(key);
	}

	public void remove(String key) {
		params.remove(key);
	}
}
