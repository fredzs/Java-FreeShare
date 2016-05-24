package com.free4lab.freeshare.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class SendMail {

	public static void sendMail(List<String> toEmail,String subject, String content) throws Exception{
        
		String appName = "freeshare";//应用名称
	   	String __postURL = "http://mail.free4lab.com/api/sendmailapi";//能力地址
	   	String appDevId = "free@free4lab.com";
	   	String accessToken = "a9afb70f";
	   	String secretKey = "0c2adada34404127baebd43502335f3d";
		long time = System.currentTimeMillis();//发送时间
		String signature = DigestUtils.md5Hex(appName + appDevId + time + secretKey);

		JSONObject inviteDescription = new JSONObject();
		
		try{		
			inviteDescription.put("content", content);
		}catch(JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject param = new JSONObject();
		try {
			param.put("accessToken", accessToken);
			param.put("time", time);
			param.put("signature", signature);
			param.put("appDevId", appDevId);
			param.put("appName", appName);

			param.put("userIds", toEmail);
			param.put("title", subject);
			param.put("description", inviteDescription);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(__postURL);

		try {
			httppost.setEntity(new StringEntity(param.toString(), "utf-8"));
			httppost.addHeader("Content-Type", "application/json");
			client.execute(httppost);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
