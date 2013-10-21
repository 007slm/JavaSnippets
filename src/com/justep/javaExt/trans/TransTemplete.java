package com.justep.javaExt.trans;

import java.sql.SQLException;
/**
 * 
 * @author 007slm
 *	支持事务的模板类，利用此事务模板，你可以简单的按用wapper把一端代码用事务抱起来。
 * wapper的参数atom的中run方法就是事务的边界
 */
public class TransTemplete {
	/**
	 * 
	 * 支持一系列操作是原子的
	 */
	static ThreadLocal<Transaction> trans=new ThreadLocal<Transaction>();
	/**
	 * 当存在嵌套事务的时候，这个事务的深度
	 */
	static ThreadLocal<Integer> depth = new ThreadLocal<Integer>();
	
	private static void createTransaction(){
		Transaction tran ;
		if(trans.get()!=null){
			tran=trans.get();
		}else{
			tran=new Transaction();
			trans.set(tran);
		}
	}
	
	private static void safeBegin() {
		if(trans.get()==null){
			throw new RuntimeException("事务启动异常");
		}else{
			if(depth.get()==null){
				depth.set(1);
				
			}else{
				depth.set(depth.get()+1);
			}
			
		}
	}
	
	private static void safeCommit() throws SQLException{
		int depthValue=depth.get();
		if(--depthValue==0){
			trans.get().commit();
		}
		depth.set(depthValue);
		

	}
	
	/**
	 * 事务完成 关闭这个事务占用的资源 
	 * 同时清空threadLocal中当前线程的事务对象
	 * @throws SQLException
	 */
	private static void safeClear() throws SQLException{
		int depthValue=depth.get();
		if(depthValue==0){
			trans.get().clear();
			trans.set(null);
		}
		
	}
	
	private static void safeRollback()throws SQLException{
		trans.get().rollback();
	}
	
	public static void wapper(Atom atom) {
		createTransaction();
		try {
			safeBegin();
			atom.run(trans.get());
			safeCommit();
		} catch (Exception e) {
			depth.set(0);
			e.printStackTrace();
			try {
				safeRollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				safeClear();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}