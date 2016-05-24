package com.free4lab.freeshare.model.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="group_user", catalog="freeshare_release")
public class GroupUser {
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", nullable=true, unique=true)
	private Integer id;
	@Column(name="group_id", nullable=true, unique=true)
	private Integer groupId;
	@Column(name="user_id",unique=true,nullable=true)
	private Integer userId;
	@Column(name="extend",nullable=true)
	private String extend;
	
	public GroupUser(){}
	public GroupUser(Integer groupId, Integer userid){
		this.groupId = groupId;
		this.userId = userid;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}
