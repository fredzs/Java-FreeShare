package com.free4lab.freeshare.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class FormworkDAO extends AbstractDAO<Formwork> {
	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return Formwork.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	public Formwork findById(Integer id) {
		Query query = getEntityManager().createQuery(
				"select model from " + getClassName()
						+ " model where model.id = " + id);
		if (query instanceof org.hibernate.ejb.QueryImpl) {
			((org.hibernate.ejb.QueryImpl) query).getHibernateQuery()
					.setCacheable(true);
			return (Formwork) query.getSingleResult();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Formwork> findAll() {
		List<Formwork> lists = new ArrayList<Formwork>();
		Query query = getEntityManager().createQuery(
				"select model from " + getClassName() + " model");
		try {
			if (query instanceof org.hibernate.ejb.QueryImpl) {
				((org.hibernate.ejb.QueryImpl) query).getHibernateQuery()
						.setCacheable(true);
				return query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
}
