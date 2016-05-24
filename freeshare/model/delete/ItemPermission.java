package com.free4lab.freeshare.model.delete;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the item_permission database table.
 * 
 */
@Deprecated
@Entity
@Table(name = "item_permission")
public class ItemPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "group_id")
	private Integer teamId;
	@Column(name = "item_type")
	private Integer itemType;
	@Column(name = "permission_type")
	private String type;
	
	public static final String TYPE_READONLY = "readonly";
	public static final String TYPE_READ_WRITE = "read-write";

	// Constructors
	public ItemPermission() {
	}

	public ItemPermission(Integer itemId, Integer teamId, String type) {
		this.itemId = itemId;
		this.teamId = teamId;
		this.type = type;
	}

	// getter and setter

	public Integer getItemId() {
		return this.itemId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}