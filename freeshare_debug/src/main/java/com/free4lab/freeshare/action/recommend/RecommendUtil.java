package com.free4lab.freeshare.action.recommend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.json.JSONException;

import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.manager.HttpApiManager;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.utils.recommend.RecommendObject;
import com.free4lab.utils.recommend.RecommendQuery;
import com.free4lab.utils.recommend.RecommendResults;

/**
 * @author zhaowei
 * 
 */
public class RecommendUtil {
	private static Logger logger = Logger.getLogger(RecommendUtil.class);
	private static String RECOMMEND_URL = URLConst.APIPrefix_Recommend +"/api/recommend?";
	private static String LOG_URL = URLConst.APIPrefix_Recommend +"/api/log?";
	private static final String POST_LOG_URL = URLConst.APIPrefix_Recommend +"/api/log";
	static {
		try {
			String hostname = InetAddress.getLocalHost().getHostName();

			// ... sorry for this
			if (hostname.equals("LAB-WANGCHAO")) {
				logger.info(hostname);
				RECOMMEND_URL = "http://127.0.0.1:8081/api/recommend?";
				LOG_URL = "http://127.0.0.1:8081/api/log?";
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	static RecommendUtil instance;

	public static synchronized RecommendUtil getInstance() {
		if (instance == null) {
			instance = new RecommendUtil();
		}
		return instance;
	}

	private RecommendUtil() {

	}

	/**
	 * 通过post方式提交日志
	 * 
	 * @param uid
	 * @param itemid
	 * @param resourceType
	 * @param behaviorType
	 */
	public static void postLog(Integer uid, Integer itemId,
			String behaviorType, ObjectAdapter adapter) {
		try {
			postLog(RecommendObject.toID(uid, Constant.ITEM_TYPE_USER),
					toRecommendId(itemId, adapter.getType()), behaviorType,
					adapter);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	static void postLog(Long uid, Long itemId, String behaviorType,
			ObjectAdapter adapter) throws HttpException, IOException,
			JSONException {
		BehaviorLog log = new BehaviorLog();
		log.setBehaviorType(behaviorType);
		log.setTimestamp(System.currentTimeMillis());
		RecommendObject userRO = new RecommendObject(uid);

		RecommendObject itemRO = new RecommendObject();
		itemRO.setId(itemId);
		itemRO.setName(adapter.getName());
		itemRO.setContent(adapter.getDescription());
		List<Long> belongs = toLongs(adapter.getGroupIds(),
				Constant.ITEM_TYPE_GROUP);
		itemRO.setBelongs(belongs);
		log.setA(userRO);
		log.setB(itemRO);

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(POST_LOG_URL);
		method.getParams().setParameter(HttpMethodParams.CREDENTIAL_CHARSET,
				"utf-8");
		method.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		method.addParameter("param", log.toJSON());
		client.executeMethod(method);
	}

	/**
	 * 日志
	 * 
	 * @param uid
	 * @param itemid
	 * @param shareType
	 *            资源类型
	 * @param behaviorType
	 *            行为类型
	 */
	public static void log(Integer uid, Integer itemid, Integer shareType,
			String behaviorType) {
		try {
			log(RecommendObject.toID(uid, Constant.ITEM_TYPE_USER),
					toRecommendId(itemid, shareType), behaviorType);
		} catch (JSONException e) {
			logger.error("推荐日志记录出错！！", e);
		}
	}

	static void log(Long uid, Long itemId, String behaviorType)
			throws JSONException {
		RecommendObject userRO = new RecommendObject(uid);
		RecommendObject itemRO = new RecommendObject(itemId);
		log(userRO, itemRO, behaviorType);
	}

	/**
	 * 用户同时操作两个对象日志记录：加如列表/加入群组
	 * 
	 * @param uid
	 * @param fromId
	 * @param fromType  share type
	 * @param toIds
	 * @param toType    share type
	 * @param behaviorType
	 */
	public static void log(Integer uid, Integer fromId, Integer fromType,
			List<Integer> toIds, Integer toType, String behaviorType) {
		try {
			RecommendObject userRO = new RecommendObject(RecommendObject.toID(uid,
					Constant.ITEM_TYPE_USER));
			RecommendObject itemRO = new RecommendObject(toRecommendId(fromId,
					fromType));
			List<Long> belongs = toLongs(toIds, formatType(toType));
			itemRO.setBelongs(belongs);
			log(userRO, itemRO, behaviorType);
		} catch (JSONException e) {
			logger.error("推荐日志记录出错！！", e);
		}
	}

	public static void log(RecommendObject a, RecommendObject b,
			String behaviorType) throws JSONException {
		BehaviorLog log = new BehaviorLog();
		log.setBehaviorType(behaviorType);
		log.setTimestamp(System.currentTimeMillis());

		log.setA(a);
		log.setB(b);
		HttpApiManager.invokeApi(LOG_URL, log.toJSON());
	}

	public static RecommendResults recommend(Integer uid, Long itemId,
			Integer qType, Integer recNum ,Integer recByUser) throws JSONException {
		ArrayList<Long> items = new ArrayList<Long>(1);
		items.add(itemId);
		return recommend(uid, items, qType, recNum, recByUser);
	}

	public static RecommendResults recommend(Integer uid, List<Long> items,
			Integer qType, Integer recNum,Integer recByUser) throws JSONException {
		if (uid == null) {
			return new RecommendResults();
		}
		Long userId = RecommendObject.toID(uid, Constant.ITEM_TYPE_USER);
		RecommendQuery query = new RecommendQuery(false, qType, userId, items);
		query.setResultNum(recNum);
		
		if(recByUser == 1) query.setRecommendUser(true);
		else query.setRecommendUser(false);
		
		String result = HttpApiManager.invokeApi(RECOMMEND_URL, query.toJSON());
		RecommendResults results = RecommendResults.fromJSON(result);
		return results;
	}

	public static RecommendResults recommend(Integer uid, List<Long> items)
			throws JSONException {
		if (uid == null) {
			return new RecommendResults();
		}
		Long userId = RecommendObject.toID(uid, Constant.ITEM_TYPE_USER);
		RecommendQuery query = new RecommendQuery(false,
				Constant.ITEM_TYPE_RESOURCE, userId, items);

		String result = HttpApiManager.invokeApi(RECOMMEND_URL, query.toJSON());
		RecommendResults results = RecommendResults.fromJSON(result);
		return results;
	}

	public static Integer formatType(Integer resourceType) {
		if (resourceType == null) {
			return Constant.ITEM_TYPE_LIST;
		}
		switch (resourceType) {
		case 1:
			return Constant.ITEM_TYPE_DOC;
		case 2:
			return Constant.ITEM_TYPE_URL;
		case 3:
			return Constant.ITEM_TYPE_VIDEO_UR;
		case 4:
			return Constant.ITEM_TYPE_TEXT;
		case 6:
			return Constant.ITEM_TYPE_GROUP;
		default:
			return Constant.ITEM_TYPE_UNKOWN;
		}
	}

	public static long toRecommendId(Integer shareId, Integer shareType) {
		return RecommendObject.toID(shareId, formatType(shareType));
	}

	static List<Long> toLongs(List<Integer> ids, Integer recType) {
		List<Long> longs = new ArrayList<Long>();
		if (ids != null) {
			for (Integer id : ids) {
				long lid = RecommendObject.toID(id, recType);
				longs.add(lid);
			}
		}
		return longs;
	}

	public static void main(String[] args) throws JSONException {
	}

}
