package com.free4lab.freeshare.manager;
import com.free4lab.freeshare.manager.factory.IScoreManager;
import com.free4lab.freeshare.model.dao.UserScoreDAO;

public class ItemScoreManager implements IScoreManager{
	private UserScoreDAO usd = new UserScoreDAO();
	public boolean addPublish(Integer id, Integer uid) {
		if(usd.create(uid, id, "item", 50.0f) == null)
			return false;
		else
			return true;
	}
	
	public boolean addUpDown(Integer id, Integer uid, String type) {
		if(type.equals("up")){
			usd.update(uid, id, "item", 1);
		}else
			usd.update(uid, id, "item", 2);
		return true;
	}
	
	public boolean addBrowse(Integer id, Integer uid) {
		usd.update(uid, id, "item", 3);
		return true;
	}


	public boolean addReply(Integer id, Integer uid) {
		usd.update(uid, id, "item", 4);
		return true;
	}

}
