package com.free4lab.freeshare.manager.factory;

import com.free4lab.freeshare.manager.ResourceScoreManager;
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
	public static ResourceScoreManager getItemScoreManager(){
		return new ResourceScoreManager();
	}
}
