package com.free4lab.freeshare.action.account;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.AccountManager;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.utils.account.AccountUtil;
import com.free4lab.utils.account.BaseLoginAction;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseLoginAction {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	private String redirectUrl;
	private Integer groupId;
	@Override
	public boolean writeToSession(JSONObject obj) {
		// TODO Auto-generated method stub
		logger.info(obj);
		if (obj != null && obj.has("uid")){
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(SessionConstants.UserID, jsonGetUserId(obj));
			session.put(SessionConstants.UserEmail, jsonGetEmail(obj));
			session.put(SessionConstants.UserName, jsonGetScreenName(obj));
			session.put(SessionConstants.Avatar, jsonGetProfileImageUrl(obj));
			return true;
		} else {
			logger.info("获取的用户信息为空");
		}
		return false;
	}
	@Override
	public String giveDefaultRedirect() {
		// TODO Auto-generated method stub
		return "news";
	}
	@Override
	public String writeAccessTokenToSession(String access_token) {
		// TODO Auto-generated method stub
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(SessionConstants.AccessToken, access_token);
		session.put(SessionConstants.KEY_ACCTOKEN, access_token.substring(8, 24));
		return null;
	}
	@Override
	public String giveClientSecret() {
		// TODO Auto-generated method stub
		return SessionConstants.CLIENT_SECRET_KEY;
	}

	@Override
	public String execute() {
		logger.info("登陆啦");
		if( code != null && !"".equalsIgnoreCase(code)){
			//获得accessToken
			String client_secret = giveClientSecret();
			String access_token = AccountUtil.getAccessTokenByCode(code,giveClientSecret());
			logger.info("access_token="+access_token);
			if (null == access_token || access_token.isEmpty()) {
				logger.info("the oauthToken is invalid;accessToken is null or is empty!");
				return INPUT;
			}
			writeAccessTokenToSession(access_token);
			// 获取用户名信息，并把用户信息写入会话中
			logger.info("开始请求用户信息，accesstoken为：" + access_token);
			JSONObject obj = AccountUtil.getUserByAccessToken(access_token);
			writeToSession(obj);
			if (groupId != null) {
				logger.info("the groupId is " + groupId);
				AccountManager.writeLogin(jsonGetUserId(obj));
				logger.info("account manager:"+jsonGetUserId(obj));
				return addMembers(groupId);
			}
			
			if(redirectUrl == null || "".equalsIgnoreCase(redirectUrl)){
				redirectUrl = giveDefaultRedirect();
			}
			return SUCCESS;
		} else {
			logger.info("code 为空!");
			return INPUT;
		}
	}
		
	private String addMembers(Integer groupId) {
		Group group = GroupManager.getSimpGroup(groupId);
		if (group == null) {
			return INPUT;
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Integer> list = new ArrayList<Integer>();
		list.add((Integer) session.get(SessionConstants.UserID));
		group.setMemberList(list);
		GroupUserManager.addMembers(group,(Integer) session.get(SessionConstants.UserID));
		String uuidString = (String) ActionContext.getContext().getSession()
				.get(SessionConstants.Groups);
		ActionContext
				.getContext()
				.getSession()
				.put(SessionConstants.Groups,
						uuidString + "," + group.getUuid());

		return SUCCESS;
	}


	public String getRedirectUrl() {
		logger.info(((String[]) ActionContext.getContext()
				.getParameters().get("redirectUrl"))[0]);
		try {
			redirectUrl = URLDecoder.decode(redirectUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
