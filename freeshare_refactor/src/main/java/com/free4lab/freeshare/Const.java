package com.free4lab.freeshare;

import java.io.IOException;
import java.util.Properties;

/**
 * 判断是否处于调试开发状态
 * @author zhaowei
 *
 */
public class Const {
	private static boolean isDev = false;
	static{
		Properties p = new Properties();
		try {
			p.load(Const.class.getClassLoader().getResourceAsStream("freeshare.properties"));
			String value = p.getProperty("isDev");
			if(value != null){
				isDev = Boolean.parseBoolean(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isDev() {
		return isDev;
	}

}
