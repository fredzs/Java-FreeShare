package com.free4lab.freeshare.interceptor;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.handler.ListHandler;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.manager.permission.ListPermissionManager;
import com.free4lab.freeshare.manager.permission.PermissionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ListPermissionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5273424582050065076L;
	private static final Logger logger = Logger.getLogger(ListPermissionInterceptor.class);	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Integer id = InterceptorUtil.getId(invocation);
		Integer userId = InterceptorUtil.getUserId(invocation);
		
		boolean listIsExisted = (new ListHandler()).getObject(id) != null; 
		
		if (userId != null){
			if (!listIsExisted){
				logger.info("list No." + id + " is not existed or deleted!");
				return "deleted";
			}
			Integer permission = PermissionFactory.getListPermissionManager()
					.getPermission(id, userId);		
			if (permission.compareTo(PermissionUtil.READABLE_PERMISSION) <= 0){
				logger.info("have read permission——permission value is " + permission);
				InterceptorUtil.setPermission(invocation, permission);
				return invocation.invoke();
			}
			ActionContext.getContext().put("idlist", Integer.toString(id));
			return "permissionDenied";
		} else{
			if (listIsExisted && (new ListPermissionManager()).isPublic(id)){
				return invocation.invoke();
			} else{
				InterceptorUtil.setRedirectUrl(invocation);
				return "notlogined";
			}
		}	
	}
}
