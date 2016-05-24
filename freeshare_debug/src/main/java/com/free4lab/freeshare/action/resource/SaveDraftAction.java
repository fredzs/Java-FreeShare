package com.free4lab.freeshare.action.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.action.BaseAction;

/**
 * 处理保存草稿
 * 
 * @author zhaowei
 * 
 */
@Deprecated
public class SaveDraftAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4606115916449813789L;
	private static Logger logger = Logger.getLogger(SaveDraftAction.class);
	private Integer id;
	private String name;
	private String description;
	private Integer type;
	private String extension; // 资源的后缀名，如果存在的话
	private String enclosure;
	private String toMySelf; // 记录是否是自己保存的资源
	private String writegroups;
	private String status;

	public String execute() {

		logger.info("开始保存草稿。");
		JSONObject json = new JSONObject();
		if(type == null){
			type = ResourceTypeConst.TYPE_TEXT;
		}
		try {
			json.put("name", name).put("description", description)
					.put("type", type).put("extention", extension)
					.put("enclosure", enclosure).put("toMySelf", toMySelf)
					.put("writegroups", writegroups);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		save(id, getSessionUID(), json.toString());
		status = "success";
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getToMySelf() {
		return toMySelf;
	}

	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}

	public String getWritegroups() {
		return writegroups;
	}

	public void setWritegroups(String writegroups) {
		this.writegroups = writegroups;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void save(Integer draftId, Integer userId, String content) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		int port = 3333;
		try {
			socket = new Socket("localhost", port);
			// 发送到服务器的内容
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);
			StringBuffer sb = new StringBuffer();
			if(draftId != null){
				sb.append(id);
				sb.append("##");
			}
			sb.append(userId);
			sb.append("##");
			String encodeContent = URLEncoder.encode(content, "utf8");
			sb.append(encodeContent);
			out.print(sb.toString());
			out.flush();
			// 服务器响应返回的内容
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			id = Integer.parseInt(in.readLine());
			sb = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
