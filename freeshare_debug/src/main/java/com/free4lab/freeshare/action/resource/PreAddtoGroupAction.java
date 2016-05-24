package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.GroupBrief;

public class PreAddtoGroupAction extends BaseAction {
	private static Logger logger = Logger.getLogger(PreAddtoGroupAction.class);
	public String getSelectedvalue() {
		return selectedvalue;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}

	private List<GroupBrief> lists; 			//“我的群组”
	private String selectedvalue;
	public String execute(){
		Integer uid = this.getSessionUID();	
		//----------------------------------------------------------
		logger.info("selectedvalue"+selectedvalue);
		//获取“最近使用的群组”		
		//从数据库读取用户的最近使用过的群组记录
		UserLatestList userLatestList = new UserLatestListDAO().findLatestListByUid(uid);
		
		if(userLatestList == null){
			return SUCCESS;
		}		
		logger.info(userLatestList.getLatestGroup());
		String groupStr = userLatestList.getLatestGroup();
		String[] groupArray = groupStr.split(",");
		logger.info(groupArray);
		//根据群组id的记录，获取群组信息
		Group model = null;
		lists = new ArrayList<GroupBrief>();
		List<Group> groupList = GroupUserManager.getMyGroups(uid);
		List<Integer> myGroupID = new ArrayList<Integer>();
		for(Group gl:groupList){
			myGroupID.add(gl.getGroupId());
		}
		//logger.info(groupStr);
		for(int i=0;i<groupArray.length;i++){
			if(groupArray[i] != "" && !groupArray[i].equals("")){
				model = GroupManager.getSimpGroup(Integer.parseInt(groupArray[i]));
				if(model != null){
					if(myGroupID.contains(model.getGroupId())){
						lists.add(new GroupBrief(model.getGroupId(), model.getGroupInfo().get("name")));
					}else{
						groupStr = groupStr.replace(","+groupArray[i]+",", ",");
					}
				}else{
					groupStr = groupStr.replace(","+groupArray[i]+",", ",");
				}
			}			
		}
		//logger.info(groupStr);
		(new UserLatestListDAO()).updateLatestGroup(getSessionUID(), groupStr);
		return SUCCESS;		
	}
	
	public String myGroup(){
		Integer uid = this.getSessionUID();	
		//----------------------------------------------------------
		//获取“我的群组”
		List<Group> groupList = GroupUserManager.getMyGroups(uid);
		lists = new ArrayList<GroupBrief>();
		for (Group model : groupList) {
			lists.add(new GroupBrief(model.getGroupId(), model.getGroupInfo().get("name")));
		}
		return SUCCESS;	
	}

	public List<GroupBrief> getLists() {
		return lists;
	}

	public void setLists(List<GroupBrief> lists) {
		this.lists = lists;
	}

}
