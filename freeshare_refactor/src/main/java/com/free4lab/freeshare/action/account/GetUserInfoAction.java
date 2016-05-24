package com.free4lab.freeshare.action.account;

import java.util.ArrayList;
import java.util.List;

import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

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
		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(uid);
		if(UserInfoUtil.getUserInfoByUid(getSessionToken(), userIdList) != null){
			uuid = UserInfoUtil.getUserInfoByUid(getSessionToken(), userIdList).get(0).getAvatar();
		}
		//TODO当uuid为null，在页面onerror
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
