package com.free4lab.freeshare.action.group.administration;

import java.util.*;

import org.apache.log4j.Logger;


import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.dao.*;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

/*
 * GetAllGroups该类根据存在session我的用户id，来获取我管理的群组。
 * 通过type进行区分 
 * type = 2为我创建的群组；
 * @param getSessionUID()
 * @return groupList到groupadmin.jsp
 * */
public class DepartmentAdminAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DepartmentAdminAction.class);
	private String name;
	private String desc;
	private String time;
	private Integer type;//1:管理员；2：创建者
	private List<Group> groupList;
	private String groupIds;
	private String company;

	public String execute(){
		Integer userId = new BaseAction().getSessionUID();
		String token = new BaseAction().getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> m = UserInfoUtil.getUserInfoByUid(token, userList);
		if(m != null)
		{
		    company= m.get(0).getCompany();
		}
		if(getSessionUID()==null||GroupUserManager.getMyGroups(getSessionUID()) == null){
			return INPUT;
		}	
		groupList = GroupUserManager.getMyGroups(getSessionUID());

		if(type == 2){
			logger.info("得到我创建的组。");
			groupList = getOwnCompanyGroups();
			findBelongGroups3();
			logger.info("groupIds"+groupIds);
			logger.info(groupList.size());
			System.out.println("groupList.size():"+groupList.size());
		}
		else
		{
			return null;
		}
		return SUCCESS;
	}
	
	private List<Group> getOwnCompanyGroups()
	{
		groupList = GroupManager.getCompanyGroups();
		Iterator<Group> it = groupList.iterator();
		/*while(it.hasNext()){
			Group group =it.next();
			GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), group.getGroupId());
			if(gp==null||gp.getType() != 2){
				it.remove();
			}	
		}*/
		
		System.out.print("得到我公司的组");
		return groupList;
	}
	public String findBelongGroups3()
	{
		Integer userId = getSessionUID();
		groupIds = "";
		if(groupList != null)
		{
			for(Group g:groupList){
				groupIds = groupIds + g.getGroupId()+",";
			}
			if(groupIds.length() > 0)
				groupIds = groupIds.substring(0, groupIds.length()-1) ;
		}
		System.out.println("该用户所属的groupList.size()："+userId+"是"+groupList.size());
		System.out.println("groupIds:"+groupIds);
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
