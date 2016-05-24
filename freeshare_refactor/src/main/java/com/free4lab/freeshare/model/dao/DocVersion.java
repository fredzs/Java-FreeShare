package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author ZhangSheng 文档版本 Entity
 */
@Entity
@Table(name = "doc_version")
public class DocVersion {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	@Column(name = "uuid", nullable = false)
	private String uuid;
	@Column(name = "description", nullable = false, length = 65535)
	private String description;
	@Column(name = "edit_time", nullable = false)
	private Timestamp editTime;
	@Column(name = "item_id", nullable = false)
	private Integer itemId;

	public DocVersion() {
	}

	public DocVersion(Integer userId, String uuid, String description,
			Timestamp editTime) {

		this.uuid = uuid;
		this.description = description;
		this.editTime = editTime;
		this.userId = userId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

}