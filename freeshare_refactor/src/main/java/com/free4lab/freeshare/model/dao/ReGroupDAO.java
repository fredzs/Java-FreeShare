package com.free4lab.freeshare.model.dao;

import java.util.logging.Level;

import javax.persistence.EntityManager;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.SimpleEntityManagerHelper;

public class ReGroupDAO extends AbstractDAO<ReGroup> {

	public Class<ReGroup> getEntityClass() {
		return ReGroup.class;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new SimpleEntityManagerHelper();
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	public void delByGroupId(Integer groupId) {
		EntityManager em = getEntityManager();

		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			ReGroup rg = (ReGroup) findByProperty("reGroupId", groupId).get(0);
			if (rg != null) {
				//TODO 看一下文档
				em.remove(rg);
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			log("delete model failed!", Level.SEVERE, e);
		}
	}
}
