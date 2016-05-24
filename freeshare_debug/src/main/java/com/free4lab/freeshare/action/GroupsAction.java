package com.free4lab.freeshare.action;

import java.util.*;

import org.apache.log4j.Logger;


import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.dao.*;

/*
 * GetAllGroups该类根据存在session我的用户id，来获取我的所属的/创建的/我管理的群组。
 * 通过type进行区分
 * type = null为我所属的群组；
 * type = 2为我创建的群组；
 * type = 1为我管理的群组；
 * 
 * @param getSessionUID()
 * @return groupList到groups.jsp
 * */
public class GroupsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GroupsAction.class);
	private String name;
	private String desc;
	private String time;
	private Integer type;//1:管理员；2：创建者
	private List<Group> groupList;
	private Map<Integer,String> map;
	private Integer inviteNum;
	public Integer getInviteNum() {
		return inviteNum;
	}
	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}
	//private 
	public String execute(){
	
		if(getSessionUID()==null||GroupUserManager.getMyGroups(getSessionUID()) == null){
			return INPUT;
		}
		
		/**/
		groupList = GroupUserManager.getMyGroups(getSessionUID());
		inviteNum = getInviteMeGroups().size();
		System.out.println("inviteNum"+inviteNum);
		if(type == null){
			logger.info("得到我属于的组，包括我创建的和我管理的。");
			getMyGroups();
			
			
		}else{
			if(type == 3){	
				groupList = getInviteMeGroups();
				for(int i = 0;i < groupList.size();i++){
					System.out.println("grouplist i:"+groupList.get(i).getGroupId());
				}
				System.out.println("groupList num"+groupList.size());
				logger.info("得到邀请我的组。");
			}
			else if(type == 2){
				logger.info("得到我创建的组。");
				groupList = getOwnGroups();
			}
			else{
				logger.info("得到我管理的的组，当然包括我创建的组。");
				groupList = getManageGroups();
			}
		}
		return SUCCESS;
	}
	private List<Group> getInviteMeGroups(){
		
			logger.info("邀请我的群组。");
			//setEmail(getSessionEmail());
			logger.info("getSessionEmail()"+getSessionEmail());
			List<Group> groups = new ArrayList<Group>();
			List<VerifyUser> vList = VerifyUserManager.findVerifyUserByEmail(getSessionEmail());
			logger.info("vList.size():"+vList.size());
			if(vList != null&&vList.size()>0){
				for(VerifyUser vu: vList){
					logger.info("vu.getGroupId():"+vu.getGroupId());
					Group group= GroupManager.getSimpGroup(vu.getGroupId());
					if(group != null){
						groups.add(GroupManager.getSimpGroup(vu.getGroupId()));
					}
					
				}
			}
			
			logger.info("inviting groups size is " + groups.size());
			return groups;
		
	}
    private void getMyGroups(){
    	Iterator<Group> it = groupList.iterator();
    	//logger.info("groupList.size():"+groupList.size());
    	//groupPermissionMap = new HashMap<Integer,Integer>();
    	while(it.hasNext()){
			Group group =it.next();
			GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), group.getGroupId());
			if(gp!=null){
				//logger.info("before!");
				//groupPermissionMap.put(group.getGroupId(),gp.getType());
				group.setAuthToken("admin");
				
			}
		}
    	
    }
	
	private List<Group> getOwnGroups(){
		Iterator<Group> it = groupList.iterator();
		while(it.hasNext()){
			Group group =it.next();
			GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), group.getGroupId());
			if(gp==null||gp.getType() != 2){
				it.remove();
			}
		}
		return groupList;
	}
	private List<Group> getManageGroups(){
		Iterator<Group> it = groupList.iterator();
		while(it.hasNext()){
			Group group =it.next();
			GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), group.getGroupId());
			if(gp==null){
				it.remove();
			}
		}
		return groupList;
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
	public Map<Integer,String> getMap() {
		return map;
	}
	public void setMap(Map<Integer,String> map) {
		this.map = map;
	}
}
