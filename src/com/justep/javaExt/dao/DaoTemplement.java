package com.justep.javaExt.dao;

/**
 * 
 * @author 007slm
 *  要实现的逻辑
          插入 Insert 一条 SQL 插入一条记录
       @EXAMPLE{
          dao.insert(p);	
       }   
	删除 Delete 一条 SQL 删除一条记录
	   @EXAMPLE{
   		  根据名称删除 （如果你的实体声明了 @Name 字段） 
			dao.delete(Person.class,"Peter");
		 根据 ID 删除 （如果你的实体声明了 @Id 字段） 
		 	dao.delete(Person.class,2);
	   } 
	
	更新 Update 一条 SQL 更新一条记录
	@EXAMPLE{
		Person p = dao.fetch(Person.class,2); 
		p.setAge(32); 
		dao.update(p)
		
	}
	 
	获取 Fetch 一条 SQL 获取一条记录 
	
	查询 Query 一条 SQL 根据条件获取多条记录 
	@EXAMPLE{
		List<Person>people = dao.query(Person.class, Cnd.where("name", "like", "P%"), dao.createPager(1, 4));
	}
	联系
	清除 Clear 一条 SQL 根据条件删除多条记录 
	@EXAMPLE{
		dao.clear(Person.class,Cnd.where("id", ">", 35));
	}

 */
public class DaoTemplement {
	public static <T> ResultSetWapper fetch(Class<T> klass){
		//dao.fetch(Person.class,2);
		return null;
	}
	
	
	
//	select * from Pa_auditOpinion join Pa_opinion on Pa_auditOpinion.fIsHaveCh on Pa_opinion.fid
//  Pa_auditOpinion.fIsHaveCh.Tfile
//  joinFetch(Pa_auditOpinion.class,fIsHaveCh)
	
//  joinFetch(Map)	
	
	
	
	
	
	
	
	
	public class ResultSetWapper {
		
	}
	
	
}
