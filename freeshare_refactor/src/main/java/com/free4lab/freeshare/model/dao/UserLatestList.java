package com.free4lab.freeshare.model.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户最近操作的对象的列表
 * 对象：列表、群组
 * @author lzc 
 */
@Entity
@Table(name = "user_latestlist")
public class UserLatestList {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "uid", nullable = false)
	private Integer uid;
	@Column(name = "latest_list", nullable = false)
	private String latestList;
	@Column(name = "latest_group", nullable = false)
	private String latestGroup;

	public UserLatestList() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getLatestList() {
		return latestList;
	}

	public void setLatestList(String latestList) {
		this.latestList = latestList;
	}

	public String getLatestGroup() {
		return latestGroup;
	}

	public void setLatestGroup(String latestGroup) {
		this.latestGroup = latestGroup;
	}

}
