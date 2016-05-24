package com.free4lab.freeshare.model.data;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.ResourceTypeConst;

/**
 * content的外观类。两种功能：
 * 1：把各种content的组装成一层的json结构体
 * 2：把从search接受到的数据解析成需要的数据格式
 * @author zhaowei
 *
 */
public class ContentFaced {
	
	
	/**
	 * 对各种格式的contents做最后的封装
	 * @param BasicContent
	 * @param Content
	 * @return 两个json结构体合并后的具有json结构的String
	 *
	 */
	public static String getContent(BasicContent bContent, Content content) throws JSONException{
		String bcontent = bContent.toString();
		bcontent = bcontent.substring(0, bcontent.length() - 1);
		String contentStr = content.toString();
		contentStr = contentStr.substring(1, contentStr.length());
		JSONObject json  = new JSONObject(bcontent + "," +  contentStr);
		return json.toString();
	}
	
	@Deprecated
	public Content getContent(JSONObject jsonObj){
		Content bcontent = new Content();
		try {
			JSONObject content = new JSONObject(jsonObj.getString("content"));
			if(content.getInt("type") == ResourceTypeConst.TYPE_VIDEO_URL){
				bcontent = new VideoContent(jsonObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return bcontent;
	}
}
