package com.free4lab.freeshare.model.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class GroupPermissionDAO extends AbstractDAO<GroupPermission>{

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return GroupPermission.class;
	}

	public String getClassName(){
		return getEntityClass().getName();
	}
	private static final String PU_NAME = "FreeSharePU";
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	public GroupPermission add(Integer groupId, Integer user, Integer type){
		GroupPermission gp = new GroupPermission();
		gp.setGroupId(groupId);
		gp.setUid(user);
		gp.setType(type);
		gp.setExtend(null);
		save(gp);
		return gp;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupPermission> findGroupsPermission(Integer uid){
		try{
			final String qlString = "select model from " + 
					getClassName() + " model where " +
					"model.uid = :uid"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("uid", uid);
			return (List<GroupPermission>)query.getResultList();
		}catch(Exception e){
			
			log(e.getMessage(),Level.INFO,e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<GroupPermission> findAllGroupsPermission(){
		try{
			final String qlString = "select model from " + 
					getClassName() + " model";
			Query query = getEntityManager().createQuery(qlString);
			
			return (List<GroupPermission>)query.getResultList();
		}catch(Exception e){
			
			log(e.getMessage(),Level.INFO,e);
			return null;
		}
	}
	//检查该用户是否是该组的管理员
	public GroupPermission findPermission(Integer uid, Integer groupId){
		try{
			final String qlString = "select model from " + 
					getClassName() + " model where " +
					"model.uid = :uid and model.groupId=:value"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("uid", uid);
			query.setParameter("value", groupId);
			try {
				return (GroupPermission)query.getSingleResult();
			} catch(Exception e){
				return null;
			} 
		}catch(Exception e){
			
			log(e.getMessage(),Level.INFO,e);
			return null;
		}
	}
	public boolean removeUserPermission(Integer user, Integer groupId){
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where " +
					"model.uid = :uid and model.groupId = :value"; 
			Query query = em.createQuery(qlString);
			query.setParameter("uid", user);
			query.setParameter("value", groupId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
	public boolean removeGroupPermission(Integer groupId){
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where  model.groupId = :value"; 
			Query query = em.createQuery(qlString);
			query.setParameter("value", groupId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
}
