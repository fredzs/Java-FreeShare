package com.free4lab.freeshare.model.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class UserCollectionDAO extends AbstractDAO<UserCollection> {

	public Class<UserCollection> getEntityClass() {
		return UserCollection.class;
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
	
	public UserCollection create(Integer uid, Integer iid, String type){
		UserCollection uc = new UserCollection();
		uc.setAuthor_id(uid);
		uc.setIid(iid);
		uc.setExtend("");
		uc.setType(type);
		save(uc);
		getLogger().info("保存成功！");
		return uc;
	}
	/**
	 * 通过用户id和要查找的资源类型查找该用户的收藏资源
	 * @param uid 用户的id
	 * @param type 资源类型
	*/
	public List<UserCollection> findByUid(Integer uid, String type){
		return findByProperty2("author_id", uid, "type", type);
	}
	/**
	 * 通过用户id和资源iid和资源类型查找数据库是否存在该资源
	 * @param uid 用户的id
	 * @param iid 资源id
	*/
	public boolean isExist(Integer uid, Integer iid, String type){
		EntityManager em = getEntityManager();
		try {
			String queryString = "select model from UserCollection model where model.author_id= ?1 " +
					"and model.iid= ?2 and model.type= ?3";
			Query query = em.createQuery(queryString);
			query.setParameter(1, uid);
			query.setParameter(2, iid);
			query.setParameter(3, type);
			try{
				UserCollection uc = (UserCollection) query.getSingleResult();
				if(uc != null){
					return false;
				}
		     }
		     catch(NoResultException re){
		    	log("find model failed", Level.INFO, re);
		    	return true;
		     }
		     return false;
		} catch (Exception e) {
			log("finding model failed!", Level.SEVERE, e);
			return false;
		} finally{
			em.close();
		}
	}
	/**
	 * 通过用户id和资源iid和资源类型删除某个收藏资源
	 * @param uid 用户的id
	 * @param iid 资源id
	*/
	public void delete(Integer uid, Integer iid, String type){
		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			String queryString = "delete from UserCollection model where model.author_id= ?1 " +
					"and model.iid= ?2 and model.type= ?3";
			Query query = em.createQuery(queryString);
			query.setParameter(1, uid);
			query.setParameter(2, iid);
			query.setParameter(3, type);
			getLogger().info(queryString);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log("delete model failed!", Level.SEVERE, e);
		}
	}
	public void delByIid(Integer iid){
		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			String queryString = "delete from UserCollection model where model.iid=?1";
			getLogger().info(queryString);
			Query query = em.createQuery(queryString);
			query.setParameter(1, iid);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log("delete model failed!", Level.SEVERE, e);
		}
	}
}
