package com.free4lab.freeshare.util;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtil {

	public static Timestamp getCurrentTime(){
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		return currentTime;
	}
	
	public static void main(String[] args){
		System.out.println("first"+TimeUtil.getCurrentTime());
		int j = 0;
		for(int i = 0; i < 10000000;i++){
			j = j*3;
			String oneString = new String("hello");
			oneString += "one";
		}
		System.out.println("second"+TimeUtil.getCurrentTime());
	}
}
