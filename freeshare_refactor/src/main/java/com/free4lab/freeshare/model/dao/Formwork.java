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
 * Formwork entity.
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "formwork")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Formwork implements java.io.Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type;
	private String name;
	private String content;
	private Integer authorId;
	private Integer unuse;
	private String description;
	private Timestamp time;
	private String image_path;
	private Timestamp recentEditTime;
	private String extend;

	public Formwork() {
	}

	/** minimal constructor */
	public Formwork(Integer type, String name, String content,
			Integer authorId, Integer unuse, Timestamp time,String image_path,
			Timestamp recentEditTime) {
		this.type = type;
		this.name = name;
		this.content = content;
		this.authorId = authorId;
		this.unuse = unuse;
		this.time = time;
		this.image_path = image_path;
		this.recentEditTime = recentEditTime;
	}

	/** full constructor */
	public Formwork(Integer type, String name, String content,
			Integer authorId, Integer unuse, String description, Timestamp time,
			String image_path,Timestamp recentEditTime, String extend) {
		this.type = type;
		this.name = name;
		this.content = content;
		this.authorId = authorId;
		this.unuse = unuse;
		this.description = description;
		this.time = time;
		this.image_path = image_path;
		this.recentEditTime = recentEditTime;
		this.extend = extend;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
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

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "extend", length = 200)
	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}