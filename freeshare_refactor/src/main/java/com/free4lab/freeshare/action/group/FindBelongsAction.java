package com.free4lab.freeshare.action.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.TagsManager;
import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.TagObject;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

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
	private Integer uid;
	private Integer resourceType;
	private List<Group> groupList;
	private List<TagObject> toList;

	public String findBelongGroups3()
	{
		Integer userId = new BaseAction().getSessionUID();
		String company = null;
		groupList = GroupUserManager.getMyGroups(uid);
		String token = getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> ui = UserInfoUtil.getUserInfoByUid(token, userList);
		if(ui != null)
		{
			company = ui.get(0).getCompany();
		}
		if(!company.equals("null"))
		{
			Iterator<Group> it = groupList.iterator();
		
			while(it.hasNext()){
				Group group =it.next();
				if(!group.getExtend().equals(company)){
					it.remove();
				}
		}
			
		}
		
		System.out.println("该用户所属的l.size()："+uid+"是"+groupList.size());
		System.out.println("得到用户所属的全部组");
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


	public List<TagObject> getToList() {
		return toList;
	}

	public void setToList(List<TagObject> toList) {
		this.toList = toList;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}


}
