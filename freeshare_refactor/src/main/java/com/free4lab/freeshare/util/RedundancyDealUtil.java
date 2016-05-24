package com.free4lab.freeshare.util;

import java.util.*;

import com.free4lab.utils.account.UserInfo;

//import com.free4lab.freeshare.model.dao.User;

public class RedundancyDealUtil {
	public static List<UserInfo> removeRedundancy(List<UserInfo> members){
		List<UserInfo> temp = new ArrayList<UserInfo>();
		
		List<Integer> uuids = new  ArrayList<Integer>();
		for(UserInfo member:members){
			if(!uuids.contains(member.getUserId())){
				uuids.add(Integer.parseInt(member.getUserId()));
				temp.add(member);		
			}		
		}
		return temp;
	}
	
	public static void removeMembers(List<UserInfo> future,List<UserInfo> members){
		
		for(UserInfo member:members){		
			for(UserInfo one:future){					
				if(one.getUserId().equals(member.getUserId()) ){
					future.remove(one);//如果member中有uuid相同的对象，就去除掉冗余。
					break;
				}
			}
		}
	}
}
