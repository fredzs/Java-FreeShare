package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
@Entity
@Table (name="all_groups", catalog="freeshare_release")
public class GroupModel {
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", nullable=true, unique=true)
	private Integer id;
	@Column(name="group_info", nullable=true, unique=true)
	private String groupInfo;
	@Column(name="uuid",unique=true,nullable=false)
	private String uuid;
	@Column(name="auth_token",unique=true,nullable=false)
	private String authToken;
	@Column(name = "time", nullable = false, length = 19)
	private Timestamp time;
	@Column(name="extend",nullable=true)
	private String extend;
	
	public GroupModel(){}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
