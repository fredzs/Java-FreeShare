/**
 * 
 */
package com.free4lab.freeshare.manager.search;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.utils.http.SearchClient;

/**
 * @author wenlele
 * 
 */
public class SearchManager {

	private static final Logger logger = Logger.getLogger(SearchManager.class);
	private static final SearchClient client = new SearchClient(URLConst.APIPrefix_FreeSearch);

	/**
	 * 搜索资源索引
	 * 
	 * @param query
	 *            查询词（如果不需要，则设为空字符串）
	 * @param page
	 *            页码
	 * @param size
	 *            分页大小
	 * @param tags
	 *            tags参数，多个tag必须以空格隔开
	 * @param sortMode
	 *            结果排序类型，freesearch此功能暂时无用
	 * @return SrhResult 资源索引内容。如果搜索内容为null，或结果无法解析，也返回null。
	 */
	public static SrhResult searchItems(String query, Integer page,
			Integer size, String tags) {
		String result = getSearchResult(query, page, size, tags);
		try {
			JSONObject obj = new JSONObject(result);
			return new SrhResult(obj);
		} catch (NullPointerException npe) {
			logger.debug("SearchManager searchNews:return null", npe);
		} catch (JSONException je) {
			logger.error("Analysis Json object failed!", je);
		} catch (Exception e) {
			logger.debug("Analysis Json object failed!", e);
		}
		return null;
	}

	// 搜索属于某列表内的资源，按照tag升序返回
	public static SrhResult searchListItems(Integer page, Integer size,
			String tags) {
		try {
			String result = client.search(null, tags, "ASC:tagValue:XXX", page,
					size, null);
			JSONObject obj = new JSONObject(result);
			return new SrhResult(obj);
		} catch (NullPointerException npe) {
			logger.error("NullPointerException by " + tags, npe);
		} catch (JSONException je) {
			logger.error("Analysis Json object failed!", je);
		} catch (Exception e) {
			logger.error("Exception!!!", e);
		}
		return null;
	}

	/*
	 * 通过搜索得到JSON字符串 通过字符串，获取评论结果
	 */
	public static SrhResult searchCmt(Integer page, Integer size, String tags) {
		try {
			String result = client.search("", tags, "ASC:TIME:XXX", page,
					size, null, false);
			JSONObject obj = new JSONObject(result);
			return new SrhResult(obj);
		} catch (NullPointerException npe) {
			logger.error("SearchManager searchNews:return null", npe);

		} catch (JSONException je) {
			logger.error("Analysis Json object failed!", je);
		} catch (Exception e) {
			logger.error("Exception!", e);
		}
		return null;
	}

	/**
	 * 获取搜索结果字符串
	 * 
	 * @return String 搜索结果 （如果搜索失效，返回null）
	 */
	public static String getSearchResult(String query, Integer page,
			Integer size, String tags) {

		try {
			logger.debug("the tags is " + tags);
			long start  = System.currentTimeMillis();
			String result = client.search(query, tags, null, page, size, null);
			long end = System.currentTimeMillis();
			logger.debug("the result's time is " + (end- start));
			// logger.info("the search result is " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
