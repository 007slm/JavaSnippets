package com.justep.test.testClassLoader;


public class GetPath {
	public static void main(String[] args) {
		/**
		 * 根据class找到jar的位置
		 */
		System.out.println(org.apache.log4j.Logger.class.getResource(""));
	}
}
