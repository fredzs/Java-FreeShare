package com.free4lab.freeshare.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.free4lab.freeshare.manager.HttpApiManager;
import com.free4lab.freeshare.model.data.DraftObject;

public class DraftsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7632381356367569329L;
	private static final Logger logger = Logger.getLogger(DraftsAction.class);
	private List<DraftObject> doList;
	public String execute(){
		String result = getDrafts(getSessionUID());
		if(result != null){
			logger.info("result is " + result);
			try {
				doList = new ArrayList<DraftObject>();
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					DraftObject draftObject = new DraftObject(array.get(i).toString());
					doList.add(draftObject);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		return INPUT;
	}
	public static void main(String[] args) {
		String result = getDrafts(70);
		logger.info("result is " + result);
		try {
			DraftsAction da = new DraftsAction();
			da.doList = new ArrayList<DraftObject>();
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				DraftObject draftObject = new DraftObject(array.get(i).toString());
				da.doList.add(draftObject);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getDrafts(Integer userId) {
		String result = "";
		String url = "http://localhost:9090/";
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> l = new ArrayList<String>();
		l.add(userId.toString());
		params.put("userId", l);
		result = HttpApiManager.invokeApi(url, params);
		return result;
	}

	public List<DraftObject> getDoList() {
		return doList;
	}

	public void setDoList(List<DraftObject> doList) {
		this.doList = doList;
	}
}
