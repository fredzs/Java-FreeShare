package com.free4lab.freeshare.model.dao;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.*;
import com.free4lab.utils.sql.*;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class FreeUserScoreDAO extends AbstractDAO<FreeUserScore> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return FreeUserScore.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	//在数据库中插入一条评分记录
    public FreeUserScore create(Integer uid, Integer publish, Integer browse,Integer reply, 
    		  Integer publishDay, Integer browseDay, Integer replyDay, Integer redHeart){
		FreeUserScore freeUserScore = new FreeUserScore();
		freeUserScore.setUid(uid);
		freeUserScore.setBrowse(browse);
		freeUserScore.setBrowseDay(browseDay);
		freeUserScore.setPublish(publish);
		freeUserScore.setPublishDay(publishDay);
		freeUserScore.setReplyDay(replyDay);
		freeUserScore.setReply(reply);
		freeUserScore.setRedHeart(redHeart);
		save(freeUserScore);
		return freeUserScore;
	}
	
    /**
     * 根据操作类型在数据库中更新一条评分记录
     * 不同操作得分不同
     * @return
     * @param uid 
     * @param type
     * @param score
    */
	public void updateFus(Integer uid, Integer type, Integer score){
		FreeUserScore fus = findFusByUid(uid);
		if(type == 0){
			fus.setPublish(score+fus.getPublish());
			fus.setPublishDay(score+fus.getPublishDay());
		}else if(type == 1){
			fus.setReply(score+fus.getReply());
			fus.setReplyDay(score+fus.getReplyDay());
		}else if(type == 2){
			fus.setBrowse(score+fus.getBrowse());
			fus.setBrowseDay(score+fus.getBrowseDay());
		}else{
			fus.setRedHeart(fus.getRedHeart() + 1);
		}
		update(fus);
	}
	
	/**
     * 通过uid删除评分记录
	 */
    public int delete(Integer uid){
    	log("finding" + getClassName() + " instance: " +
				"del scores by iid: " + uid, Level.INFO, null);
        
        EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			
			final String queryString = "delete from " + getClassName() + " model where model.uid = :uid";
			Query query = em.createQuery(queryString);
			query.setParameter("uid", uid);
			int ans = query.executeUpdate();
			em.getTransaction().commit();
			logger.info("del " + ans + "records by uid = " + uid);
			return ans;
		} catch (RuntimeException re) {
			log("del records by iid = " + uid + " failed!",Level.INFO, re);
			em.getTransaction().rollback();
			throw re;
		}
    }
	
	 /**
	  * 返回某个用户的积分记录
     * @return FreeUserScore
     * @param uid
     */
    public FreeUserScore findFusByUid(Integer uid){
    	log("finding " + getClassName() + " instance with uid: " + uid,
                Level.INFO, null);
        try {
            final String queryString = "select model from " + getClassName() + " model where model.uid = :uid";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("uid", uid);
            try {
            	return (FreeUserScore)query.getSingleResult();
            } catch (NoResultException e) {
            	return null;
            }
            
        } catch (RuntimeException re) {
            log("find by uid failed",Level.SEVERE, re);
            throw re;
        }
    }
    //查找所有的用户
    public List<FreeUserScore> findAll(){
    	return super.findAll();
    }
}
