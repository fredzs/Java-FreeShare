package com.free4lab.freeshare.interceptor;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.dao.GroupPermissionDAO;
import com.free4lab.freeshare.model.dao.VerifyUserDAO;
import com.free4lab.utils.userinfo.UserInfo;
import com.free4lab.utils.userinfo.UserInfoUtil;

/**
 * 检查访问者在要访问的群组中的身份
 * 
 * @author zhaowei
 * 
 */
public class IdentityInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(IdentityInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {

		Integer groupId = InterceptorUtil.getGroupId(invocation);
		Integer userId = InterceptorUtil.getUserId(invocation);
		Integer userType = -1;
		if (GroupUserManager.checkMember(userId, groupId)) {
			GroupPermission gp = new GroupPermissionDAO().findPermission(userId, groupId);
			if(gp != null){
				userType = gp.getType();
			}else{
				userType = UserType.TYPE_MEM;
			}
			logger.info("是该组成员，且身份为 ：" + userType); 
		} else {
			String email = (String) ActionContext.getContext().getSession().get("email");
			if (new VerifyUserDAO().checkType(groupId, email,
					UserType.TYPE_INVITED) != null) {				
					userType = UserType.TYPE_INVITED;
				
			} else {
				String groupType = GroupManager.getSimpGroup(groupId).getExtend();
				if(groupType.equals("public")){
					userType = UserType.TYPE_APPLY2;
				}
				else{
					userType = UserType.TYPE_APPLY;
				}
			}
			logger.info("不是该组成员，且身份为 ：" + userType); 
		}
		ActionContext.getContext().getContextMap().put("type", userType);
		return invocation.invoke();
	}
}