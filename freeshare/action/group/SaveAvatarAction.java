package com.free4lab.freeshare.action.group;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.GroupInfo;
/*import com.free4lab.freeuserinfo.manager.UserInfoManager;
import com.free4lab.freeuserinfo.model.dao.User;*/

import com.free4lab.utils.http.DiskClient;

public class SaveAvatarAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7211233372793987188L;
	
	private static final Logger logger = Logger.getLogger(SaveAvatarAction.class);
	
	private File photo;
	private String photoContentType;
    private String photoFileName;
    private String avatar;
    private Integer groupId;
    private String message;
    private String hiddenUuid;
    private String uuid;
    private InputStream inputStr;
    private List<String> avatars;
    private String oldAvatar;
    private final static String DOWNLOAD_URL = "http://freedisk.free4lab.com/download?uuid=";
    
    public List<String> getAvatars() {
		return avatars;
	}

	public void setAvatars(List<String> avatars) {
		this.avatars = avatars;
	}

	public String getOldAvatar() {
		return oldAvatar;
	}

	public void setOldAvatar(String oldAvatar) {
		this.oldAvatar = oldAvatar;
	}

	public String execute(){
    	logger.info("in SaveAvatarAction");
		
    	logger.info("^^^^^^^^^^^^^^uuid:"+uuid);
		if(uuid != null){
			/*
			 * 如果是已经创建的group，就应该有groupid，通过groupid进行查找。
			 * 进行群组的更新。
			*/
			
			if(groupId != null ){
	    		//GroupModel gm =  GroupManager.getGroupById(groupId);
	    		Group group = GroupManager.getSimpGroup(groupId);
	    		GroupInfo oldInfo = group.getGroupInfo();
	    		String name = oldInfo.get("name");
	    		String date = oldInfo.get("date");
	    		String desc = oldInfo.get("desc");
	    		Map<String, String> descriptionMap = new HashMap<String, String>();
	    		//前后端限制长度
	    		
	    		descriptionMap.put("name", name);
	    		descriptionMap.put("date", date);
	    		descriptionMap.put("desc", desc);
	    		descriptionMap.put("avatar",uuid);
	    		String groupInfo = descriptionMap.toString();
	    		System.out.println("^^^^^^^^^^^groupInfo:"+groupInfo);
	    		GroupInfo info = new GroupInfo(groupInfo);
				group.setGroupInfo(info);
				GroupManager.updateGroupInfo(group);
	    	}
			logger.info("SaveAvatarAction");
			setAvatar(DOWNLOAD_URL+uuid);
			//if(UserInfoManager.updateUserInfo(user)){
			
			try {
					inputStr = new ByteArrayInputStream(uuid.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
					return ERROR;
			}
			return SUCCESS;
		}
		return ERROR;
    }
    
    public String preview(){
    	logger.info("in SaveAvatarAction > preview");
		uuid = avatarUUID();
		if(hiddenUuid.length() > 0){
			logger.debug("deleting " + hiddenUuid);
			try {
				DiskClient.delete(hiddenUuid);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
    }
    
    public String avatarUUID() {
    	try {
    		logger.info("uploaded doc info: " + "file name is " + this.getPhotoFileName()
    				+ " type is " + this.getPhotoContentType());
			String fileName = this.getPhotoFileName();
			fileName = getDecodeString(fileName);
			String uuid =DiskClient.upload(this.getPhoto(), fileName, null);
			return uuid;
		} catch (Exception ex) {
			logger.debug("upload error:" + ex.getMessage());
			return null;
		}
    }
    public String getAvatarsList(){
    	
    	avatar = DOWNLOAD_URL + avatar;
    	defineAvatars();				
    	return SUCCESS;
    }
    
   public String deleteOldAvatar(){
    	defineAvatars();
		if(oldAvatar != null && -1 == avatars.indexOf(oldAvatar)){
			try {
				DiskClient.delete(oldAvatar);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return SUCCESS;
    }
    
    private void defineAvatars(){
    	avatars = new ArrayList<String>();
    	avatars.add("b1cf1db3-bf5a-48e1-ad4a-41fb35d02bb4");
    	avatars.add("7a90ad0b-c9e9-448d-8d1c-359f44cfad50");
    	avatars.add("792948f9-68fa-4840-991b-8e3d66ad84d5");
    	avatars.add("8cedda98-96a8-4f5f-9e58-782c212780a7");
    	avatars.add("35a23bf2-bc44-4859-b6b7-b7d50494c82d");
    	avatars.add("d48e82a9-8f20-49ad-9597-794268569955");
    	avatars.add("f6237939-c8ff-4a71-9064-fb1fb53a051c");
    	avatars.add("a3bcfc29-8cf9-4392-a1c1-b2ae296a8b63");
    	avatars.add("bac580e1-f8f6-473b-a6a6-8af4b34963fe");
    	avatars.add("5e7ab4f6-508d-45f7-adbe-89677f552d5d");
    	avatars.add("3c7a344d-78b4-4a38-844b-46863735b265");
    	avatars.add("89f5a85d-b9ad-4109-b701-b9b7bd52da0c");
    }

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public InputStream getInputStr() {
		return inputStr;
	}

	public void setInputStr(InputStream inputStr) {
		this.inputStr = inputStr;
	}

	public String getHiddenUuid() {
		return hiddenUuid;
	}

	public void setHiddenUuid(String hiddenUuid) {
		this.hiddenUuid = hiddenUuid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
