package com.justep.slm.junit.basic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @Before：初始化方法
	@After：释放资源
	@Test：测试方法，在这里可以测试期望异常和超时时间
	@Ignore：忽略的测试方法
	@BeforeClass：针对所有测试，只执行一次，且必须为static void
	@AfterClass：针对所有测试，只执行一次，且必须为static void
	一个JUnit 4 的单元测试用例执行顺序为：
	@BeforeClass –> @Before –> @Test –> @After –> @AfterClass
	每一个测试方法的调用顺序为：
	@Before –> @Test –> @After
	所有方法都应该是public的

 * @author Administrator
 *
 */
public class TestAnnotation {
	@Before
	public void before(){
		System.out.println("before");
	}
	
	@After
	public void after(){
		System.out.println("before");
	}
	
	@Test
	public void testMethod(){
		System.out.println("test");
	}
	
	@Ignore
	public void ignore(){
		System.out.println("ignore");
	}
	
	public void defaultMethod(){
		System.out.println("defaultMethod");
	}
	
	@BeforeClass
	public static void beforeClass(){
		System.out.println("beforeClass");
	}
	
	@AfterClass
	public static void afterClass(){
		System.out.println("afterClass");
	}
}
