package com.free4lab.freeshare.manager.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.manager.factory.BuildContentFactory;
import com.free4lab.freeshare.manager.factory.IndexManagerFactory;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.utils.http.SearchClient;

/**
 * 实现了定义着各种针对索引操作的接口： {@link IndexManagerFactory} <br/>
 * TODO 把构造Content的行为抽离出来
 * @author zhaowei
 */
public class NewIndexManager implements IndexManagerFactory {
	private Logger logger = Logger.getLogger(NewIndexManager.class);

	private final SearchClient client = new SearchClient(URLConst.APIPrefix_FreeSearch);
	private final static String NEED_INDEX_DESCRIPTION = "description";
	private final static String RESOURCE_URL = "resource?id=";
	//private static ConcurrentLinkedQueue<IndexRunnable> clq = new ConcurrentLinkedQueue<IndexRunnable>();
	private static NewIndexManager instance;

	public synchronized static NewIndexManager getInstance() {
		if (instance == null) {
			instance = new NewIndexManager();
		}
		return instance;
	}

	/**
	 * @param url
	 *            该资源访问的相对url
	 * @param title
	 *            资源题目
	 * @param content
	 *            资源的主要信息。也是从search搜索获取数据后的主要解析对象 <br/>
	 *            解析负责类:{@link Doc4Srh} <br/>
	 * @param tags
	 *            要添加的标签
	 */
	public boolean addIndex(ResourceWrapper adapter) {
		logger.info("创建资源索引。");
		String content = new BuildContentFactory("search").produce().buildContent(adapter);
		String url = indexUrl(adapter.getId());
		addIndex(url, adapter.getName(), content, adapter.getTags());
		logger.info("创建资源索引成功。");
		return true;
	}

	/**
	 * @param url
	 *            该资源访问的相对url
	 * @param title
	 *            资源题目
	 * @param content
	 *            资源的主要信息,从search获取数据后的主要解析对象 <br/>
	 *            解析负责类:{@link Doc4Srh} <br/>
	 * @param tags
	 *            要添加的标签
	 */
	public void addIndex(String url, String title, String content,
			List<String> tags) {
		try {
			IndexRunnable in = new IndexRunnable(url, title, content, tags);
			Thread t = new Thread(in);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean delIndex(String url) {
		logger.info("delIndex url: " + url);
		try {
			client.delDoc(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean addTags(String url, List<String> tags) {
		logger.info("addtags url: " + url);
		try {
			client.addTags(url, tags);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean removeTags(String url, List<String> tags) {
		logger.info("removetags url: " + url);
		try {
			client.delTags(url, tags);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 得到某索引上某个标签的得分
	 * 
	 * @param url
	 *            索引唯一标识
	 * @param tag
	 *            标签
	 * @return 标签的得分
	 */
	public long getTagValue(String url, String tag) {
		long value = -1;
		try {
			value = client.getTagValue(url, tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	/**
	 * 设置某索引上某个标签的得分
	 * 
	 * @param url
	 *            索引唯一标识
	 * @param tag
	 *            标签
	 * @param value
	 *            得分
	 * @return 标签的得分
	 */
	public void setTagValue(String url, String tag, long value) {
		try {
			client.setTagValue(url, tag, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Map中的key修改而且仅修改该url所标识的索引，对其他字段值没有影响
	 * 
	 * @param url
	 *            索引唯一标识
	 * @param Map
	 * @throws Exception
	 */
	public void changeDocContent(String url, Map<String, Object> m)
			throws Exception {
		client.changeDocContent(url, m);
	}

	public String indexUrl(Integer id){
		return RESOURCE_URL + id;
	}
	
	class IndexRunnable implements Runnable {
		private String url;
		private String title;
		private String content;
		private List<String> tags;

		public IndexRunnable(String url, String title, String content,
				List<String> tags) {
			this.url = url;
			this.title = title;
			this.content = content;
			this.tags = tags;
		}

		public void run() {
			try {
				List<String> l = new ArrayList<String>();
				l.add(NEED_INDEX_DESCRIPTION);
				client.addDoc(url, title, content, tags, l, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
