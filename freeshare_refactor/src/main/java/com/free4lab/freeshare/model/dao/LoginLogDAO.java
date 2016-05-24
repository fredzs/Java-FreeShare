package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.sql.Update;

import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class LoginLogDAO extends AbstractDAO<LoginLog> {
	
	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return LoginLog.class;
	}

	public static final String PU_NAME = "FreeSharePU";
	private static final Integer REMINDER_OPEN = 1;
	private static final Integer REMINDER_CLOSE = 0;

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	//如果登录成功的话，记录登录的时间。
	//如果用户是第一次登录的话，就向log_login表中插入一条记录，包含第一次登录的时间、最后一次登录的时间和登录次数1，标志是否有提醒通知等。
	//如果不是，更新相关的条目，最后一次登录的时间和登录的次数。
	public boolean updateLogin(Integer userId,Timestamp last){
		List<LoginLog> loginList = findByProperty("userId",userId);
		LoginLogDAO dao = new LoginLogDAO();
		LoginLog login;
		if(null == loginList||0 == loginList.size()){
			//创建一个新的登录记录；
			login = new LoginLog();
			login.setUserId(userId);
			login.setFirstLogin(last);
			login.setLastLogin(last);
			login.setTimes(1);
			login.setReminder(1);
			dao.save(login);
		}
		else {
			//更新原有的登录记录;
			login = loginList.get(0);	
			login.setLastLogin(last);
			login.setTimes(login.getTimes()+ 1);			
			dao.update(login);
			
		}
		return true;
	}
	public boolean modifyReminder(Integer userId){
		List<LoginLog> loginList = findByProperty("userId",userId);
		LoginLogDAO dao = new LoginLogDAO();
		if(null != loginList||0 < loginList.size()){
         	LoginLog login = loginList.get(0);
         	Integer reminder = login.getReminder();
         	if(reminder.equals(REMINDER_OPEN) ){
         		login.setReminder(REMINDER_CLOSE);
         	}
         	else{
         		login.setReminder(REMINDER_OPEN);
         	}
         	dao.update(login);
         	return true;
		}
		else{
			return false;
		}
		
	}

}
