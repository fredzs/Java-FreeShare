package com.free4lab.freeshare.model.dao;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class UserScoreDAO extends AbstractDAO<UserScore> {

	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return UserScore.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	// 在数据库中插入一条评分记录
	public UserScore create(Integer uid, Integer iid, String type, float score) {
		UserScore userScore = new UserScore(uid, iid, 0, 0, type, score);
		save(userScore);
		return userScore;
	}

	/**
	 * 通过kind和type值，相应更新评分记录
	 * 
	 * @param uid
	 *            用户
	 * @param iid
	 *            资源
	 * @param type
	 *            item/list
	 * @param kind
	 *            1：顶，2：踩，3：浏览，
	 */
	public void update(Integer uid, Integer iid, String type, Integer kind) {
		UserScore userScore = find(uid, iid, type);
		if (userScore == null) {
			userScore = create(uid, iid, type, 0.1f);
		}
		switch (kind) {
		case 1:
			userScore.setUp(userScore.getUp() + 1);
			break;
		case 2:
			userScore.setDown(userScore.getDown() + 1);
			break;
		case 3:
			userScore.setScore(userScore.getScore() + 0.1f);
			break;
		}
		update(userScore);
	}

	/**
	 * 通过资源iid删除评分记录
	 * 
	 * @param iid
	 *            资源
	 */
	public int delete(Integer iid) {
		log("finding" + getClassName() + " instance: " + "del scores by iid: "
				+ iid, Level.INFO, null);

		EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {

			final String queryString = "delete from " + getClassName()
					+ " model where model.iid = :iid";
			Query query = em.createQuery(queryString);
			query.setParameter("iid", iid);
			int ans = query.executeUpdate();
			em.getTransaction().commit();
			logger.info("del " + ans + "records by iid = " + iid);
			return ans;
		} catch (RuntimeException re) {
			log("del records by iid = " + iid + " failed!", Level.INFO, re);
			em.getTransaction().rollback();
			throw re;
		}
	}

	/**
	 * 通过uid和iid寻找评分记录
	 */
	public UserScore find(Integer uid, Integer iid, String type) {
		log("finding " + getClassName() + " instance with uid: " + uid
				+ ", iid: " + iid, Level.INFO, null);
		try {
			final String queryString = "select model from "
					+ getClassName()
					+ " model where model.uid = ?1 and model.iid = ?2 and model.type = ?3";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter(1, uid);
			query.setParameter(2, iid);
			query.setParameter(3, type);
			try {
				return (UserScore) query.getSingleResult();
			} catch (Exception e) {
				log("find by uid " + uid + "and iid " + iid + "and type  "
						+ type + " failed!", Level.SEVERE, e);
			}

		} catch (RuntimeException re) {
			log("find by uid " + uid + "and iid " + iid + "and type  "
					+ type + " failed!", Level.SEVERE, re);
		}
		return null;
	}

	/**
	 * 通过iid查找所有用户对该资源的操作记录
	 * 
	 * @param iid
	 *            资源id
	 */
	@SuppressWarnings("unchecked")
	public List<UserScore> findUserScoreById(Integer iid, String type) {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.iid = ?1 and model.type = ?2";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter(1, iid);
			query.setParameter(2, type);
			try {
				return query.getResultList();
			} catch (Exception e) {
				log("find by type " + type + "and iid " + iid + " failed!", Level.SEVERE, e);
				return null;
			}
		} catch (RuntimeException re) {
			log("find by uid and iid failed", Level.SEVERE, re);
			throw re;
		}
	}

	// 查找所有的用户
	@SuppressWarnings("unchecked")
	public List<UserScore> findAll() {
		try {
			final String queryString = "select model from " + getClassName()
					+ " model";
			Query query = getEntityManager().createQuery(queryString);
			getLogger().info(queryString);
			try {
				return query.getResultList();
			} catch (NoResultException e) {
				log("find all failed", Level.SEVERE, e);
				return null;
			}
		} catch (RuntimeException re) {
			log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}
}