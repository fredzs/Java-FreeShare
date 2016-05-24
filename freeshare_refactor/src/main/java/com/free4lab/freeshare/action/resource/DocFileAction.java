package com.free4lab.freeshare.action.resource;

import java.io.File;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.utils.http.DiskClient;

/**
 * 对上传文档的操作：上传、删除
 * 
 * uploadFile：前端页面进行文档上传的函数（在提交之间就进行上传了。） 
 * delUploadedFile:前端页面进行文档的删除函数。
 * 注意：附件只有一个，如果有多余的附件就首先进行删除附件工作，重新添加附件。
 * 
 * @author Administrator
 * 
 */
public class DocFileAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -285675419776162342L;

	private static final Logger logger = Logger.getLogger(DocFileAction.class);

	private File doc;
	private String docContentType;
	private String docFileName;
	private String docUuid;

	// 上传资源
	public String uploadFile() {
		try {
			String fileName = this.getDocFileName();
			fileName = getDecodeString(fileName);
			fileName = URLEncoder.encode(fileName, "UTF8");
			logger.info("the filename is " + fileName);
			docUuid = DiskClient.upload(this.getDoc(), fileName, null);
		} catch (Exception ex) {
			logger.debug("upload error:" + ex.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	// 删除已上传的资源
	public String delUploadedFile() {
		try {
			DiskClient.delete(docUuid);
		} catch (Exception ex) {
			logger.debug("upload error:" + ex.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getDocUuid() {
		return docUuid;
	}

	public void setDocUuid(String docUuid) {
		this.docUuid = docUuid;
	}

}
