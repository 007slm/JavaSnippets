package com.justep.justepExt.biz.core;

public interface Context {
	public Object get(String key);
	public Object put(String key,Object value);
	public boolean contains(String key);
	public void remove(String key);
}
