package com.justep.slm.easyMock.basic;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.justep.slm.easyMock.test.User;
import com.justep.slm.easyMock.test.UserDao;
import com.justep.slm.easyMock.test.UserDaoImpl;
import com.justep.slm.easyMock.test.UserService;
import com.justep.slm.easyMock.test.UserServiceImpl;

/**
 * 
 * 本测试用例 要测试 UserServiceImpl里面query方法的实现时候正确
 * 
 * 这个时候不用关心 这个方法调用的函数 是否返回正确 我们默认应该是正确的 
 * 所以 利用mock 在虚构 这个方法调用的其他方法让这个方法 调用的函数都能返回期望的结果 
 * 来验证这个函数本身是否正确
 * 
 * 所以 
 * 为了测试 UserServiceImple中query的正确行 
 * 我们mock UserDao 的queryByID 
 * 
 */
public class TestEasyMock {
	@Test
	/**
	 * 基于接口的mock
	 */
	public void mockUserDao() {
		User user = new User();
		user.setName("007slm");
		user.setAge(26);
		user.setId(007);
		/**
		 * 默认是不记录调用顺序 但是记录调用次数的
		 * createStrictMock  记录 被mock对象被调用函数的顺序 同时记录调用的次数 最严格 
		 * createNiceMock  不记录被mock对象被调用函数的顺序 同时也不记录调用的次数 最懒
		 *  
		 */
		UserDao ud = EasyMock.createMock(UserDao.class);
		//UserDao ud = EasyMock.createStrictMock(UserDao.class);
		//UserDao ud = EasyMock.createNiceMock(UserDao.class);
		/* 也可以对impl进行mock
		 * UserDao ud = EasyMock.createMock(UserDaoImpl.class);
		 * */
		EasyMock.expect(ud.queryByID(007)).andReturn(user).times(2);
		EasyMock.replay(ud);
		
		
		UserService us = new UserServiceImpl();
		us.setUd(ud);
		User u = us.query(007);
		
		/**
		 * Unexpected method call UserDao.queryByID(7):
				    UserDao.queryByID(7): expected: 1, actual: 2
			默认createMock是记录了调用次数的 期望的是一次 可以上面调用了一次 这里在调用一次 2次 就报错了
		 */
		System.out.println(ud.queryByID(007));
			  		
					

		/**
		 * 没有闹懂这个verify是干什么的
		 */
		EasyMock.verify(ud); 
		Assert.assertSame(user, u);
	}
	
}
