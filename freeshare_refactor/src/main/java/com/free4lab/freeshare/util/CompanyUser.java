package com.free4lab.freeshare.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.free4lab.utils.http.HttpCrawler;

public class CompanyUser{
	final static Logger logger = Logger.getLogger(CompanyUser.class);
	final static int MAX_FREEACCOUNT_ATTEMPTS = 10;
	
	final static String URL_ACCOUNT = "http://newaccount.free4lab.com/";
	final static String ADD_COMPANYUSER = "basic/addUser";
	final static String UPDATE_COMPANY_OF_USER = "basic/setUserCompany";
	
	public String addNewCompanyUser(String email,String pwdMD5,String company){
		String action = ADD_COMPANYUSER;
		JSONObject obj = null;
		String result = "-2";
		
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> emailKey = new ArrayList<String>();
		emailKey.add(email);
		List<String> pwdMD5Key = new ArrayList<String>();
		pwdMD5Key.add(pwdMD5);
		List<String> companyKey = new ArrayList<String>();
		companyKey.add(company);
		params.put("email", emailKey);
		params.put("passwordMd5", pwdMD5Key);
		params.put("company", companyKey);
		logger.info("param:"+params.get("email"));
		try {
			obj = getTargetValue(action, params);
			System.out.println(obj);
			if(obj != null && obj.has("status")){
				result = obj.getString("status");
			}
			logger.info("status"+"="+result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//根据action和参数返回json对象
	public static JSONObject getTargetValue(String url, Map<String, List<String>> params) {
		String result = null;
		int i = 0;//获取result，共测试MAX_FREEACCOUNT_ATTEMPTS次
		url = URL_ACCOUNT + url;
		while ((result == null || "".equalsIgnoreCase(result)) && i <= MAX_FREEACCOUNT_ATTEMPTS) {
			logger.info(url+";"+params);
			result = HttpCrawler.getHtmlDoc(url, params);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			return new JSONObject();
		}
	}
	
	public String updateCompanyOfUser(Integer uid, String company ){
		String action = UPDATE_COMPANY_OF_USER;
		JSONObject obj = null;
		String result = "-2";
		
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> uidKey = new ArrayList<String>();
		uidKey.add(uid.toString());
		List<String> companyKey = new ArrayList<String>();
		companyKey.add(company);
		params.put("uid", uidKey);
		params.put("company", companyKey);
		logger.info("param:"+params.get("uid"));
		try {
			obj = getTargetValue(action, params);
			System.out.println(obj);
			if(obj != null && obj.has("status")){
				result = obj.getString("status");
			}
			logger.info("status"+"="+result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}