package com.free4lab.freeshare.model.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "resource")
public  class Resource implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type;
	private String name;
	private String description;
	private Integer authorId;
	private String attachment;
	private Timestamp createTime;
	private Timestamp recentEditTime;
	private String extend;

	private Set<ResourcePermission> rps;
	
	public Resource(){
		
	}
	public Resource(Integer type, String name, String description,
			Integer authorId, String attachment) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		this.attachment = attachment;
	}
	
	public Resource(Integer type, String name, String description,
			Integer authorId, Timestamp createTime,
			Timestamp recentEditTime,String attachment, String extend) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		
		this.createTime = createTime;
		this.recentEditTime = recentEditTime;
		this.attachment = attachment;
		this.extend = extend;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "resource_type", nullable = false)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "resource_name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "author_id", nullable = false)
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "resource",
			fetch = FetchType.LAZY)*/
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resource",
			fetch = FetchType.EAGER)
	
	public Set<ResourcePermission> getRps() {
		return rps;
	}
	public void setRps(Set<ResourcePermission> rps) {
		this.rps = rps;
	}
	
	@Column(name = "create_time", nullable = false)
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "edit_time", nullable = false)
	public Timestamp getRecentEditTime() {
		return recentEditTime;
	}
	public void setRecentEditTime(Timestamp recentEditTime) {
		this.recentEditTime = recentEditTime;
	}
	@Column(name = "attachment", nullable = true)
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Column(name = "extend", nullable = true)
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	
	public void addResourcePermission(ResourcePermission rp){
		rp.setResource(this);
		if(rps ==  null){
			rps = new HashSet<ResourcePermission>();
		}
		rps.add(rp);
	}
}
