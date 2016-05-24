package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.ListRelationManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.ResourcePermissionManager;
import com.free4lab.freeshare.manager.TagsManager;
import com.free4lab.freeshare.manager.factory.ResourceFactory;
import com.free4lab.freeshare.manager.factory.ResourcePermissionFactory;
import com.free4lab.freeshare.model.dao.ListRelation;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.TagObject;

/**
 * 获取资源的归属：归属列表、归属群组、归属那些标签
 * @author zhaowei
 *
 */
public class FindBelongsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2730112883638320677L;
	private Integer id;
	private Integer resourceType;
	private List<Group> groupList;
	private List<Resource> listsList;
	private List<TagObject> toList;
	
	public String findBelongGroups() {
		ResourcePermissionManager pm = new ResourcePermissionManager() ;
		groupList = new ArrayList<Group>();
		if (id != null) {
			List<Integer> groupIdList = pm.selectWritableGroups(id);
			groupList = GroupManager.getGroups(groupIdList);
		}
		return SUCCESS;
	}
	//需要将隐私组进行隐藏。
	public String findBelongGroups2() {
		ResourcePermissionManager pm = new ResourcePermissionManager() ;
		groupList = new ArrayList<Group>();
		if (id != null) {
			List<Integer> groupIdList = pm.selectWritableGroups(id);
			groupList = GroupManager.getSearchableGroups(groupIdList);
		}
		return SUCCESS;
	}
	public String findBelongLists() {
		
		listsList = new ArrayList<Resource>();
		
		if (id != null) {
			List<ListRelation> listrelations = (new ListRelationManager()).obtainRelationByResource(id);
			
			if (listrelations != null) {
				if (listrelations.size() > 0) {
					for (ListRelation r : listrelations) {
						Resource re = ResourceFactory.getInstance()
								.getResource(r.getListId());
						if (re != null) {
							listsList.add(re);
						}
					}
				}
			}
			else
			{
				System.out.println("listrelations为空");
			}
		}
		return SUCCESS;
	}

	public String findBelongLabels() {
		toList = new ArrayList<TagObject>();
		if (id != null && resourceType != null) {
			try {
				JSONObject json = new JSONObject();
				json.put("resourceId", id);
				json.put("resourceType", resourceType);
				toList = new TagsManager().valLabelList(json.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public List<Resource> getListsList() {
		return listsList;
	}

	public void setListsList(List<Resource> listsList) {
		this.listsList = listsList;
	}

	public List<TagObject> getToList() {
		return toList;
	}

	public void setToList(List<TagObject> toList) {
		this.toList = toList;
	}

	public static void main(String[] args) {  
		  System.out.println("Hello World!"); 
		  FindBelongsAction fba = new FindBelongsAction();
		  fba.id = 909;
		  fba.findBelongLists();
		 /* for(Resource r:fba.listsList)
		  {
			  System.out.println("listsList:"+r.getId());
		  }*/
		  
	}

}
