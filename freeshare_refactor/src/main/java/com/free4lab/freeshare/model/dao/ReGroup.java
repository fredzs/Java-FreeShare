package com.free4lab.freeshare.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "re_group")
public class ReGroup implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer reGroupId;
	private Integer reNum;
	public ReGroup(){}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "re_num", nullable = false)
	public Integer getReNum() {
		return reNum;
	}
	public void setReNum(Integer reNum) {
		this.reNum = reNum;
	}
	@Column(name = "re_groupId", nullable = false)
	public Integer getReGroupId() {
		return reGroupId;
	}
	public void setReGroupId(Integer reGroupId) {
		this.reGroupId = reGroupId;
	}
}

