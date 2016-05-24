package com.free4lab.freeshare.action.group;
import java.util.Iterator;
import java.util.List;
import com.free4lab.freeshare.action.*;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.data.Group;


public class DeleteUserAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private List<Group> groupList;
	private Integer uid;
	private Integer groupId;
	public String execute()
	{
		
		groupList = GroupUserManager.getMyGroups(uid);
		System.out.println("uid:"+uid);
		System.out.println("该用户的所有群组数目为"+groupList.size());
		Iterator<Group> it = groupList.iterator();
		while(it.hasNext()){
			Group group =it.next();
			if(group.getExtend().equals("company")){
				it.remove();
				GroupUserManager.delMember(group.getGroupId(), uid);
			}	
		}
		
		System.out.println("该用户所属的非企业群组group.size()："+uid+"是"+groupList.size());
		return SUCCESS;
	}
	
	public String deleteUserFromOneGroup(){
		GroupUserManager.delMember(groupId, uid);
		System.out.println("用户Id："+uid+"在"+groupId+"组中，删除成功");
		return SUCCESS;
	}
	public List<Group> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
