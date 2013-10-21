package com.justep.justepExt.process;

import java.util.ArrayList;
import java.util.List;

import com.justep.justepExt.acitivity.Activity;
import com.justep.justepExt.action.Action;

public interface Process {
	List<Action> actionList = new ArrayList<Action>();
	
	List<Activity> activity = new ArrayList<Activity>();
	
}
