package com.free4lab.freeshare.action.account;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.utils.userinfo.UserInfoUtil;

/**
 * 处理用户信息
 * @author zhaowei
 *
 */
public class GetUserInfoAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer uid;
	private String uuid;
	public String execute(){
		return SUCCESS;
	}
	public String findUserAvatar(){
		uuid = UserInfoUtil.getUserAvatar(uid);
		return SUCCESS;
	}
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
