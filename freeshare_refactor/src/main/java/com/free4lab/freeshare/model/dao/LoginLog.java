package com.free4lab.freeshare.model.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the item_permission database table.
 * 
 */
@Entity
@Table(name="login_log")
public class LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="times")
	private Integer times;

	@Column(name="reminder")
	private Integer reminder;
	
	@Column(name="first_login")
	private Timestamp firstLogin;
	
	@Column(name="last_login")
	private Timestamp lastLogin;
	
	@Column(name="extend1")
	private String extend1;
	
	@Column(name="extend2")
	private String extend2;
	
	
	// Constructors
    public LoginLog() {
    }    
    public LoginLog(Integer userId,Timestamp firstLogin,Timestamp lastLogin,Integer times,Integer reminder,String extend1,String extend2){
	   this.userId = userId;
       this.firstLogin = firstLogin;
	   this.lastLogin = lastLogin;
	   this.times = times;
	   this.reminder = reminder;
	   this.extend1 = extend1;
	   this.extend2 = extend2;
   }

	// getter and setter

	public Integer getTimes() {
		return times;
	}


	public void setTimes(Integer times) {
		this.times = times;
	}


	public Integer getReminder() {
		return reminder;
	}


	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}





	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Timestamp getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Timestamp firstLogin) {
		this.firstLogin = firstLogin;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getExtend1() {
		return extend1;
	}


	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}


	public String getExtend2() {
		return extend2;
	}


	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	

}