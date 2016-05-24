package com.free4lab.freeshare.action.group.administration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sun.tools.jstat.Operator;

import com.free4lab.freeshare.action.BaseAction;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.dao.VerifyUser;
import com.free4lab.freeshare.model.dao.VerifyUserDAO;
import com.free4lab.freeshare.model.data.Group;

/**
 * @author 
 * 对组员的审核或者邀请组员的处理
 */
public class AgreeInAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AgreeInAction.class);
	
	private String email;
	private String uuid;
	private List<VerifyUser> vList;
	private List<Group> groups;
	private Integer groupId;
	//private String operationList ;
	
	

	public String execute(){
		setEmail(getSessionEmail());
		logger.info("getSessionEmail()"+getSessionEmail());
		groups = new ArrayList<Group>();
		vList = VerifyUserManager.findVerifyUserByEmail(email);
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
		return SUCCESS;
	}
	public void deleteInviteMem() {
		logger.info("deleteInviteMem");
		logger.info("groupId:"+groupId);
		logger.info("email:"+email);
		VerifyUserDAO dao = new VerifyUserDAO();
		String[] idOrEmail = email.split(",");
		Integer i;
		for(i = 0;i < idOrEmail.length;i++){
			if(checkInteger(idOrEmail[i])){
				dao.delete(groupId, Integer.parseInt(idOrEmail[i]));
			}
			else{
				dao.delete(groupId, idOrEmail[i].substring(1, idOrEmail[i].lastIndexOf("'")));
			}
		}
	}
	private boolean checkInteger(String elem) {
		try{
			Integer.parseInt(elem);		
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public List<VerifyUser> getvList() {
		return vList;
	}
	public void setvList(List<VerifyUser> vList) {
		this.vList = vList;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/*public String getOperationList() {
		return operationList;
	}
	public void setOperationList(String operationList) {
		this.operationList = operationList;
	}*/
}
