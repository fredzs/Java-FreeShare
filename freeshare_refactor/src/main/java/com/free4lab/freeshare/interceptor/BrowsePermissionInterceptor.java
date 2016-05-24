package com.free4lab.freeshare.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.ResourcePermissionManager;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 检查是否有浏览资源权限拦截器
 * 
 * @author zhaowei
 * 
 */
public class BrowsePermissionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(BrowsePermissionInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		Integer userId = InterceptorUtil.getUserId(invocation);
		if (userId != null) {
			Integer resourceId = InterceptorUtil.getId(invocation);
			boolean isExisted = new ResourceManager().readResource(resourceId) == null ? true
					: false;
			if (isExisted) {
				logger.warn(resourceId + " is deleted or not existed! ");
				return "deleted";
			}
			Integer permission = returnPermission(userId, resourceId, invocation);
			if(permission > ReadWritePermissionEnum.READABLE_PERMISSION.getValue()){
				invocation.getInvocationContext().put("iditem", Integer.toString(resourceId));
				return "permissionDenied"; 
			}
		}else{
			InterceptorUtil.setRedirectUrl(invocation);
			return "notlogined";
		}
		return invocation.invoke();
	}
	/**
	 * 先判断是否是作者,然后判断作者是否在资源的归属群组中
	 * @param userId
	 * @param resourceId
	 * @param invocation
	 * @return
	 */
	private Integer returnPermission(Integer userId, Integer resourceId,
			ActionInvocation invocation) {
		Integer permission = 0;
		ResourcePermissionManager rpm = new ResourcePermissionManager();
		if (!rpm.isAuthor(resourceId, userId)) {
			List<Integer> resourceBelongGroupIds = rpm
					.selectWritableGroups(resourceId);
			Integer size = resourceBelongGroupIds.size();
			List<Integer> userBelongGroupIds = null;
			//先看下cookie中是否存储有用户的归属群组
	/*		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				userBelongGroupIds = new ArrayList<Integer>();
				for (Cookie c : cookies) {
					userBelongGroupIds.add(Integer.parseInt(c.getValue()));
				}
			}*/
			if (userBelongGroupIds == null)
				userBelongGroupIds = GroupUserManager.getMyGroupIds(userId);
			
			resourceBelongGroupIds.removeAll(userBelongGroupIds);
			if (resourceBelongGroupIds.size() < size) {
				permission = ReadWritePermissionEnum.WRITABLE_PERMISSION
						.getValue();
			} else {
				permission = ReadWritePermissionEnum.AVOID_PERMISSION
						.getValue();
			}
		} else
			permission = ReadWritePermissionEnum.AUTHOR_PERMISSION.getValue();
		return permission;
	}

}
