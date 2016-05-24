package com.free4lab.freeshare.model.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class GroupUserDAO extends AbstractDAO<GroupUser> {

	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return GroupUser.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	public List<GroupUser> getGusByGroupId(Integer groupId) {
		try {
			if (groupId == null) {
				return null;
			}
			return findByProperty("groupId", groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 根据群组的id，获取该组的所有组员的id
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getUids(Integer groupId) {
		try {
			if (groupId == null) {
				return null;
			}
			final String queryString = "select userId from " + getClassName()
					+ " model where model.groupId = :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", groupId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<GroupUser> getGusByUID(Integer userId) {
		try {
			return findByProperty("userId", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 通过groupId,删除某个群组的所有用户关系
	public void delMembers(Integer groupId) {
		EntityManager em = getEntityManager();
		try {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.getTransaction().begin();
			final String qlString = "delete from " + getClassName()
					+ " model where model.groupId = :groupId";
			Query query = em.createQuery(qlString);
			query.setParameter("groupId", groupId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log(e.getMessage(), Level.INFO, e);
		}
	}

	// 通过groupId,userId,删除某个组与组员之间的关系
	public void delMember(Integer groupId, Integer userId) {
		EntityManager em = getEntityManager();
		try {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.getTransaction().begin();
			final String qlString = "delete from "
					+ getClassName()
					+ " model where model.groupId = :groupId and model.userId = :userId";
			Query query = em.createQuery(qlString);
			query.setParameter("groupId", groupId);
			query.setParameter("userId", userId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log(e.getMessage(), Level.INFO, e);
		}
	}

	/**
	 * 如果是该组成员,返回真
	 */
	public boolean isMember(Integer groupId, Integer userId) {
		try {
			if (groupId == null || userId == null) {
				return false;
			}
			List<GroupUser> gu = findByProperty2("groupId", groupId, "userId",
					userId);
			if (gu != null && gu.size() > 0) {
				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}

}
