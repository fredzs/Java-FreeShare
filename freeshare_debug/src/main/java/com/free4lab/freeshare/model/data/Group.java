package com.free4lab.freeshare.model.data;

import java.sql.Timestamp;
import java.util.List;

public class Group {
	private Integer groupId;
	private String uuid;
	private String authToken;
	private Timestamp time;
	private GroupInfo groupInfo;
	private String extend;
	private List<Integer> memberList;
	
	public Group(String uuid, String authToken) {
		this.uuid = uuid;
		this.authToken = authToken;
	}
	
	public Group(Integer groupId, String uuid, String authToken) {
		this.groupId = groupId;
		this.uuid = uuid;
		this.authToken = authToken;
	}
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public List<Integer> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Integer> memberList) {
		this.memberList = memberList;
	}
	
}
