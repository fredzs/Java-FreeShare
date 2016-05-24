package com.free4lab.freeshare.interceptor;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.handler.ItemHandler;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.manager.permission.PermissionUtil;
import com.free4lab.freeshare.model.dao.ItemPermissionDAO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ItemPermissionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298650340816199342L;
	private static final Logger logger = Logger.getLogger(ItemPermissionInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Integer itemId = InterceptorUtil.getId(invocation);
		Integer userId = (Integer) invocation.getInvocationContext().getSession().get(SessionConstants.UserID);
		
		boolean itemIsExisted = new ItemHandler().getItem(itemId) != null;
		if (userId != null){
			if (!itemIsExisted){
				logger.info("the item No." + itemId + " is deleted or not existed! ");
				return "deleted";
			}
			Integer permission = PermissionFactory.getItemPermissionManager()
					.getPermission(itemId, userId);
			if (permission.compareTo(PermissionUtil.READABLE_PERMISSION) <= 0){
				logger.info("have read permission——permission value is " + permission);
				InterceptorUtil.setPermission(invocation, permission);
				return invocation.invoke();
			}
			//把访问的该资源的id和类型存进actioncontext，用来在无权限页面做进一步操作
			ActionContext.getContext().put("iditem", Integer.toString(itemId));
			return "permissionDenied"; 
		} else{
			logger.info("针对未登录用户的鉴权。");
			if (itemIsExisted && (new ItemPermissionDAO()).isPublic(itemId)){
				InterceptorUtil.setPermission(invocation, new Integer(3));
				logger.info("资源为公开");
				return invocation.invoke(); 
			} else{
				InterceptorUtil.setRedirectUrl(invocation);
				return "notlogined";
			}
		}
	}

}
