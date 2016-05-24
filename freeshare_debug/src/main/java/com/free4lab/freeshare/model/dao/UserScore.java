package com.free4lab.freeshare.model.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Score entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_score", catalog = "freeshare_release")
public class UserScore implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Integer uid;
	private Integer iid;
	private Integer up;
	private Integer down;
	private String type;
	
	private Float score;//把用户的浏览和发布资源得分结合在一起

	// Constructors

	/** default constructor */
	public UserScore() {
	}

	/** full constructor */
	public UserScore(Integer uid, Integer iid, Integer up, Integer down, String type, Float score) {
		this.uid = uid;
		this.iid = iid;
		this.up = up;
		this.down = down;
		this.type = type;
		this.score = score;
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

	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "iid", nullable = false)
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "score", nullable = false, precision = 12, scale = 0)
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name = "up", nullable = true)
	public Integer getUp() {
		return up;
	}

	public void setUp(Integer up) {
		this.up = up;
	}
	@Column(name = "down", nullable = true)
	public Integer getDown() {
		return down;
	}

	public void setDown(Integer down) {
		this.down = down;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}