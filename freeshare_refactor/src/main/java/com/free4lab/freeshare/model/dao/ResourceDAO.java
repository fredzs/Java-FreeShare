package com.free4lab.freeshare.model.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.apache.turbine.util.Log;
import org.hibernate.Session;

import com.free4lab.freeshare.util.TimeUtil;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class ResourceDAO extends AbstractDAO<Resource> {

	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return Resource.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	/* 创建一个资源，同时将创建的资源返回。 */
	public Resource create(Resource resource) {
		log("finding " + getClassName() + " instance: create a item",
				Level.INFO, null);
		try {
			save(resource);
		} catch (Exception e) {
			return null;
		}
		return resource;
	}

	/* 更新一个资源。 */
	public boolean updateResource(Resource resource) {
		Resource newResource = findByPrimaryKey(resource.getId());
		resource.setCreateTime(newResource.getCreateTime());
		resource.setRecentEditTime(TimeUtil.getCurrentTime());
		resource.setAuthorId(newResource.getAuthorId());
		// TODO: 作者可能要修改
		try {
			update(resource);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/* 删除一个资源。 */
	public boolean deleteResource(Integer id) {
		try {
			deleteByPrimaryKey(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 通过id获取单个资源
	public Resource getResource(Integer resourceId) {
		Resource resource = null;
		try {
			resource = findById(resourceId);
		} catch (Exception e) {
			return null;
		}
		return resource;
	}

	// 通过群组id和资源类型获取资源总数
	public Integer getGroupResourcesNum(List<Integer> typeList, Integer groupId) {
		Integer groupResourcesNum;
		try {
			String sqlString = "SELECT COUNT(*) FROM resource INNER JOIN resource_permission ON resource.id = resource_permission.resource_id WHERE resource_permission.group_id = "
					+ groupId + " and ( ";

			for (Integer type : typeList)
				sqlString = sqlString + " resource.resource_type = " + type + " or";

			sqlString = sqlString.substring(0, sqlString.length() - 2);

			sqlString = sqlString + " ) ";

			// 使用jpa接口无法满足需求
			Session session = (Session) getEntityManager().getDelegate();
			org.hibernate.Query query = session.createSQLQuery(sqlString);
			List l = query.list();

			groupResourcesNum = ((BigInteger) l.get(0)).intValue();
		} catch (Exception e) {
			log("ERROR", Level.SEVERE, e);
			return null;
		}
		return groupResourcesNum;
	}

	// 通过群组id和资源类型获取多个资源
	public List<Resource> getGroupResources(Integer page,List<Integer> typeList, Integer groupId) {
		List<Resource> resources = new ArrayList<Resource>();
		try {
			String sqlString = "from Resource as r, ResourcePermission as rp where r.id = rp.resource and rp.groupId = "
					+ groupId + " and ( ";

			for (Integer type : typeList)
				sqlString = sqlString + " r.type = " + type + " or";

			sqlString = sqlString.substring(0, sqlString.length() - 2);

			sqlString = sqlString + " ) order by r.createTime desc";

			// 使用jpa接口无法满足需求
			Session session = (Session) getEntityManager().getDelegate();
			org.hibernate.Query query = session.createQuery(sqlString);
			query.setFirstResult((page - 1) * 10);
			query.setMaxResults(10);
			List l = query.list();

			for (Object o : l) {
				resources.add((Resource) ((Object[]) o)[0]);
			}
		} catch (Exception e) {
			log("ERROR", Level.SEVERE, e);
			return null;
		}
		return resources;
	}
	
	// 通过作者ID和资源类型获得我的资源总数
		public Integer getResourceNum(List<Integer> typeList, Integer authorId) {
			Integer num;
			try {
				String sqlString = "select count(*) from resource where resource.author_Id=" + authorId + " and ( ";

				for (Integer type : typeList)
					sqlString = sqlString + " resource.resource_type = " + type + " or";

				sqlString = sqlString.substring(0, sqlString.length() - 2);

				sqlString = sqlString + " )";

				// 使用jpa接口无法满足需求
				Session session = (Session) getEntityManager().getDelegate();
				org.hibernate.Query query = session.createSQLQuery(sqlString);
				List l = query.list();

				num = ((BigInteger) l.get(0)).intValue();
				
			} catch (Exception e) {
				log("ERROR", Level.SEVERE, e);
				return null;
			}
			return num;
		}

	// 通过作者ID和资源类型获得多个资源
	public List<Resource> getResource(Integer page,List<Integer> typeList, Integer authorId) {
		List<Resource> resources = new ArrayList<Resource>();
		try {
			String sqlString = "select model from " + getClassName()
					+ " model where model.authorId=" + authorId + " and ( ";

			for (Integer type : typeList)
				sqlString = sqlString + " model.type = " + type + " or";

			sqlString = sqlString.substring(0, sqlString.length() - 2);

			sqlString = sqlString + " ) order by model.createTime desc";
			
			Query query = getEntityManager().createQuery(sqlString);
			query.setFirstResult((page - 1) * 10);
			query.setMaxResults(10);
			resources = query.getResultList();
		} catch (Exception e) {
			log("ERROR", Level.SEVERE, e);
			return null;
		}
		return resources;
	}

	public static void main(String[] args) {
		ResourceDAO dao = new ResourceDAO();
		List<Integer> typeList = new ArrayList<Integer>();
		List<Integer> resultList = new ArrayList<Integer>();
		Integer authorId = 430;
		typeList.add(2);
		typeList.add(1);
		dao.getGroupResourcesNum(typeList,265);
		System.out.println("??????????????????????????????????");
		System.out.println(resultList);
	}

	// TODO:没有测试过
	/* 获取多个资源 */
	public List<Resource> getMutilResource(List<Integer> resourceIds) {
		List<Resource> resources = new LinkedList<Resource>();
		try {
			for (Integer resourceId : resourceIds) {
				resources.add(findById(resourceId));
			}
		} catch (Exception e) {
			return null;
		}
		return resources;
	}
}
