package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * List entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lists", catalog = "freeshare_release")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lists implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private Integer authorId;
	private Integer unuse;
	private Timestamp time;
	private Timestamp recentEditTime;
	private String extend;

	// Constructors

	/** default constructor */
	public Lists() {
	}

	/** minimal constructor */
	public Lists(String name, String description, Integer authorId,
			Integer unuse, Timestamp time, Timestamp recentEditTime) {
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		this.unuse = unuse;
		this.time = time;
		this.recentEditTime = recentEditTime;
	}

	/** full constructor */
	public Lists(String name, String description, Integer authorId,
			Integer unuse, Timestamp time, Timestamp recentEditTime,
			String extend) {
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		this.unuse = unuse;
		this.time = time;
		this.recentEditTime = recentEditTime;
		this.extend = extend;
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

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "author_id", nullable = false)
	public Integer getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@Column(name = "group_id", nullable = true)
	public Integer getUnuse() {
		return unuse;
	}

	public void setUnuse(Integer unuse) {
		this.unuse = unuse;
	}

	@Column(name = "time", nullable = false, length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "recent_edit_time", nullable = false, length = 19)
	public Timestamp getRecentEditTime() {
		return this.recentEditTime;
	}

	public void setRecentEditTime(Timestamp recentEditTime) {
		this.recentEditTime = recentEditTime;
	}

	@Column(name = "extend", length = 50)
	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}