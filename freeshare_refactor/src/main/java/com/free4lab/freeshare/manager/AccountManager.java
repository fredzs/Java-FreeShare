package com.free4lab.freeshare.manager;

import java.sql.Timestamp;
import com.free4lab.freeshare.model.dao.LoginLogDAO;
public class AccountManager {
	
	public static boolean writeLogin(Integer userId){
		LoginLogDAO dao = new LoginLogDAO();
		Timestamp last = new Timestamp(System.currentTimeMillis());
		dao.updateLogin(userId, last);
		return true;
	}
}
