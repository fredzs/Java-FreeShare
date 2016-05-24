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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PreAddtoGroupAction.class);
	
	//private List<GroupBrief> groupsBrief; 			//“我的群组”
	private List<GroupBrief> lists; //“我的群组” lists名称和前端框架相关，请勿修改
	private String selectedvalue;
	
	public String execute(){
		Integer uid = this.getSessionUID();		
		logger.info("selectedvalue"+selectedvalue);
		Group model = null;
		lists = new ArrayList<GroupBrief>();
		//获取“最近使用的群组”		
		// 从数据库读取用户的最近操作记录 ->userLatestList
		UserLatestList userLatestList = new UserLatestListDAO()
				.findLatestListByUid(uid);
		if (userLatestList == null) {
			return SUCCESS;
		}
		logger.info("getLatestGroup:"+userLatestList.getLatestGroup());
		
		// 从数据库读取用户的最近群组 -> groupArray
		String groupStr = userLatestList.getLatestGroup();
		String[] groupArray = groupStr.split(",");
		// 获取我的群组id -> myGroupID
		List<Group> groupList = GroupUserManager.getMyGroups(uid);
		List<Integer> myGroupID = new ArrayList<Integer>();
		for(Group gl:groupList){
			myGroupID.add(gl.getGroupId());
			logger.info("myGroup:"+gl.getGroupId());
		}
		//如果最近使用的群组不存在或者不是我的群组，从最近使用群组中除去 -> groupStr
		for(int i=0;i<groupArray.length;i++){
			if(groupArray[i] != "" && !groupArray[i].equals("")){
				model = GroupManager.getSimpGroup(Integer.parseInt(groupArray[i]));
				if(model != null){
					if(myGroupID.contains(model.getGroupId())){
						lists.add(new GroupBrief(model.getGroupId(), model.getGroupInfo().get("name")));
						logger.info("LatestGroupid:"+model.getGroupId()+"LatestGroupname:"+model.getGroupInfo().get("name"));
					}else{
						groupStr = groupStr.replace(","+groupArray[i]+",", ",");
					}
				}else{
					groupStr = groupStr.replace(","+groupArray[i]+",", ",");
				}
			}			
		}
		logger.info("LatestGroup:"+groupStr);
		//更新最近使用的群组
		(new UserLatestListDAO()).updateLatestGroup(uid, groupStr);
		return SUCCESS;		
	}
	
	public String myGroup(){
		Integer uid = this.getSessionUID();
		
		//获取“我的群组”
		List<Group> groupList = GroupUserManager.getMyGroups(uid);
		lists = new ArrayList<GroupBrief>();
		for (Group model : groupList) {
			lists.add(new GroupBrief(model.getGroupId(), model.getGroupInfo().get("name")));
			logger.info("LatestGroupid:"+model.getGroupId()+"LatestGroupname:"+model.getGroupInfo().get("name"));
		}
		return SUCCESS;	
	}

	public List<GroupBrief> getLists() {
		return lists;
	}

	public void setLists(List<GroupBrief> lists) {
		this.lists = lists;
	}
	
	public String getSelectedvalue() {
		return selectedvalue;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}
	
	/*public static void main(String[] arg)
	{
		//测试execute() 更改uid=122
		PreAddtoGroupAction paddtogroup = new PreAddtoGroupAction();
		paddtogroup.execute();
		
		//测试myGroup() 更改uid=122
		PreAddtoGroupAction paddtogroup = new PreAddtoGroupAction();
		paddtogroup.myGroup();
		
		
	}*/


}
