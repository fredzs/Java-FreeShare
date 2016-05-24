package com.free4lab.freeshare.filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.manager.AccountManager;
public class LoginFilter extends PermissionFilter {
	private static final Logger logger = Logger.getLogger(LoginFilter.class);
	/**
	 * 判断是否已经登录
	 * 方法：判断session内是的acceeetoken是否有效
	 */
	protected boolean checkPermission(HttpServletRequest request,
			HttpServletResponse response) {	
		logger.debug("the request url is " + request.getRequestURI());
		HttpSession session = request.getSession();
		String accessToken = (String) session.getAttribute(SessionConstants.AccessToken);
		if(accessToken != null){
			String result = AccountManager.getUserInfoByAccessToken(accessToken);
			if(result.indexOf("avatar") == -1){
				session.invalidate();
				return false;
			}
			return true;
		}
		return false;
	}
}
