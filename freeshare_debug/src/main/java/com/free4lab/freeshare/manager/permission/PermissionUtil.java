package com.free4lab.freeshare.manager.permission;

import com.free4lab.freeshare.SessionConstants;
import com.opensymphony.xwork2.ActionContext;

public class PermissionUtil {
	public static Integer AUTHOR_PERMISSION = 1;
	public static Integer WRITABLE_PERMISSION = 2;
	public static Integer READABLE_PERMISSION = 3;
	public static Integer AVOID_PERMISSION = 4;
	
	public static String getToken(){
		return (String)ActionContext.getContext()
				.getSession().get(SessionConstants.AccessToken);
	}
}
