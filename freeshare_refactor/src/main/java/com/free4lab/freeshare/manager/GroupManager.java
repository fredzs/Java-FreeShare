package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.dao.GroupModelDAO;
import com.free4lab.freeshare.model.dao.GroupUser;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.GroupInfo;
import com.free4lab.freeshare.util.CompanyUser;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

/**
 * 获取群组对象,所有使用到的群组相关信息,必须调用这里方法.不能直接操作model
 * 
 * @author Administrator
 * 
 */
public class GroupManager {

	/**
	 * @param Group对象
	 * <br/>
	 *            该group对象必须要包括父组的uuid与authToken，要创建的群组的描述信息是可选
	 * @retur 创建成功的Group对象
	 */
	public static Group createGroup(Group fg) {
		GroupModel gm = new GroupModelDAO().getFreeshareGroup();
		if (gm != null && fg.getAuthToken().equals(gm.getAuthToken())) {
			String groupInfo = "";
			if (fg.getGroupInfo() != null) {
				try {
					groupInfo = JSONUtil.serialize(fg.getGroupInfo());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			String uuid = UUID.randomUUID().toString();
			String authToken = UUID.randomUUID().toString();
			GroupModel model = new GroupModelDAO().addGroupModel(groupInfo,
					uuid, authToken,gm.getExtend());
			Group group = new Group(model.getId(), uuid, authToken);
			group.setGroupInfo(fg.getGroupInfo());
			return group;
		}
		return null;
	}

	public static Group createGroup(String privilege) {
		String uuid = UUID.randomUUID().toString();
		String authToken = UUID.randomUUID().toString();
		GroupModel gm = new GroupModelDAO().addGroupModel("", uuid, authToken,privilege);
		Group group = new Group(gm.getId(), uuid, authToken);
		group.setGroupInfo(new GroupInfo(gm.getGroupInfo()));
		gm.setExtend(privilege);
		return group;
	}

	/**
	 * @param Group对象
	 * <br/>
	 *            该group对象必须包括要修改组的uuid与authToken，和要修改的群组新描述信息
	 * @retur 创建成功的Group对象
	 */
	public static boolean delGroup(Group group) {
		if (checkNull(group)) {
			new GroupModelDAO().delGroupModel(group.getUuid());
			return true;
		}
		return false;
	}

	/**
	 * @param Group对象
	 * <br/>
	 *            该group对象必须包括要修改组的uuid与authToken，和要修改的群组新描述信息
	 * @retur 创建成功的Group对象
	 */
	public static Group updateGroupInfo(Group group) {
		if (checkNull(group)) {
			
			new GroupModelDAO().setGroupInfo(group);
			return group;
		}
		return null;
	}

	public static Group get(String uuid) {
		GroupModel gm = new GroupModelDAO().getGroupByUuid(uuid);
		if (gm != null) {
			Group group = new Group(gm.getId(), gm.getUuid(), gm.getAuthToken());
			GroupInfo groupInfo = new GroupInfo(gm.getGroupInfo());
			group.setGroupInfo(groupInfo);
			return group;
		}
		return null;
	}

	/**
	 * 通过群组的uuid批量获取群组对象
	 */
	public static List<Group> getGroupsByuuid(List<String> uuids) {
		List<Group> groups = new ArrayList<Group>();
		if (uuids != null) {
			int size = uuids.size();
			for (int i = 0; i < size; i++) {
				Group group = get(uuids.get(i));
				if (group != null) {
					groups.add(group);
				}
			}
		}
		return groups;
	}

	/**
	 * 获取简单群组对象，该群组对象不包含组与组员之间的关系。<br/>
	 * 想获取比较全的包括有组员关系的群组对象，应该调用getFullGroup
	 */
	public static Group getSimpGroup(Integer groupId) {
		GroupModel gm = new GroupModelDAO().findById(groupId);
		if (gm != null) {
			Group group = new Group(gm.getId(), gm.getUuid(), gm.getAuthToken());
			GroupInfo groupInfo = new GroupInfo(gm.getGroupInfo());
			group.setGroupInfo(groupInfo);
			group.setTime(gm.getTime());
			group.setExtend(gm.getExtend());
			return group;
		}
		return null;
	}

	/**
	 * 根据群组groupId与用户userId获取该组的含有组员关系的一个Group对象.用户userId为使用该接口的用户id,必须传值,
	 * 用以判断该用户是否是groupId所代表的群组成员之一
	 * 
	 * @param groupId
	 * @param userId
	 * @return 含有组员关系的一个Group对象
	 */
	public static Group getFullGroup(Integer groupId) {
		if (groupId != null) {
			Group group = getSimpGroup(groupId);
			
			if(group != null){
				List<Integer> memberList = new ArrayList<Integer>();
				List<GroupUser> gus = GroupUserManager.getGroupUsers(groupId);
				for (GroupUser gu : gus) {
					memberList.add(gu.getUserId());
				}
				group.setMemberList(memberList);
			}
			return group;
		}
	
		return null;
	}
    
	public static List<Group> getGroups(List<Integer> groupIds) {
		List<Group> groupList = new ArrayList<Group>();
		if (groupIds != null && groupIds.size() > 0) {
			for (Integer groupId : groupIds) {
				Group group = getSimpGroup(groupId);
				if(group != null){
					groupList.add(group);
				}
			}
		}
		return groupList;
	}
	public static List<Group> getSearchableGroups(List<Integer> groupIds) {
		List<Group> groupList = new ArrayList<Group>();
		if (groupIds != null && groupIds.size() > 0) {
			for (Integer groupId : groupIds) {
				Group group = getSimpGroup(groupId);
				if(group != null&&!group.getExtend().equals("private")){
					groupList.add(group);
				}
			}
		}
		return groupList;
	}
	private static boolean checkNull(Group group) {
		return new GroupModelDAO().getGroupByUuid(group.getUuid()) != null;
	}

	public static List<Group> getAllGroups() {
		List<Group> groupList = new ArrayList<Group>();
		List<GroupModel> modelList = new ArrayList<GroupModel>();
		modelList = new GroupModelDAO().findByProperty("extend", "public");
		for(GroupModel m : modelList){
			groupList.add(getSimpGroup(m.getId()));
		}
		return groupList;
	}
	
	/*判断某个用户是否在某一群组中*/
	public static boolean checkInGroup(Integer groupId,Integer userId) {
		if (groupId != null) {
			Group group = getSimpGroup(groupId);
			if(group != null){
				List<GroupUser> gus = GroupUserManager.getGroupUsers(groupId);
				for (GroupUser gu : gus) {
					if(userId.equals(gu.getUserId()))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//企业版添加
	@SuppressWarnings("unused")
	public static List<Group> getCompanyGroups(){
		String company = null; 
		List<Group> groupList = new ArrayList<Group>();
		List<GroupModel> modelList = new ArrayList<GroupModel>();		
		Integer userId = new BaseAction().getSessionUID();
		String token = new BaseAction().getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> ui = UserInfoUtil.getUserInfoByUid(token, userList);
		if(ui != null)
		{
			company= ui.get(0).getCompany();
		}
		System.out.println("ui.get(0).getCompany:"+ui.get(0).getCompany());
		System.out.println("before groupList.size()"+groupList.size());
		System.out.println("groupmanager-company: " + company);
		
		if(!company.equals("null") )
		{
			//System.out.println("company == nul");
			modelList = new GroupModelDAO().findByProperty("extend", company);
			for(GroupModel m : modelList){
				groupList.add(getSimpGroup(m.getId()));
			}
			System.out.println("after groupList.size()"+groupList.size());
			return groupList;
		}
		else
		{
			return groupList;
		}
	}
	
}
