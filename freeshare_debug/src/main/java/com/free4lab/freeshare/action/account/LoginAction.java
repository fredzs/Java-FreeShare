package com.free4lab.freeshare.action.account;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.AccountManager;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.data.Group;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567646001401970359L;
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private String oauthToken;
	private String redirectUrl;
	private Integer groupId;

	public String execute() throws IOException {
		String accessTokenInSession = getSessionToken();
		// 判断session中有accessToken
		if (accessTokenInSession != null) {
			String result = AccountManager
					.getUserInfoByAccessToken(accessTokenInSession);
			// 通过Token获取的用户信息为空，说明本地session无效;无效则重新登录
			if (!result.contains("email")) {
				logger.info("session中有accessToken但是失效。");
				return login();
			}
			
			AccountManager.writeLogin(getSessionUID());
			logger.info("account manager:"+getSessionUID());
			logger.info("session中有accessToken，而且有效，开始跳转。");
			return SUCCESS;
		} else {
			logger.info("session中没有accessToken，通过oauthToken获取。oauthToken ： "
					+ oauthToken);
			return login();
		}
	}

	private String login() {
		if (null != oauthToken) {
			// 获取accessToken
			String accessToken = AccountManager.getAccessToken(oauthToken);
			if (null == accessToken || accessToken.isEmpty()) {
				logger.info("accessToken is null or is empty!");
				return NOT_LOGINED;
			}
			// 获取用户名信息，并把用户信息写入会话中
			logger.info("开始到userinfo请求用户信息，acceeetoken为：" + accessToken);
			if (!AccountManager.writeToSession(
					AccountManager.getUserInfo(accessToken), accessToken)) {
				logger.info("用户信息写入session失败。");
				return NOT_LOGINED;
			}
			// 当groupId不为null的时候说明是要直接加入某个群组
			if (groupId != null) {
				logger.info("the groupId is " + groupId);
				AccountManager.writeLogin(getSessionUID());
				logger.info("account manager:"+getSessionUID());
				return addMembers(groupId);
			}
			
			try {
				redirectUrl = java.net.URLEncoder.encode(redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("登录成功，开始跳转到：" + redirectUrl);
			if (redirectUrl == null || redirectUrl.equals("")) {
				setRedirectUrl("news");
			}
			AccountManager.writeLogin(getSessionUID());
			logger.info("account manager:"+getSessionUID());
			return SUCCESS;
		} else {
			// 会话中无用户信息，而且无oauth_token参数，表示未登录
			return NOT_LOGINED;
		}
	}
	private String addMembers(Integer groupId) {
		Group group = GroupManager.getSimpGroup(groupId);
		if (group == null) {
			return NOT_LOGINED;
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(getSessionUID());
		group.setMemberList(list);
		GroupUserManager.addMembers(group, getSessionUID());
		String uuidString = (String) ActionContext.getContext().getSession()
				.get(SessionConstants.Groups);
		ActionContext
				.getContext()
				.getSession()
				.put(SessionConstants.Groups,
						uuidString + "," + group.getUuid());

		return SUCCESS;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
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
