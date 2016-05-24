package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class VerifyUserDAO extends AbstractDAO<VerifyUser> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return VerifyUser.class;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	public VerifyUser add(Integer groupId, String email, Integer type,
			String groupName, String reason, String extend,Integer userId) {
		try {
			VerifyUser vUser = new VerifyUser();
			vUser.setGroupId(groupId);
			vUser.setEmail(email);
			vUser.setType(type);
			vUser.setGroupName(groupName);
			vUser.setReason(reason);
			vUser.setExtend(extend);
			vUser.setUserId(userId);
			
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			vUser.setTime(time);
			save(vUser);
			return vUser;
		} catch (Exception e) {
			log("create model failed!", Level.SEVERE, e);
			return null;
		}
	}

	// 同意申请，通过邀请之后删除待审核记录
	public void delete(Integer groupId, String email) {
		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			String queryString = "delete from VerifyUser model where model.groupId=:value " +
					"and model.email=?1";
			Query query = em.createQuery(queryString);
			query.setParameter("value", groupId);
			query.setParameter(1, email);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log("delete model failed!", Level.SEVERE, e);
		}
	}

	// 同意申请，通过邀请之后删除待审核记录
	public void delete(Integer groupId, Integer userId) {
		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			String queryString = "delete from VerifyUser model where model.groupId=:value " +
					"and model.userId=?1";
			Query query = em.createQuery(queryString);
			query.setParameter("value", groupId);
			query.setParameter(1, userId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log("delete model failed!", Level.SEVERE, e);
		}
	}

	// 通过组和用户名得到该记录
	public VerifyUser find(Integer groupId, String email) {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.groupId = :value and model.email=:email";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", groupId);
			query.setParameter("email", email);
			return (VerifyUser) query.getResultList().get(0);
		}
		catch(NoResultException ex){
			System.out.println("Verify User no result.");
			
			return null;
		}catch (Exception e) {
			log("find model failed!", Level.SEVERE, e);
			return null;
		}
	}

	public VerifyUser checkType(Integer groupId,String email,Integer type){

		try{
			
			String queryString = "select model from " + getClassName()
					+ " model where model.groupId = :value and model.email=:email and model.type=:type";
			
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", groupId);
			query.setParameter("email", email);
			query.setParameter("type", type);
			
			VerifyUser verifyUser = null;
			try{
				List<VerifyUser> users =  query.getResultList();
				if(users != null&&users.size() > 0){
					verifyUser = (VerifyUser) query.getResultList().get(0);
				}
			}
			catch(EntityNotFoundException e1){
				
				return null;
			}
			catch(NoResultException e1){
				
				System.out.println("I should be null");
				return null;
			}
			return verifyUser;
			
			
		}catch(Exception e){
			log("find model failed!", Level.SEVERE, e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<VerifyUser> findVerifyUserByGroupId(Integer id, Integer type) {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.groupId = :id and model.type =:type";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			query.setParameter("type", type);
			return query.getResultList();
		} catch (Exception e) {
			log("find model failed!", Level.SEVERE, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<VerifyUser> findVerifyUserByEmail(String email) {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.email =?1";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter(1, email);
			return query.getResultList();
		} catch (Exception e) {
			log("find model failed!", Level.SEVERE, e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<VerifyUser> findVerifyUserByTypeandEmail(int type,String email) {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.type =?1 and model.email=?2";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter(1, type);
			query.setParameter(2, email);
			return query.getResultList();
		} catch (Exception e) {
			log("find model failed!", Level.SEVERE, e);
			return null;
		}
	}
}