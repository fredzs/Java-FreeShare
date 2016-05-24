package com.free4lab.freeshare.action.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.data.BasicContent;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.GroupInfo;

/**
 * @author 
 * 更新组的描述
 */
public class UpdateGroupInfo extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	private String permission;
	
	private Integer groupId;//前端传值
	private Map<Integer,String> avatarMap;
	private static final String GROUP_URL = "group/items?groupId=";
	private static final Logger logger = Logger.getLogger(UpdateGroupInfo.class);
	
	public String prepare(){
		Group group = GroupManager.getSimpGroup(groupId);
		setName(group.getGroupInfo().get("name"));
		setDesc(group.getGroupInfo().get("desc"));
		setPermission(group.getExtend());
		return SUCCESS;
	} 
	public String execute() {
		Group group = GroupManager.getSimpGroup(groupId);
		GroupInfo infomation = group.getGroupInfo();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", name);
		m.put("desc", desc);
		try{
			String avatar = infomation.get("avatar");
			m.put("avatar", avatar);
			
		}
		catch(Exception e){
			System.out.println("avatar exception.");
		}
		try{

			String date = infomation.get("date");
			m.put("date", date);
		}
		catch(Exception e){
			System.out.println("date exception.");
		}

		String groupInfo = m.toString();
		GroupInfo info = new GroupInfo(groupInfo);

		group.setGroupInfo(info);
		logger.info("permission:"+getPermission());
		logger.info("extend:"+group.getExtend());
		
		String url = GROUP_URL+groupId;
		List<Integer> groupIds = new ArrayList<Integer>();
		groupIds.add(groupId);
		//1、如果是隐私组，设为公开或者自由加入群组，都需要添加索引。是隐私组，就没有其他动作。
		if(group.getExtend().equals("private")){
			if(!getPermission().equals("private")){			
				addNewIndex(url);
			}			
		}
		else {
			//2、如果是公开组或者自由加入组，改成是隐私组，就删除索引，如果是公开或者公开组，没有其他动作。
			if(getPermission().equals("private")){
				new NewIndexManager().delIndex(url);
			}
		}
		System.out.println("getPermission():"+getPermission());
		group.setExtend(getPermission());
	/*	
		if(getPermission().equals(group.getExtend()) == false){
			group.setExtend(getPermission());
			logger.info("extend2:"+group.getExtend());

			if(getPermission().equals("public")== true){
				logger.info("permission equal public");
				//TODO 增加索引
				addNewIndex(url);
			}
			else{
				logger.info("clearUrl");
				//TODO 增加索引
			}
		}
		else{//如果权限没有改，但是index的内容还是要改的。
			if(getPermission().equals("public")== true){
				logger.info("permission equal public2");
				//TODO 增加索引),groupIds);
			}
		}*/
		GroupManager.updateGroupInfo(group);
		return SUCCESS;
	}
	private void addNewIndex(String url){
		
		Integer creatorId = getGroupCreatorUid();
		BasicContent bContent = new BasicContent(creatorId, desc, ResourceTypeConst.TYPE_GROUP, BehaviorConst.TYPE_CREATE);
		List<String> tagValues = new ArrayList<String>();
		tagValues.add(TagValuesConst.pAUTHOR + creatorId);
		tagValues.add(TagValuesConst.FMT_GROUP);
		new NewIndexManager().addIndex(url, name, bContent.toString(), tagValues);
	}
	private Integer getGroupCreatorUid(){
		return GroupUserManager.getCreator(groupId);
		
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
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Map<Integer,String> getAvatarMap() {
		return avatarMap;
	}
	public void setAvatarMap(Map<Integer,String> avatarMap) {
		this.avatarMap = avatarMap;
	}

}
