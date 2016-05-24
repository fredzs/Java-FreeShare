package com.free4lab.freeshare.interceptor;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.manager.permission.PermissionUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ItemUpdatePermissionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757896255180877031L;
	private static final Logger logger = Logger.getLogger(ItemUpdatePermissionInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Integer permission = PermissionFactory.getItemPermissionManager()
				.getPermission(InterceptorUtil.getId(invocation), 
							   InterceptorUtil.getUserId(invocation));		
		if (permission.compareTo(PermissionUtil.WRITABLE_PERMISSION) <= 0){
			logger.info("update item , have read permission——permission value is " + permission);
			return invocation.invoke();			
		}
		else{
			return "permissionDenied"; 
		}
	}


	
}
