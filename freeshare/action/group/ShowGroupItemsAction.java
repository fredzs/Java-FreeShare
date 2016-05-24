package com.free4lab.freeshare.action.group;


import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.dao.VerifyUserDAO;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.util.MessageUtil;
import com.free4lab.utils.account.UserInfo;
import com.opensymphony.xwork2.ActionContext;


public class ShowGroupItemsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319021815980163997L;
	private static final Logger logger = Logger.getLogger(ShowGroupItemsAction.class);
	private Integer groupId;
	private String uuid;
	private String name;
	private String groupInfo;
	
	
	private Integer type;//是否是管理员
	private String groupAvatar;
	private SrhResult srhResult;
	private Integer page;
	private Integer lastPage;
	
	private static final Integer SIZE = 11;
	private String resourceTypeList;
	//private String searchType;
	private static final String DOWNLOAD_URL = "http://freedisk.free4lab.com/download?uuid=";
	
	public String getGroupHead(){
		Group group = GroupManager.getSimpGroup(groupId);
		
		setName(group.getGroupInfo().get("name"));
		if(!GroupUserManager.checkMember(getSessionUID(), groupId)){
			System.out.println("check member false!"+getSessionUID());
			if(group.getExtend() == "public")
			{
				if(isRedirect(group)){
					return SUCCESS;
				}
			}
		}
		setUuid(group.getUuid());
		
		setGroupInfo(group.getGroupInfo().get("desc"));
		if(group.getGroupInfo().toString().contains("avatar")){
			setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));
		}
		type = (Integer)ActionContext.getContext().getContextMap().get("type");
		return SUCCESS;
		
	}
	public String execute(){
		
		type = (Integer)ActionContext.getContext().getContextMap().get("type");
		//无参数page时，默认为1
		if (null == page){
			setPage(1);		
		}
		if(type.equals(UserType.TYPE_APPLY)||type.equals(UserType.TYPE_INVITED)){
			logger.info("not a member!");
			return SUCCESS;
		}
		String tags ="";
		tags = returnTags(resourceTypeList);

		setSrhResult(SearchManager.searchItems(NULL_QUERY, 
				page, SIZE, tags));
		
		if (null == getSrhResult()){
			logger.error("search result is null!");
			return INPUT;
		}		
		setLastPage(srhResult.getTotalNum()/SIZE + 1);
		return SUCCESS;
	}

	private String returnTags(String rType) {
		String tags = TagValuesConst.pGROUP + groupId; 
		if (rType == null || rType.equals("all")) {
			return tags;
		} else {
			tags += " AND (" + rType.replaceAll(",", " OR ") + " )";
		}
		return tags;
	}
	
	/**
	 * 呈现团队的资源信息
	 */
	public String showGroupResource(){
	
		//setResourceTypeList(resourceTypeList);
		return execute();
	}
	
	/**
	 * 判断是否是通过注册进入该组。<br/>
	 * 是的话把该用户加入该组，返回true，指示下一步跳转；否则返回false，什么也不做
	 */
	private boolean isRedirect(Group group){
		ActionContext context = ActionContext.getContext();
		
		String[] params = (String[]) context.getParameters().get("from");
		
		String title = getSessionUserName()+"加入了"+name+"群组";
		String url = "http://freeshare.free4lab.com/group/getgroup?groupId="+groupId+"&kind=0";
		String hrefurl ="<a href="+url+">"+url+"</a>";
		String content = title+"。可以通过"+hrefurl+"进行查看。";
		
		boolean condition  = (params != null && params[0].equalsIgnoreCase("register"))||(group.getExtend().equals("freeIn"));
		
		if(condition == true){
			GroupUserManager.addMembers(group, getSessionUID());
			List<UserInfo> users = GroupUserManager.getAdmin(group.getGroupId(),getSessionToken());
			List<String> emails = new LinkedList<String>();
			List<Integer> userIds = new LinkedList<Integer>();
			for(UserInfo user:users){
				emails.add(user.getEmail());
				userIds.add(Integer.parseInt(user.getUserId()));
			}
			
			try {
				SendMail.sendMail(emails, title, content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MessageUtil.sendMessage(title, url, userIds);
			return true;
		}
		return false;
	}
	public String getResourceTypeList() {
		return resourceTypeList;
	}
	public void setResourceTypeList(String resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public SrhResult getSrhResult() {
		return srhResult;
	}

	public void setSrhResult(SrhResult srhResult) {
		this.srhResult = srhResult;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getGroupAvatar() {
		return groupAvatar;
	}
	public void setGroupAvatar(String groupAvatar) {
		this.groupAvatar = groupAvatar;
	}	
	
}
