package com.free4lab.freeshare.manager.factory;

import com.free4lab.freeshare.manager.permission.IPermissionManager;
import com.free4lab.freeshare.manager.permission.ItemPermissionManager;
import com.free4lab.freeshare.manager.permission.ListPermissionManager;
/**
 * manager的工厂
 * @author wenlele
 *
 */
public class PermissionFactory {
	
	public static IPermissionManager getPermissionManager(Integer type){
		if(type == null || type != -1){
			return getItemPermissionManager();
		}
		return getListPermissionManager();
	}
	
	public static IPermissionManager getItemPermissionManager(){
		return new ItemPermissionManager();
	}
	
	public static IPermissionManager getListPermissionManager(){
		return new ListPermissionManager();
	}
}
