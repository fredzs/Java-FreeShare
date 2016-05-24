package com.free4lab.freeshare.action.group.administration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;
import com.free4lab.freeshare.action.group.GetGroup;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.util.MessageUtil;
import com.free4lab.utils.account.UserInfo;


public class ApplyAction extends BaseAction {

	/**
	 * 成员申请加入一个群组的Action。
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApplyAction.class);
	private Integer groupId;
	private String groupName;
	private String applyReason;
	private static final String FREEIN = "freein";

	private final static String direct_url = "http://freeshare.free4lab.com/group/getgroup?groupId=";
	
	
	// this function is for the users to prepare to apply to be into a group.
	//执行结果，弹出用户申请加入群组的弹出框。
	public String prepare(){
		Group group = GroupManager.getSimpGroup(groupId);
		setGroupName(group.getGroupInfo().get("name"));
		//进行判断，如果是自由加入的群组，
		setApplyReason("");
		//对群组类型进行判断，如果是自由加入的群组，就直接将其加为成员。
		if(group.getExtend().equals("freeIn")){
			logger.info("freeIn");
			GroupUserManager.addMembers(group,getSessionUID());
			return FREEIN;
		}
		else{
			return SUCCESS;
		
		}
	}
	public String execute() {
		Group group = GroupManager.getSimpGroup(groupId);
		setGroupName(group.getGroupInfo().get("name"));
		sendEmailAndMsg();
	
			
		logger.info(group.getExtend());
		
		if (VerifyUserManager.checkUserType(groupId, getSessionEmail(), 0) == false) {
				VerifyUserManager
						.addUser(groupId, getSessionEmail(), UserType.TYPE_APPLY,
								groupName, applyReason, "extend",getSessionUID());
		}
		
		return SUCCESS;
		
	}
	private boolean sendEmailAndMsg(){
		
	
		List<UserInfo> uiList = GroupUserManager.getAdmin(groupId,getSessionToken());
		String url = "";
		if (uiList != null) {
			List<String> emails = new LinkedList<String>();
			List<Integer> ids = new LinkedList<Integer>();
			for (UserInfo ui : uiList) {
				emails.add(ui.getEmail());
				ids.add(Integer.parseInt(ui.getUserId()));
			}
			
			url =  direct_url + groupId +"&kind=1";
			
			String subject = "用户"+getSessionUserName()+"申请加入“";
			subject += groupName+ "”群组";
	
			String content = getEmailContent(url);	
			try {
					
				//开启一个单独的线程向管理员发送邮件。
				ApplyEmailThread emailThread = new ApplyEmailThread(subject,content,emails);
				Thread emailT = new Thread(emailThread);
				emailT.start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ids.size() > 0) {
				//开启一个单独的线程向管理员发送站内消息。
				logger.info("ids.size"+ids.size());
				logger.info("ids:"+ids.toString());
				ApplyMessageThread messageThread = new ApplyMessageThread(ids,url,subject);
				Thread messageT = new Thread(messageThread);
				messageT.start();
			}
			
		 }
		return true;
	}
	//public static  Send
	private String getEmailContent(String url){
		String content = "&nbsp;&nbsp;您好!<br/>";
		content += "&nbsp;&nbsp;";
		content += getSessionUserName();
		content += "申请加入";
		content += groupName;
		content += "兴趣组，请访问";
		content += "<a href=";
		content += url;
		content += ">";
		content += url;
		content += "</a>";
		content += "进行相关处理。<br/>";
		content += "&nbsp;&nbsp;申请理由是：";
		content += applyReason;
		return content;
	}
	class ApplyEmailThread implements Runnable {

		private String subject;
		private String content;
		private List<String> emailContainer;
		
		public ApplyEmailThread(String subject,String content,List<String> emailContainer){
			this.subject = subject;
			this.content = content;
			this.emailContainer = emailContainer;
		}
		public void run() {
			
			try {
				SendMail.sendMail(emailContainer, subject, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	class ApplyMessageThread implements Runnable{
		private String url;
		private List<Integer> idContainer;
		private String title;
		public ApplyMessageThread(List<Integer> idContainer,String url,String title){
			this.idContainer = idContainer;
			this.title = title;
			this.url = url;
		}
		public void run() {
					
			MessageUtil.sendMessage(title, url, idContainer);
			
		}
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

}
