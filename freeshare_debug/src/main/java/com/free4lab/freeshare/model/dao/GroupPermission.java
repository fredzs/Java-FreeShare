package com.free4lab.freeshare.model.dao;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
@Entity
@Table(name="group_permission", catalog="freeshare_release")
public class GroupPermission {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable =false, unique = true)
	private Integer id;
	@Column(name = "uid", nullable = false)
	private Integer uid;
	@Column(name = "group_id", nullable = false, unique = true)
	private Integer groupId;
	@Column(name = "extend", nullable = true)
	private String extend;
	@Column(name = "type", nullable = false, unique = true)
	private Integer type;

	public GroupPermission(){}
	
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

	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
