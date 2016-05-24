package com.free4lab.freeshare.action.resource;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.action.BaseAction;

/**
 * 处理编辑操作
 * 作者对别人编辑之后的资源进行审核处理
 * @author zhaowei
 *
 */
@Deprecated
public class EditAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditAction.class);
	private Integer idd;
	private String originalValue;
	private String editValue;
	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public String getEditValue() {
		return editValue;
	}

	public void setEditValue(String editValue) {
		this.editValue = editValue;
	}

	private Edittion edittion;

	public Edittion getEdittion() {
		return edittion;
	}

	public void setEdittion(Edittion edittion) {
		this.edittion = edittion;
	}

	public String execute() {
		HttpClient client = new HttpClient();

		String requestUrl = "http://localhost:9090/get";
		requestUrl += "?id=" + idd + "&uid=" + 3;
		GetMethod method = new GetMethod(requestUrl);
		try {
			client.executeMethod(method);
			String result = method.getResponseBodyAsString();
			try {
				 logger.info("the result is " + result);
				JSONArray array = new JSONArray(result);

				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);
					edittion = new Edittion(json);
					System.out.println(json.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	public String add() throws HttpException, IOException{
		String addUrl = "http://localhost:9090/add";
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(addUrl);  
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		method.addParameter("id", "3");
		method.addParameter("value", originalValue);
		method.addParameter("editvalue", editValue);
		method.addParameter("uid", "3");
		method.addParameter("type","0");
		client.executeMethod(method);
		return SUCCESS;
	}
	
	public Integer getIdd() {
		return idd;
	}

	public void setIdd(Integer idd) {
		this.idd = idd;
	}

	public class Edittion {
		private String edittion;
		private Integer id;
		private String editname;
		private String time;

		public Edittion(JSONObject json) throws JSONException {
			this.setEdittion(json.getString("edittion"));
			this.setEditname("edittion");
			this.setTime(json.getString("edit_time"));
			this.setId(json.getInt("original_id"));
		}

		public String getEdittion() {
			return edittion;
		}

		public void setEdittion(String edittion) {
			this.edittion = edittion;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getEditname() {
			return editname;
		}

		public void setEditname(String editname) {
			this.editname = editname;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

	}
}
