package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 企业表 Entity
 * @author WangXiangyun 
 */
@Entity
@Table(name = "company")
public class Company implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "company_id", nullable = false)
	private Integer companyId;
	@Column(name = "company_name",nullable = false, length = 50)
	private String companyName;
	@Column(name = "admin_uid",nullable = false)
	private Integer adminUid;
	@Column(name = "notice",nullable = true,length=256)
	private String notice;
	@Column(name = "extent",nullable = true,length=256)
	private String extent;
	
	public Company(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getAdminUid() {
		return adminUid;
	}
	public void setAdminUid(Integer adminUid) {
		this.adminUid = adminUid;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getExtent() {
		return extent;
	}
	public void setExtent(String extent) {
		this.extent = extent;
	}
	
	
}