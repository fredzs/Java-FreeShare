package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.free4lab.freeshare.model.data.Group;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class GroupModelDAO extends AbstractDAO<GroupModel> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return GroupModel.class;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	private static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	@SuppressWarnings("unchecked")
	public List<GroupModel> getAllFreeGroups(){
		try{
			String queryString = "select model from " + getClassName()
					+ " model ";
			Query query = getEntityManager().createQuery(queryString);
		
			return query.getResultList();
		}catch(Exception e){
			log("find FreeShare_Group model failed!", Level.SEVERE, e);
		}
		return null;
	}
	public GroupModel getFreeshareGroup() {
		try{
			return getGroupByUuid("54811782-122a-48b2-9224-041db3daa61c");
		}catch(Exception e){
			log("find FreeShare_Group model failed!", Level.SEVERE, e);
		}
		return null;
	}
	public GroupModel addGroupModel( String groupInfo, String uuid, String authToken,String extend){
		GroupModel g=new GroupModel();
		g.setGroupInfo(groupInfo);
		g.setUuid(uuid);
		g.setAuthToken(authToken);
		g.setExtend(extend);
		Timestamp time = new Timestamp(new Date().getTime());
		g.setTime(time);
		save(g);
		return g;
	}
	
	public GroupModel setGroupInfo(Group group){
		GroupModel gm = getGroupByUuid(group.getUuid());
		gm.setGroupInfo(group.getGroupInfo().toString());
		System.out.println("setGroupInfo:"+group.getExtend());
		gm.setExtend(group.getExtend());
		update(gm);
		return gm;
	}
	public GroupModel getGroupByUuid(String uuid) {
		try{
			String queryString = "select model from " + getClassName()
					+ " model where model.uuid=:value";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", uuid);
			return (GroupModel) query.getSingleResult();
		}catch(Exception e){
			log("find model by uuid failed! And the uuid is " + uuid, Level.SEVERE, e);
		}
		return null;
	}
	public void delGroupModel(String uuid){
		EntityManager em = getEntityManager();

        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.getTransaction().begin();
		try{
			String queryString = "delete from GroupModel model where model.uuid=:value";
			Query query = em.createQuery(queryString);
			query.setParameter("value", uuid);
			query.executeUpdate();
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			log("delete model by uuid failed!", Level.SEVERE, e);
		}
	}
}
