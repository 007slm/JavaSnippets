package com.justep.justepExt.acitivity;

import java.util.ArrayList;
import java.util.List;

import com.justep.justepExt.action.Action;
import com.justep.justepExt.biz.cast.Castorable;

public interface Activity {
	List<Action> actionList = new ArrayList<Action>();
	
	// 利用activity对象来执行action 返回biz和ui约定好的可以转化的接口的一种实现
	public Castorable doAction();
}
