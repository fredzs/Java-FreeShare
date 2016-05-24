package com.free4lab.freeshare.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.free4lab.freeshare.SessionConstants;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 提供拦截器的操作ActionContext或者数据库的接口
 * 
 * @author zhaowei
 * 
 */
public class InterceptorUtil {
	static public Integer getId(ActionInvocation invocation) {
		Map<String, Object> paramsMap = invocation.getInvocationContext()
				.getParameters();
		String[] list = (String[]) paramsMap.get("id");

		return Integer.parseInt(list[0]);
	}

	static public Integer getReourceType(ActionInvocation invocation) {
		Map<String, Object> paramsMap = invocation.getInvocationContext()
				.getParameters();
		String[] list = (String[]) paramsMap.get("type");
		if (list != null && list.length > 0) {
			try {
				return Integer.parseInt(list[0]);
			} catch (Exception e) {
				// ignore
			}
		}
		return null;
	}

	static public Integer getGroupId(ActionInvocation invocation) {
		Map<String, Object> paramsMap = invocation.getInvocationContext()
				.getParameters();
		String[] list = (String[]) paramsMap.get("groupId");
		return Integer.valueOf(list[0]);
	}
	
	@Deprecated
	static public Integer getUserId(ActionInvocation invocation) {
		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();
		String accessToken = (String) session.get(SessionConstants.AccessToken);
		if (accessToken == null) {
			return null;
		} else {
			return (Integer) invocation.getInvocationContext().getSession()
					.get(SessionConstants.UserID);
		}
	}

	private static final String PERMISSION = "permission";

	static public void setPermission(ActionInvocation invocation,
			Integer permission) {
		Map<String, Object> paramsMap = invocation.getInvocationContext()
				.getParameters();
		paramsMap.put(PERMISSION, permission);
	}

	private static final String REDIRECT_URL = "redirectUrl";

	public static void setRedirectUrl(ActionInvocation invocation) {
		HttpServletRequest req = ServletActionContext.getRequest();
		String path = "" + req.getRequestURL();
		String paramsPath = req.getQueryString();
		StringBuffer realPath = new StringBuffer(path);
		if (paramsPath != null) {
			realPath.append("?" + paramsPath);
		}
		Map<String, Object> paramsMap = invocation.getInvocationContext()
				.getValueStack().getContext();

		String url = realPath.toString();
		try {
			url = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		paramsMap.put(REDIRECT_URL, url);
	}
}
