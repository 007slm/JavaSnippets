package com.justep.javaExt.test.dao;


import java.util.Date;

import java.util.List;

import com.justep.javaExt.dao.BaseDao;
import com.justep.javaExt.dao.Cnd;
import com.justep.javaExt.dao.OP;
import com.justep.javaExt.dao.poAnnotation.DataSource;
import com.justep.javaExt.trans.Atom;
import com.justep.javaExt.trans.TransTemplete;
import com.justep.javaExt.trans.Transaction;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataSource ds =TestPo.class.getAnnotation(DataSource.class);
		System.out.println(ds.value());
		String jndiName="";
		if(!"".equals(ds.value())){
			jndiName=ds.value();
		}
		System.out.println(jndiName);
		TransTemplete.wapper(new Atom(){
			public void run(Transaction tran) throws Exception {
				BaseDao<TestPo> dao = new BaseDao<TestPo>(tran,"oracle.jdbc.driver.OracleDriver;jdbc:oracle:thin:@192.168.0.114:1521:justep;x5system1690;justep"){
					public Class<TestPo> getPoType() {
						return TestPo.class;
					}
				};
				List<TestPo> list = dao.query(new Cnd(dao.getPoType()).where("time",
						OP.LESS_EQ, new Date()).and("flag", OP.EQ, '*').orderBy(
						"biaoShi", false).orderBy("time", true));
				for (TestPo vo : list) {
					System.out.println(vo.getBiaoShi());
				}
			}
		});
	}

}
