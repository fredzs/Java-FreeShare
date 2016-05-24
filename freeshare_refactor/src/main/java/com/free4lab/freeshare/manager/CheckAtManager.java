package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.free4lab.freeshare.SessionConstants;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author qie
 *
 */
public class CheckAtManager {
	private final Logger logger = Logger.getLogger(CheckAtManager.class);
	
	//检测是否有@name(id)这种形式
	public List<String> checkAt(String content){
		logger.info("content="+content);
		Pattern patternAll=Pattern.compile("@([^\\)])*\\)");  
        //通过match（）创建Matcher实例  
        Matcher matcher=patternAll.matcher(content);  
        String itemAt="";
        //检测到被@人的id列表
        List<Integer> memberIdList = new ArrayList<Integer>();
        //检测到被@人的id-name的map
        Map<Integer,String> memberList = new HashMap<Integer,String>();
        
        while (matcher.find())//查找符合pattern的字符串  
        {  
        	itemAt = matcher.group(0);
        	if(itemAt.indexOf("(") != -1){     		
        		String[] itemObj = itemAt.split("\\(");   
        		//拆分出@name(id)中的name
        		String userName = itemObj[0].substring(1);
        		logger.info("userName="+userName);
        		//拆分出@name(id)中的id
        		Integer itemUserId = Integer.parseInt( itemObj[1].substring(0, itemObj[1].length()-1));
        		logger.info("userId="+itemUserId);
        		
        		memberIdList.add(itemUserId);
        		memberList.put(itemUserId, userName);      		
        	}  	
        }  
        if(memberList != null && memberList.size()>0){
        	logger.info("发送="+memberList.get(0));
        	return checkViaUserInfo(memberList,memberIdList);
        }else{
        	return null;
        }       	
	}
	
	//检测@name(id)是否合法，通过userInfo获取id和name，查看是否与传过来的memberList map相匹配
	public List<String> checkViaUserInfo(Map<Integer,String> memberList,List<Integer> memberIdList){
		String token = (String)ActionContext.
    			getContext().getSession().get(SessionConstants.AccessToken);
		logger.info("index="+memberList);
		//根据id列表获取userInfo列表，注意这里得到的返回值的顺序和我传过去的memberIdList的顺序不一致
    	List<UserInfo> memberUserInfoList = UserInfoUtil.getUserInfoByUid(token, memberIdList);

    	//发消息提醒的收信人，形式为name-id
    	List<String> toUserNameToIds = new ArrayList<String>();
    	
    	for(int i=0; i< memberUserInfoList.size(); i++){
    		//获取每个人的id
    		String id = memberUserInfoList.get(i).getUserId();
    		//根据上述id，获取memberList map中对应的name值
    		String editName = memberList.get(Integer.parseInt(id));
    		//获取每个人的name
			String name = memberUserInfoList.get(i).getUserName();
			logger.info("name="+name+",editName="+editName);
			//两个名字是否匹配
			if(name.equals(editName)){				
				toUserNameToIds.add(name+"-"+memberUserInfoList.get(i).getUserId());
				logger.info("toUserIds="+toUserNameToIds+",name="+name);
			}
		}
    	return toUserNameToIds;
	}
}
