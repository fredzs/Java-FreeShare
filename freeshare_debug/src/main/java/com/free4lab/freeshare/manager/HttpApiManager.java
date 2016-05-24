package com.free4lab.freeshare.manager;

import com.free4lab.utils.http.HttpCrawler;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpApiManager {

	private static final Logger logger = Logger.getLogger(HttpApiManager.class);
	private static final String PARAM = "param";
	/**
	 * 
	 * @param url
	 * @param params
	 * @return 搜索结果字符串
	 */
	public static String invokeApi(String url, Map<String, List<String>> params) {
		String result = null;
		try { 
			result = HttpCrawler.getHtmlDoc(url, params, 4000);
			if (null != result) {
				logger.info("返回结果为: " + result);
			} else {
				logger.info("返回结果为null!!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @return 搜索结果字符串
	 */
	public static String invokeApi(String url, String params) {
		String result = null;
		try { 
			url = url + PARAM + "=" + URLEncoder.encode(params, "utf8");
			logger.info("req url : " + url);
			result = HttpCrawler.getString(url, 4000);
			if (null != result) {
				logger.info("返回结果为: " + result);
			} else {
				logger.info("返回结果为null!!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
