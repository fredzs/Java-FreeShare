package com.free4lab.freeshare.manager;

import java.util.List;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.*;

/**
 * @author Administrator
 * 处理组与用户建立关系之前的关系
 */
public class VerifyUserManager {
	//记录一条用户和组之间邀请或申请
	public static VerifyUser addUser(Integer groupId, String email, Integer type, String groupName, String reason, String extend,Integer userId){
		return new VerifyUserDAO().add(groupId, email, type, groupName,  reason, extend,userId);
	}
	/**
	 *同意用户加入，删除记录 
	*/
	public static void delete(Integer groupId, List<String> emails){
		for(String email:emails){
			new VerifyUserDAO().delete(groupId, email);
		}
	}
	public static void delete(Integer groupId, String email){
		
		new VerifyUserDAO().delete(groupId, email);
		
	}
	/**
	 * 根据type返回邀请或者申请用户
	 * @param groupId,type
	 * @return List<VerifyUser>
	*/
	public static List<VerifyUser> findVerifyUserByGroupId(Integer groupId,Integer type){
		return new VerifyUserDAO().findVerifyUserByGroupId(groupId, type);
	} 
	public static List<VerifyUser> findVerifyUserByEmail(String email){
		return new VerifyUserDAO().findVerifyUserByEmail(email);
	}

	public static void main(String[] arg){
		Integer groupId = 229;
		String email="free@free4lab.com";
		findVerifyUser(groupId,email);
	}

	public static boolean findVerifyUser(Integer groupId, String email){
	
		if(new VerifyUserDAO().find(groupId, email)==null){
			
			return true;
		}else
			return false;
		
	}
	public static boolean checkUserType(Integer groupId,String email,Integer type){
		VerifyUserDAO dao = new VerifyUserDAO();
		VerifyUser result = dao.checkType(groupId, email,type);	
		if(result!=null){
			
			return true;
		}
		else{
			return false;
		}
		
	}
	//得到所有申请或者邀请的记录
	public static List<VerifyUser> findAll(){
		return new VerifyUserDAO().findAll();
	}
}
