package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Relation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "relation", catalog = "freeshare_release")
public class Relation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer itemId;
	private Integer listId;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public Relation() {
	}

	/** full constructor */
	public Relation(Integer itemId, Integer listId, Timestamp time) {
		this.itemId = itemId;
		this.listId = listId;
		this.time = time;
	}
	public Relation(Integer itemId, Integer listId) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());	
		this.itemId = itemId;
		this.listId = listId;
		this.time = currentTime;
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

	@Column(name = "item_id", nullable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "list_id", nullable = false)
	public Integer getListId() {
		return this.listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	@Column(name = "time", nullable = false, length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}