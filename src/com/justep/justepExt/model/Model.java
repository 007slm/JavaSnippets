package com.justep.justepExt.model;

import java.util.ArrayList;
import java.util.List;

import com.justep.justepExt.biz.cast.Castorable;

public interface Model {
	List<Process> processList = new ArrayList<Process>();
	
	/**
	 *  当前model使用的classLoader用来动态加载卸载模块 
	 */
	ClassLoader cl = Thread.currentThread().getClass().getClassLoader();
	
	public Castorable doAction();
	
	public void loadModel();
	
	public void unloadModel();
	
}
