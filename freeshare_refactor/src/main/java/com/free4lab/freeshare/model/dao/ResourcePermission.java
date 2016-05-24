package com.free4lab.freeshare.model.dao;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "resource_permission")
public class ResourcePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@Column(name = "resource_id")
//	private Integer resourceId;

	@Column(name = "group_id")
	private Integer groupId;

	@Column(name = "permission_type")
	private String permissionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id")
	private Resource resource;

	// Constructors
	public ResourcePermission() {
	}

	public ResourcePermission(Integer resourceId, Integer groupId,
			String permission) {
	    Resource resource = new Resource();
        resource.setId(resourceId);
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

//	public Integer getResourceId() {
//		return this.resourceId;
//	}
//
//	public void setResourceId(Integer resourceId) {
//		this.resourceId = resourceId;
//	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

}