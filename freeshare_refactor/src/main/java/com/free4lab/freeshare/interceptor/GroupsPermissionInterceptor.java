package com.free4lab.freeshare.interceptor;

import javax.servlet.http.Cookie;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.free4lab.freeshare.manager.GroupUserManager;

/**
 * 在操作前进行检验是否归属群组。先读取cookie，cookie没有值则再读取数据库查询
 * @author zhaowei
 *
 */
public class GroupsPermissionInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GroupsPermissionInterceptor.class);
	public String intercept(ActionInvocation invocation) throws Exception {
		Integer userId = InterceptorUtil.getUserId(invocation);
		if(userId == null){
			return "notlogined";
		}
		Integer size = 0;
		size = GroupUserManager.getMyGroups(userId).size();
//		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
//		if(cookies.length > 0){
//			for(Cookie cookie : cookies){
//				boolean stop = false;
//				logger.info(cookie.getName());
//				while(cookie.getName().equalsIgnoreCase(userId + "groupsfilter")){
//					size += 1;
//					stop = true;
//					break;
//				}
//				if(stop){
//					break;
//				}
//			}
//		}else{
//			size = GroupUserManager.getMyGroups(userId).size();
//		}
		
		if(size == null || size <= 0){
			logger.debug("没有所属群组，跳转到无群组页面。");
			return "hasnogroups"; 
		}
		return invocation.invoke(); 
	}
}
