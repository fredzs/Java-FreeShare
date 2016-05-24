package com.free4lab.freeshare.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageUtil {
	/*private final static String __postURL = "http://freemessageapi.free4lab.com/api/sendMessage";//freemsg能力API
	private final static String accessToken = "17f49d41";//签约能力所获得的accessToken
	private final static String appDevId = "freemessage100@163.com";//开发者邮箱
	private final static String appName = "freeshare";//应用名称
	private final static String secretKey="76ae403ca37f48b39e3936888b8a0aba";//签约能力所获得的secretKey
*/		
	private final static String __postURL = "http://webrtcmessage.free4lab.com/api/sendMessage";//能力API
	private final static String accessToken = "9c49b51c";//签约能力所获得的accessToken

	private final static String appDevId = "freeshare2013@163.com";//开发者邮箱
	private final static String appName = "freeshare";//应用名称
	private final static String secretKey="4afdb696a6ab492a9ded6560a2dd35ef";//签约能力所获得的secretKey
	
	private final static String FREESHAREAPPID = "646";//share在userInfo里的accountId
	public static void main(String[] args){
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(184);
		sendMessage("test","http://freeshare.free4lab.com/members/getinvitation",ids);
	}
	static  public boolean sendMessage(String title,String url,List<Integer> userIds){
		long time = System.currentTimeMillis();//发送时间
		String signature = DigestUtils.md5Hex(appName + appDevId + time + secretKey);

		JSONObject inviteDescription = new JSONObject();
		
		try{		
			String content = "<a href=\""+url+"\" target=\"_blank\">"+title+"</a>";
			inviteDescription.put("content", content);
			inviteDescription.put("fromId", FREESHAREAPPID);
			//inviteDescription.put("redirect",java.net.URLEncoder.encode(url));
		}catch(JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONObject param = new JSONObject();
		try {
			param.put("accessToken", accessToken);
			param.put("time", time);
			param.put("signature", signature);
			param.put("appDevId", appDevId);
			param.put("appName", appName);

			param.put("userIds", userIds);
			param.put("title", title);
			param.put("description", inviteDescription);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(__postURL);

		try {

			httppost.setEntity(new StringEntity(param.toString(), "utf-8"));
			httppost.addHeader("Content-Type", "application/json");
			
			HttpResponse response = client.execute(httppost);
			//System.out.println(inputstreamToString(response.getEntity().getContent(), "utf-8"));
			
		} catch (UnsupportedEncodingException e) {	
			e.printStackTrace();
		} catch (ClientProtocolException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
