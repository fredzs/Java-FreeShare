package com.free4lab.freeshare.action.group;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.action.BaseAction;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.data.BasicContent;
import com.free4lab.freeshare.model.data.GroupInfo;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.util.GroupUserListUtil;
import com.free4lab.freeshare.util.RedundancyDealUtil;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;



/*
 * 创建一个群组，其中提供两种方式的创建，
 * 一种是填写群组信息之后，直接完成创建。
 * 一种是在创建好，直接选择添加好友。
 * @param userList ：是跳转的页面呈现邀请的成员的列表；
 * futureMembers：在邀请列表中呈现的是该session用户所属组中的成员；
 * groupId：表示的是创建的新组的id；
 * group：表示创建的新组；
 *       
 * */
public class CreateGroupAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(CreateGroupAction.class);
	private String uuid;
	private String authToken;
	private String name;
	private String desc;
	private String avatar;
	private File photo;
	private String photoContentType;
	private String photoFileName;
	private List<UserInfo> members;
	// 可选的成员。
	private List<UserInfo> futureMembers;
	private String myName;
	

	private Group group;
	private Integer groupId;
	private String groupName;
	private String groupPermission;
	private static final String GROUP_URL = "group/resource?groupId=";
	private final static String DOWNLOAD_URL = URLConst.APIPrefix_FreeDisk + "/download?uuid=";

	public String execute() {

		if (createGroup()) {
			return SUCCESS;
		} else
			return INPUT;
	}

	/**
	 * 创建一个新的群组 向GroupManager提出请求，获取Uuid和AuthToken， 并将创建者加入到成员中，并设置为管理者。
	 * 
	 * @return true/false,表示是否创建成功。
	 */
	public boolean createGroup() {

		group = GroupManager.createGroup(groupPermission);
		logger.info("groupPermission:"+groupPermission);
		setUuid(group.getUuid());
		setAuthToken(group.getAuthToken());
		logger.info("创建组成功，返回group的uuid、authToken。下一步写入组描述信息。");
		if (setGroupInfo()) {

			GroupUserManager.setAdmin(group.getGroupId(), getSessionUID(), 2);

			List<Integer> list = new ArrayList<Integer>();
			list.add(getSessionUID());
			group.setMemberList(list);
			GroupUserManager.addMembers(group, getSessionUID());
			setGroupId(group.getGroupId());
			List<Integer> groupIds = new ArrayList<Integer>();
			groupIds.add(groupId);
			String url = GROUP_URL + groupId;
			if (groupPermission.equals("public")||groupPermission.equals("normal")) {
				
				
				addNewIndex(url);
			}
			

			// TODO：暂时不能将新闻打印上索引，因为不能在news中呈现。格式不一致。
			logger.info("创建组成功！");
			return true;
		} else {
			return false;
		}
	}
	
	public String createGroup2() {
		Integer userId = getSessionUID();
		String token = getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> ui = UserInfoUtil.getUserInfoByUid(token, userList);
		if(ui != null)
		{
			groupPermission = ui.get(0).getCompany();
		}
		//groupPermission = "company";
		if(groupPermission != null)
		{
			group = GroupManager.createGroup(groupPermission);
		}
		logger.info("groupPermission:"+groupPermission);
		setUuid(group.getUuid());
		setAuthToken(group.getAuthToken());
		logger.info("创建组成功，返回group的uuid、authToken。下一步写入组描述信息。");
		if (setGroupInfo()) {

			GroupUserManager.setAdmin(group.getGroupId(), getSessionUID(), 2);

			List<Integer> list = new ArrayList<Integer>();
			list.add(getSessionUID());
			group.setMemberList(list);
			GroupUserManager.addMembers(group, getSessionUID());
			setGroupId(group.getGroupId());
			List<Integer> groupIds = new ArrayList<Integer>();
			groupIds.add(groupId);
			String url = GROUP_URL + groupId;
			if (groupPermission.equals("public")||groupPermission.equals("normal")) {
				
				
				addNewIndex(url);
			}
			

			// TODO：暂时不能将新闻打印上索引，因为不能在news中呈现。格式不一致。
			logger.info("创建组成功！");
			return SUCCESS;
		} else {
			return INPUT;
		}
	}

	private void addNewIndex(String url){
		BasicContent bContent = new BasicContent(getSessionUID(), desc, ResourceTypeConst.TYPE_GROUP, BehaviorConst.TYPE_CREATE);
		List<String> tagValues = new ArrayList<String>();
		tagValues.add(TagValuesConst.pAUTHOR + getSessionUID());
		tagValues.add(TagValuesConst.FMT_GROUP);
		new NewIndexManager().addIndex(url, name, bContent.toString(), tagValues);
	}
	/**
	 * 提供创建的时候就直接邀请成员的入口。
	 * 
	 */
	public String inviteMembers() {

		if (createGroup()) {
		
			group = GroupManager.getFullGroup(groupId);
			groupPermission = group.getExtend();
			logger.info("groupPermission"+groupPermission);
			setGroupName(group.getGroupInfo().get("name"));

			members = GroupUserListUtil.getGroupUserListByGroupId(groupId,getSessionToken());
            myName = getSessionUserName();
				
			return SUCCESS;
		} else
			return INPUT;

	}

	/*
	 * 对群组信息进行修改。
	 * 
	 * @return true/false
	 */
	private boolean setGroupInfo() {
		if (avatar.contains("http:")) {
			avatar = avatar.substring(avatar.lastIndexOf("=") + 1);
		}
		
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		String realTime = time.toString();

		int index = realTime.indexOf(" ");
		String today = realTime.substring(0, index);
		Map<String, String> m = new HashMap<String, String>();
		// 前后端限制长度
		if (name.length() < 20 && desc.length() <= 250) {
			m.put("name", name);
			m.put("date", today);
			m.put("desc", desc);
			if (avatar != null && !avatar.equals("")) {
				m.put("avatar", avatar);
			}
			String groupInfo = m.toString();
			GroupInfo info = new GroupInfo(groupInfo);
			Group g = new Group(uuid, authToken);
			g.setGroupInfo(info);
			logger.info("groupPermission2:"+groupPermission);
			g.setExtend(groupPermission);
			if (GroupManager.updateGroupInfo(g) != null) {
				return true;
			}
		}
		return false;
	}

	public String uploadAvatar() {
		if (avatar == null) {
			// avatar="images/grouphead.png";
		} else {
			avatar = DOWNLOAD_URL + avatar;
		}
		return SUCCESS;
	}
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public List<UserInfo> getMembers() {
		return members;
	}

	public void setMembers(List<UserInfo> members) {
		this.members = members;
	}

	public List<UserInfo> getFutureMembers() {
		return futureMembers;
	}

	public void setFutureMembers(List<UserInfo> futureMembers) {
		this.futureMembers = futureMembers;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupPermission() {
		return groupPermission;
	}

	public void setGroupPermission(String groupPermission) {
		this.groupPermission = groupPermission;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
}
