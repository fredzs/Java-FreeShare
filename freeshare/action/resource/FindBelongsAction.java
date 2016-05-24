package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.handler.ListHandler;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.RelationManager;
import com.free4lab.freeshare.manager.TagsManager;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.manager.permission.IPermissionManager;
import com.free4lab.freeshare.model.dao.Lists;
import com.free4lab.freeshare.model.dao.Relation;
import com.free4lab.freeshare.model.data.Group;
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
	private List<Lists> listsList;
	private List<TagObject> toList;

	public String findBelongGroups() {
		IPermissionManager pm = PermissionFactory
				.getPermissionManager(resourceType);
		groupList = new ArrayList<Group>();
		if (id != null) {
			List<Integer> groupIdList = pm.findWriteTeams(id);
			groupList = GroupManager.getGroups(groupIdList);
		}
		return SUCCESS;
	}
	//需要将隐私组进行隐藏。
	public String findBelongGroups2() {
		IPermissionManager pm = PermissionFactory
				.getPermissionManager(resourceType);
		groupList = new ArrayList<Group>();
		if (id != null) {
			List<Integer> groupIdList = pm.findWriteTeams(id);
			groupList = GroupManager.getSearchableGroups(groupIdList);
		}
		return SUCCESS;
	}
	public String findBelongLists() {
		listsList = new ArrayList<Lists>();
		if (id != null) {
			List<Relation> relations = (new RelationManager()).getsByItem(id);
			if (relations.size() > 0) {
				for (Relation r : relations) {
					Lists l = new ListHandler().getLists(r.getListId());
					if(l!=null){
						listsList.add(l);
					}
				}
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

	public List<Lists> getListsList() {
		return listsList;
	}

	public void setListsList(List<Lists> listsList) {
		this.listsList = listsList;
	}

	public List<TagObject> getToList() {
		return toList;
	}

	public void setToList(List<TagObject> toList) {
		this.toList = toList;
	}


}
