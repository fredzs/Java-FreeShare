package com.free4lab.freeshare;

import java.io.IOException;
import java.util.Properties;

public class URLConst {

	public static final String APIPrefix_FreeSearch;
	public static final String APIPrefix_FreeAccount;
	public static final String APIPrefix_FreeTag;
	public static final String APIPrefix_FreeMessage;
	public static final String APIPrefix_FreeFront;
	public static final String APIPrefix_Recommend;
	public static final String APIPrefix_FreeDisk;
	static{
		try {
			Properties p = new Properties();
			p.load(ResourceTypeConst.class.getClassLoader().getResourceAsStream("url.properties"));
			APIPrefix_FreeSearch = p.getProperty("APIPrefix_FreeSearch");
			APIPrefix_FreeAccount = p.getProperty("APIPrefix_FreeAccount");
			APIPrefix_FreeTag = p.getProperty("APIPrefix_FreeTag");
			APIPrefix_FreeMessage = p.getProperty("APIPrefix_FreeMessage");
			APIPrefix_FreeFront = p.getProperty("APIPrefix_FreeFront");
			APIPrefix_Recommend = p.getProperty("APIPrefix_Recommend");
			APIPrefix_FreeDisk = p.getProperty("APIPrefix_FreeDisk");
		} catch (IOException e) {
			 throw new RuntimeException("Failed to init app configuration", e);
		}
	}
}
