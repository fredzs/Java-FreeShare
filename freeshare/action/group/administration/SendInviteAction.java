package com.free4lab.freeshare.action.group.administration;

import java.io.*;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.util.MessageUtil;

public class SendInviteAction extends BaseAction{

	/*
	  1、将前台传进来的全部的邮件处理字符串。
	  2、针对站内成员，和站外的邮件分别放入不同的队列中。
	  3、全部发送邮件。
	  4、站内成员还有freemessage的提醒。
	  
	  */
	/*private final static String __postURL = "http://freemessageapi.free4lab.com/api/sendMessage";//freemsg能力API
	private final static String accessToken = "1852062e";//签约能力所获得的accessToken
	private final static String appDevId = "chi-s@163.com";//开发者邮箱
	private final static String appName = "freeshare";//应用名称
	private final static String secretKey="ef651cb4973244dc8bee18dfbf3ca396";//签约能力所获得的secretKey
	private final static String FREESHAREAPPID = "appIdfreeshare";
*/		
	private final static String url = "http://freeshare.free4lab.com/members/getinvitation";
	private Logger logger = Logger.getLogger(SendInviteAction.class);
	private Integer groupId;
	private String selectedList;
	private String selectIds;
	private List<Integer> idList;
	private List<String> emailList;
	private String title;
	private String reason;
	public String execute(){
		
		if(selectedList != null){
			/*System.out.println("selectedList:"+selectedList);
			System.out.println("selectIds:"+selectIds);*/
			if(reason.contains(url)){
				   reason = reason.replace(url, "<a href ="+url+">"+url+"</a>");
				   reason = reason.replace("\n", "<br/>");
		    }
			emailList = getEmailList();
			//发送邮件
			try {
					//SendMail.sendMail(emailList,title,reason);
				MailSender sender = new MailSender();
				Thread thread1 = new Thread(sender);
				thread1.start();
			} catch (Exception e) {
					e.printStackTrace();
			}
		  
			//发送站内信
		    if(idList.size() > 0){		
		    	   MsgSender msgSender = new MsgSender();
		    	   Thread thread2 = new Thread(msgSender);
		    	   thread2.start();
				  // MessageUtil.sendMessage(title, url,idList);
			}		   
		    //将站内的用户和单纯的email的用户通过VerifyUserManager写入futureMember的表中。
		    Group group = GroupManager.getSimpGroup(groupId);
		    int i;
		    for(i = 0;i < idList.size();i++){
		    	
		    	  String email = emailList.get(i);
		    	 
		    	  if (VerifyUserManager.findVerifyUser(groupId, email)) {		    		  
		    		  VerifyUserManager.addUser(groupId, email,
								UserType.TYPE_INVITED, group.getGroupInfo().get("name"), "被邀请", "extend",idList.get(i));
		    	  }
		    	  else{
		    		 
		    	  }
		    }
		    
		    for(i = 0;i < emailList.size();i++){
		    	 String email = emailList.get(i);
		    	 if (VerifyUserManager.findVerifyUser(groupId, email)) {
		    		 VerifyUserManager.addUser(groupId, email,
								UserType.TYPE_INVITED, group.getGroupInfo().get("name"), "被邀请", "extend",null);
		    	  }
		    	 }
		    }

		return SUCCESS;
	}
	public void sendInviteAgain(){
		//首先要将一个长串，将id和email进行处理。
		logger.info("sendInviteAgain:"+selectedList);
		/*//处理前端按钮中的全选的bug。前端处理了。
		if(selectedList.startsWith("on")){
			selectedList = selectedList.substring(selectedList.indexOf(",")+1);
		}*/
		//logger.info("sendInviteAgain:"+selectedList);
		Group group = GroupManager.getSimpGroup(groupId);
		String[] users =  selectedList.split(",");
		idList = new ArrayList<Integer>();
		Integer i;
		List<String> emailList = new LinkedList<String>();
		for(i = 0;i < users.length;i++){
			String[] idOrEmail = users[i].split(":");
			try{
				Integer tmp = Integer.parseInt(idOrEmail[0]);
				idList.add(tmp);
			}
			catch(Exception e){
				logger.info("tmp2:");
			}
			if(0 == idOrEmail[1].indexOf("'")){
				idOrEmail[1] = idOrEmail[1].substring(1,idOrEmail[1].lastIndexOf("'"));
				logger.info("idOrEmail[1]:"+idOrEmail[1]);
				emailList.add(idOrEmail[1]);
			}
			else{
				logger.info("idOrEmail[1]222:"+idOrEmail[1].indexOf("'"));
				emailList.add(idOrEmail[1]);
				
			}
		}
		//发送邮件
		String groupname = group.getGroupInfo().get("name");
		title = "邀请您加入"+groupname+"小组";
		reason = "尊敬的用户，您好！\n      "+getSessionUserName()+"邀请您加入“"+groupname;
		reason += "”兴趣组。如果您已经拥有Free账号，请点击";
		reason += "http://freeshare.free4lab.com/members/getinvitation";
		reason += "进行相关处理。";
		reason += "如果您尚未拥有Free账号，请在http://freeshare.free4lab.com/members/getinvitation页面点击注册按钮进行相关注册\n\t\t\t\t\t“";
			//	" http://account.free4lab.com/register?customId=freeshare&handleUrl=http%3A%2F%2Ffreeshare.free4lab.com%3A80%2Flogin 进行相关注册。\n\t\t\t\t\t“";
		reason += groupname;
		reason += "”小组管理员"; 
	    logger.info("title:"+title);
	    logger.info("reason:"+reason);
		try {
				SendMail.sendMail(emailList,title,reason);
		} catch (Exception e) {
				e.printStackTrace();
		}
	  
		//发送站内信
	    if(idList.size() > 0){		    	   
			   MessageUtil.sendMessage(title, url,idList);
		}		  
		
	}
	private List<String> getEmailList(){
	
		Map<Integer,String[]> tmpMap = new HashMap<Integer,String[]>();
		List<String> emailList = new ArrayList<String>();
		String[] idNameEmails = selectIds.split(",");
		for(String id:idNameEmails){
		    if(id.indexOf(":") != -1){
		    	String[] tmp = id.split(":");	    	
		    	tmpMap.put(Integer.parseInt(tmp[0]), tmp);
		    	
		    }
		}
		if(selectedList.lastIndexOf(",") == selectedList.length()){
			selectedList = selectedList.substring(0, selectedList.length() - 1);
		}
		String[] selectName = selectedList.split(",");
		List<String> outsiders = new ArrayList<String>();
		idList = new ArrayList<Integer>();
		for(String name:selectName){
			
			String tmpName = null;
			//如果是站内的成员，就应该在tmpMap中进行Key的查询，如果存在的话，就在emailList的容器中存储。
			if(name.indexOf("(") != -1){
				tmpName = name.substring(name.indexOf("(")+1, name.indexOf(")"));
				Integer id = Integer.parseInt(tmpName);
				if(tmpMap.containsKey(id)){					
					idList.add(id);
					emailList.add(tmpMap.get(id)[2]);
					
				}
			}
			else{
				outsiders.add(name);
			}
			
		}
		//需要将只通过email邀请的人加入到emailList中，要放在最后，这样就保证了前面的idList和emailList，是一一对应的，方便VerifyManager进行调用。
		emailList.addAll(outsiders);
		return emailList;
	}
	
	public List<Integer> getIdList() {
		return idList;
	}
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
	
	
	public String inputstreamToString(InputStream in, String charsetName)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in,
				charsetName));

		StringBuffer sb = new StringBuffer();
		String tmp;
		while ((tmp = br.readLine()) != null) {
			sb.append(tmp);
		}
		return sb.toString();
	}
	
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getSelectedList() {
		return selectedList;
	}
	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(String selectIds) {
		this.selectIds = selectIds;
	}
	public class MailSender implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try {
				SendMail.sendMail(emailList,title,reason);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public class MsgSender implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try {
				 MessageUtil.sendMessage(title, url,idList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
