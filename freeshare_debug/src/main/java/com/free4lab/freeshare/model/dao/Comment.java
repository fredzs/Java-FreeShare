package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author WangXiangyun 评论表 Entity
 */
@Entity
@Table(name = "comment", catalog = "freeshare_release")
public class Comment {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "cmtUrl", nullable = false, length = 71)
	private String cmtUrl;
	@Column(name = "resource_id", nullable = false)
	private Integer resourceId;
	@Column(name = "resource_type", nullable = false)
	private Integer resourceType;
	@Column(name = "userId", nullable = false)
	private Integer userId;
	@Column(name = "time", nullable = false)
	private Timestamp time;
	@Column(name = "description", nullable = false)
	private String description;

	public Comment() {

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setcmtUrl(String cmtUrl) {
		this.cmtUrl = cmtUrl;
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCmtUrl() {
		return cmtUrl;
	}

	public void setCmtUrl(String cmtUrl) {
		this.cmtUrl = cmtUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}