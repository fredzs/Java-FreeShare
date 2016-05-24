package com.free4lab.freeshare.model.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Membership {
	
	private static final Logger logger = Logger.getLogger(Membership.class);
	
	private Group group;
	private List<Integer> uidList;
	
	public Membership(Group g, List<Integer> uidList){
    	this.group = new Group(g.getUuid(),g.getAuthToken());
    	this.uidList = uidList;
    }
	
	public Membership(String membershipJSON){
		if(membershipJSON != null){
			try {
				JSONObject json = new JSONObject(membershipJSON);
				group = (Group) json.get("group");
				uidList = new ArrayList<Integer>();
				JSONArray array = json.getJSONArray("members");
				for(int i=0;i<array.length();i++){
					uidList.add(Integer.parseInt(array.getString(i)));
				}
			} catch (JSONException e) {
				logger.error("Invalid String membershipJSON!" + membershipJSON);
			}
		}
	}
	
	
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Integer> getUidList() {
		return uidList;
	}

	public void setUidList(List<Integer> uidList) {
		this.uidList = uidList;
	}
}
