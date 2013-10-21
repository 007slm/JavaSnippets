package com.justep.javaExt.utils;

public interface ErrorMessage {
	public static final String NOT_NULL = "%s不能为空!";
	public static final String NOT_MODEL = "%s的model为空!";
	public static final String NULL_MODEL_NAME = "%s的model为空!";
	public static final String NOT_EXIST = "%s不存在!";
	public static final String NOT_FIND_MODEL = "无法找到Model: %s!";
	public static final String INVALID = "%s[%s]无效!";
	public static final String PARAMETER_INVALID = "%s参数异常!";
	public static final String INVOKE_ACTION_ERROR = "%s,%s,%sAction执行失败!%s";
	public static final String INVOKE_PROCEDURE_ERROR = "%s[code:%s]执行失败!%s";
}
