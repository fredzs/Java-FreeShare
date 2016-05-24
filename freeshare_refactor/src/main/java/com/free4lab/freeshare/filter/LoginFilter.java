package com.free4lab.freeshare.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.free4lab.freeshare.SessionConstants;
import com.free4lab.utils.account.BaseLoginFilter;

public class LoginFilter extends BaseLoginFilter {

	@Override
	protected String getClientId() {
		return "freeshare";
	}

	@Override
	protected String getRedirectUri() {
		return "/account/login";
	}

	@Override
	protected String getAccessTokenInSession(HttpServletRequest request,
			HttpServletResponse response) {
		String accessTokenInSession = (String) request.getSession().getAttribute(SessionConstants.AccessToken);
		return accessTokenInSession;
	}

}
