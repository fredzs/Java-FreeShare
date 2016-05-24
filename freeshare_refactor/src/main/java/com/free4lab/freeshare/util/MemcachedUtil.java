package com.free4lab.freeshare.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * 负责memcache交互
 * 暂时未使用，被徐老师毙了
 * @author zhaowei
 * 
 */
public class MemcachedUtil {
	private static boolean isInit = false;
	private static String[] servers;
	private static MemCachedClient client = null;

	private static MemcachedUtil instance = null;

	private MemcachedUtil() {
	}

	public synchronized static MemcachedUtil getInstance() {
		if (instance == null) {
			instance = new MemcachedUtil();
			instance.init();
			if (!isInit()) {
				return null;
			}
		}
		return instance;
	}

	public void add(String key, Object value) {
		if (isInit()) {
			client.add(key, value);
		}
	}
	/**
	 * 带有过期时间的添加k-v
	 * @param key
	 * @param value
	 * @param date
	 */
	public void add(String key, Object value,long time) {
		if (isInit()) {
			Date date = new Date(time);
			client.add(key, value, date);
		}
	}
	/**
	 * 根据某个key获取
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		if (isInit()) {
			return client.get(key);
		}
		return null;
	}
	
	public Map<String,Object> gets(String[] keys) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (isInit()) {
			for(String key : keys){
				map.put(key, get(key));
			}
			return map;
		}
		return null;
	}
	public void delete(String key){
		
	}
	public void init() {
		Properties p = new Properties();
		try {
			p.load(MemcachedUtil.class.getClassLoader().getResourceAsStream(
					"memcached.properties"));
			String v = p.getProperty("mem_serves");
			JSONArray array = new JSONArray(v);
			servers = new String[array.length()];
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = new JSONObject(array.getString(i));
				String server = "";
				server = server.concat(json.getString("ip")).concat(":")
						.concat(json.getString("port"));
				servers[i] = server;
			}
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers(servers);
			pool.initialize();
			client = new MemCachedClient();
			setInit(true);
		} catch (Exception e) {
			setInit(false);
			e.printStackTrace();
			return;
		}
	}

	private static boolean isInit() {
		return isInit;
	}

	private void setInit(boolean isInit) {
		MemcachedUtil.isInit = isInit;
	}

	public static void main(String[] args) {
		MemcachedUtil instance = MemcachedUtil.getInstance();
		if (instance != null) {
			instance.add("hello", "my_test");
			System.out.println("in memcache : " + instance.get("hello"));
		}
	}
}
