package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.*;

import com.free4lab.utils.sql.*;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class CompanyDAO extends AbstractDAO<Company> {
	
	public static final String PU_NAME = "FreeSharePU";
	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return Company.class;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}
	
	//插入一条数据记录
	public Company create(Integer companyId,String companyName,Integer adminUid,String notice){
		log("finding " + getClassName() + " instance: create a company",
				Level.INFO, null);
		Company company =  new Company();
		company.setCompanyId(companyId);
		company.setCompanyName(companyName);
		company.setAdminUid(adminUid);
		company.setNotice(notice);
		save(company);
		return company;
		
	}
	
	//通过adminUid和companyId删除记录
	public boolean deleteAdmin(Integer companyId,Integer adminUid){
		log("finding" + getClassName() + " instance: "
				+ "del docversion by companyId:" + companyId + " and adminUid:"+adminUid, Level.INFO, null);
		EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {

			final String queryString = "delete from " + getClassName()
					+ " model where model.companyId = :companyId and model.adminUid = :adminUid";
			Query query = em.createQuery(queryString);
			query.setParameter("companyId", companyId);
			query.setParameter("adminUid", adminUid);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		} catch (RuntimeException re) {
			log("del records by companyId:"+companyId+" and adminUid:" + adminUid + " failed!",
					Level.INFO, re);
			em.getTransaction().rollback();
			return false;
		}
	}
	
	//通过companyId查找一个company的所有admin
	public List<Company> getAlladminsBycompanyId(Integer companyId){
		List<Company> companys = null;
		try {
			companys = new CompanyDAO().findByProperty("companyId", companyId);
		} catch (Exception e) {
			return null;
		}
		return companys;
	}
	//通过uid查找一个用户的所有企业管理员身份
	
	//通过uid和companyId查找用户是否为该company的admin
	@SuppressWarnings("unchecked")
	public List<Company> getAdminByUidandCompanyId(Integer companyId,Integer uid){
		 log("finding " + getClassName() + " instance with companyId: "
	                + companyId + ", uid: " + uid, Level.INFO, null);
	        try {
	            final String queryString = "select model from " + getClassName() + " model where model.companyId = :companyId and model.adminUid = :uid";
	            Query query = getEntityManager().createQuery(queryString);
	            query.setParameter("companyId", companyId);
	            query.setParameter("adminUid", uid);
	            return (List<Company>) query.getResultList();
	        } catch (RuntimeException re) {
	            log("find by property name failed",
	                    Level.SEVERE, re);
	            throw re;
	        }
	}
	
}