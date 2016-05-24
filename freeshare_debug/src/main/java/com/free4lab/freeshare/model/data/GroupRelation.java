package com.free4lab.freeshare.model.data;

import java.io.Serializable;
public class GroupRelation implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer resourceId;
	private Integer groupId;
	private String permissionType;
	public static final String TYPE_READ_ONLY = "read_only";
	public static final String TYPE_READ_WRITE = "read_write";

	// Constructors
	public GroupRelation() {
	}

	public GroupRelation(Integer resourceId, Integer groupId, String permission) {
		this.resourceId = resourceId;
		this.groupId = groupId;
		this.permissionType = permission;
	}

	// getter and setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

}