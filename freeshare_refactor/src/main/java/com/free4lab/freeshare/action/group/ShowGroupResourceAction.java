package com.free4lab.freeshare.action.group;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.freeshare.util.MessageUtil;
import com.free4lab.utils.account.UserInfo;
import com.opensymphony.xwork2.ActionContext;
public class ShowGroupResourceAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5319021815980163997L;
	private static final Logger logger = Logger.getLogger(ShowGroupResourceAction.class);
	private Integer groupId;
	private String uuid;
	private String name;
	private String groupInfo;
	
	
	private Integer type;//是否是管理员
	private String groupAvatar;
	private List<ResourceWrapper> resource = new LinkedList<ResourceWrapper>();
	private Integer page;
	private Integer lastPage;
	private Integer num;
	private static final Integer SIZE = 10;
	private String resourceTypeList;
	private static final String DOWNLOAD_URL = URLConst.APIPrefix_FreeDisk + "/download?uuid=";
	
	public String getGroupHead(){
		Group group = GroupManager.getSimpGroup(groupId);
		setName(group.getGroupInfo().get("name"));
		type = (Integer)ActionContext.getContext().getContextMap().get("type");
		if( ! GroupUserManager.checkMember(getSessionUID(), groupId)){
			if( group.getExtend().equals("public")||group.getExtend().equals("normal"))
			{
				if(isRedirect(group)){
					setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));
					type = (Integer)ActionContext.getContext().getContextMap().get("type");
					return SUCCESS;
				}			
			}
			if(type == 0)
				return INPUT;
			logger.info("action，不是群组成员");
		}else{
			setUuid(group.getUuid());
			setGroupInfo(group.getGroupInfo().get("desc"));
			if(group.getGroupInfo().toString().contains("avatar")){
				setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));
			}
			type = (Integer)ActionContext.getContext().getContextMap().get("type");
			return SUCCESS;
		}
		return NONE;
	}
	/**
	 * 呈现团队的资源信息
	 */
    public String showGroupResource(){
		type = (Integer)ActionContext.getContext().getContextMap().get("type");
		//无参数page时，默认为1
        page = (page == null) ? 1 : page;
    	  
		if(type.equals(UserType.TYPE_APPLY)||type.equals(UserType.TYPE_INVITED)){
			logger.info("not a member!");
			return SUCCESS;
		}
		logger.info("群组资源数"+num);
		if(num == null){
			num = new ResourceManager().getGroupResourceWrapperNum(getTypeList(), groupId);
			logger.info(num);
		}
		
		resource = new ResourceManager().readGroupResourceWrapper(page,getTypeList(), groupId);
		logger.info(resource);
		
		if (null == resource){
			logger.error("search result is null!");
			return INPUT;
		}		
		setLastPage(num/SIZE + 1);
		return SUCCESS;
	}
    
    public static void main(String[] args){
    	
    }
      
      private List<Integer> getTypeList(){
  		List<Integer> typeList = new ArrayList<Integer>();
  		String tmp = resourceTypeList;
  		while(tmp.length() != 0){
  			String s = tmp.substring(tmp.lastIndexOf(',')+1, tmp.length());
  			Integer test = Integer.parseInt(s);
  			typeList.add(test);
  			if(tmp.lastIndexOf(',')>0)
  				tmp = tmp.substring(0,tmp.lastIndexOf(','));
  			else
  				tmp = "";
  		}	
  		logger.info(typeList);
  		return typeList;
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
		
		boolean condition  = (params != null && params[0].equalsIgnoreCase("register"))||(group.getExtend().equals("public"));
		
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

	public List<ResourceWrapper> getResource() {
		return resource;
	}
	public void setResource(List<ResourceWrapper> resource) {
		this.resource = resource;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}	
}
