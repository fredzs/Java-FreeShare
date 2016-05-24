package com.free4lab.freeshare.model.dao;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.*;

import com.free4lab.utils.sql.*;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class CommentDao extends AbstractDAO<Comment>{

	public Class<Comment> getEntityClass() {
		// TODO Auto-generated method stub
		return Comment.class;
	}
	
	public String getClassName() {
		return getEntityClass().getName();
	}
	
	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}
	
	public static final String PU_NAME = "FreeSharePU";
	
	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}
	
	//在数据库中插入一条评论
	public Comment create(String cmtUrl,Integer resourceId,Integer resourceType,Integer userId, String description){
		log("finding " + getClassName() + " instance: create a comment",
    			Level.INFO, null);
		Date date = new Date();
    	Timestamp currentTime = new Timestamp(date.getTime());
		Comment comment = new Comment();
		comment.setcmtUrl(cmtUrl);
		comment.setResourceId(resourceId);
		comment.setResourceType(resourceType);
		comment.setUserId(userId);
		comment.setTime(currentTime);
		comment.setDescription(description);
		save(comment);
		return comment; 
	}
	
	//更新一条评论
	public void updateComment(String cmtUrl,Integer resourceId,Integer resourceType,Integer userId){
		log("finding " + getClassName() + " instance: update a comment",
    			Level.INFO, null);
		Comment comment = new Comment();
		comment.setcmtUrl(cmtUrl);
		comment.setResourceId(resourceId);
		comment.setResourceType(resourceType);
		comment.setUserId(userId);
		Date date = new Date();
    	Timestamp currentTime = new Timestamp(date.getTime());
		comment.setTime(currentTime);
		update(comment);	
	}

  	/**
       * 通过cmtUrl删除评论
       * added by lzc
  	 */
      public int deleteByCmtUrl(String cmtUrl){
      	log("deleting" + getClassName() + " instance by cmtUrl: " + cmtUrl , Level.INFO, null);
          
          EntityManager em = getEntityManager();
  		if (em.getTransaction().isActive()){
  			em.getTransaction().rollback();
  		}
  		em.getTransaction().begin();
  		try {
  			
  			final String queryString = "delete from " + getClassName() + " model where model.cmtUrl = :cmtUrl";
  			Query query = em.createQuery(queryString);
  			query.setParameter("cmtUrl",cmtUrl);
  		    int ans = query.executeUpdate();
  			em.getTransaction().commit();
  			return ans;
  		} catch (RuntimeException re) {
  			log("del records by cmtUrl = " + cmtUrl +" failed!",Level.INFO, re);
  			em.getTransaction().rollback();
  			throw re;
  		}
  	}
      
    //查找所有的评论记录
    public List<Comment> findAll(){
    	return super.findAll();
    }
}