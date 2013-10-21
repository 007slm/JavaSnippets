package com.justep.slm.easyMock.test;

public class UserServiceImpl implements UserService{
	private UserDao ud;
	
	
	public void setUd(UserDao ud) {
		this.ud = ud;
	}


	public User query(int id) {
		return ud.queryByID(id);
	}

}
