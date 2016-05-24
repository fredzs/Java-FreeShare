package com.free4lab.freeshare.rebuild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.manager.search.SearchManager;


public class Clear {
	public static void main(String[] args) throws IOException{
		new NewIndexManager().delIndex("list?id=null");
	}
	public static boolean clearTag(String url, String tag){
		Map<String, List<String>> params = new HashMap<String, List<String>>();
//		List<String> urlValue = new ArrayList<String>();// 地址
//		urlValue.add(url);
//		params.put("url", urlValue);
//		List<String> tagValue = new ArrayList<String>();
//		tagValue.add(tag);
//		params.put("tag", tagValue);
			
		return true;
	}
	public static boolean clearUrl(String url, List<String> tags) {
		HashMap<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> urlValue = new ArrayList<String>();
		urlValue.add(url);
		params.put("url", urlValue);
		if (null != tags) {
			params.put("tag", tags);
		}
		return true;
	}
}
