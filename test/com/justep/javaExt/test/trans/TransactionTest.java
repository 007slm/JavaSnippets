package com.justep.javaExt.test.trans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.justep.javaExt.trans.Atom;
import com.justep.javaExt.trans.TransTemplete;
import com.justep.javaExt.trans.Transaction;

public class TransactionTest {
	public static void main(String[] args) {
		
		testMutilThread();
		
		testSignle();
	}
	
	/**
	 * 单线程下的回滚
	 */
	private static void testSignle() {
		TransTemplete.wapper(new Atom(){
			public void run(Transaction tran) throws Exception {
				Connection sysConn =tran.getConnection("oracle.jdbc.driver.OracleDriver;jdbc:oracle:thin:@192.168.0.114:1521:justep;x5system1690;justep");
				System.out.println(sysConn);
				Statement stmt =sysConn.createStatement();
				System.out.println("stmt:"+stmt);
				ResultSet rsinner =stmt.executeQuery("select * from sa_opperson");
				
				long a=System.currentTimeMillis();
				int num=0;
				while(rsinner.next()){
					num++;
				}
				System.out.println("\r<br>testSignle执行耗时 : "+(System.currentTimeMillis()-a)+"豪秒 ");
				
				System.out.println(rsinner);
				throw new RuntimeException("ddd");
			}
		});
	}

	/**
	 * 多线程运行嵌套事务
	 */
	private static void testMutilThread(){
		for (int i = 0; i < 10; i++) {
			
			Thread thread = new Thread(new Runnable(){
				
				public void run() {
					execNestTran();
				}
			});
			thread.start();
		}
		
		
		
	}
	
	/**
	 * 嵌套事务
	 */
	private static void execNestTran(){
		TransTemplete.wapper(
				new Atom(){
					
					public void run(Transaction tran) throws Exception{
						/**
						 * 这里面是要求原子操作的事务代码
						 * 
						 * oracle.jdbc.driver.OracleDriver
						 * jdbc:oracle:thin:@192.168.0.114:1521:justep
						 * x5system1690
						 * justep
						 * 
						 * oracle.jdbc.driver.OracleDriver
						 * jdbc:oracle:thin:@192.168.0.114:1521:justep
						 * PAMIS1690
						 * justep
						 */
						for (int i = 0; i < 5; i++) {
							TransTemplete.wapper(new Atom(){
								
								public void run(Transaction tran) throws Exception {
									Connection sysConn = tran.getConnection("oracle.jdbc.driver.OracleDriver;jdbc:oracle:thin:@192.168.0.114:1521:justep;x5system1690;justep");
									System.out.println(sysConn);
									Statement stmt =sysConn.createStatement();
									System.out.println("stmt:"+stmt);
									ResultSet rsinner =stmt.executeQuery("select * from sa_opperson");
									
									long a=System.currentTimeMillis();
									int num=0;
									while(rsinner.next()){
										num++;
									}
									System.out.println("\r<br>testMutilThread执行耗时 : "+(System.currentTimeMillis()-a)+"豪秒 ");
									
									System.out.println(rsinner);
								}
							});
							
						}
					}
				}
			);
	}
}
