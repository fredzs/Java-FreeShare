package com.free4lab.freeshare.manager.factory;

import com.free4lab.freeshare.manager.ItemScoreManager;
import com.free4lab.freeshare.manager.ListScoreManager;

public class ScoreManagerFactory {
	public static IScoreManager getScoreManager(Integer type){
		if(type == null || type != -1){
			return getItemScoreManager();
		}
		return getListScoreManager();
	}
	public static ListScoreManager getListScoreManager(){
		return new ListScoreManager();
	}
	public static ItemScoreManager getItemScoreManager(){
		return new ItemScoreManager();
	}
}
