package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.free4lab.freeshare.util.TimeUtil;

/**
 * Relation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "list_relation")
public class ListRelation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer resourceId;
	private Integer resourceOrder;
	private Integer listId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ListRelation() {
	}

	/** full constructor */
	public ListRelation(Integer resourceId, Integer listId, Timestamp createTime) {
		this.resourceId = resourceId;
		this.listId = listId;
		this.createTime = createTime;
	}
	public ListRelation(Integer resourceId, Integer listId) {
		
		this.resourceId = resourceId;
		this.listId = listId;
		this.createTime = TimeUtil.getCurrentTime();
	}
	public ListRelation(Integer resourceId, Integer listId, Integer resourceOrder) {
		
		this.resourceId = resourceId;
		this.listId = listId;
		this.resourceOrder = resourceOrder;
		this.createTime = TimeUtil.getCurrentTime();
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "resource_id", nullable = false)
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "list_id", nullable = false)
	public Integer getListId() {
		return this.listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp time) {
		this.createTime = time;
	}
	
	@Column(name = "resource_order")
	public Integer getResourceOrder() {
		return resourceOrder;
	}

	public void setResourceOrder(Integer resourceOrder) {
		this.resourceOrder = resourceOrder;
	}

}