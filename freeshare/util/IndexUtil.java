package com.free4lab.freeshare.util;

import java.util.*;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;

public class IndexUtil {
	private static IndexUtil instance;
	private IndexUtil(){}
	public synchronized static IndexUtil getInstance(){
		if(instance == null){
			instance = new IndexUtil();
		}
		return instance;
	}
	public List<String> getTags(ObjectAdapter adapter){
		List<String> tagValues = new ArrayList<String>();
		tagValues = IndexUtil.getTagValue(adapter.getType(), BehaviorConst.TYPE_CREATE, adapter.getAuthorId(), adapter.getGroupIds());
		tagValues.addAll(getLabels(adapter.getLables()));
		return tagValues;
	}
	public static List<String> getTagValue(Integer resourceType, Integer optype, Integer uid,
			List<Integer> groupIds) {
		List<String> tagValues = new ArrayList<String>();
		if(optype == 0){
			tagValues.add(TagValuesConst.pAUTHOR + uid);
		}
		tagValues.add(getOpTypeTagValue(optype));
		if(resourceType == -1){
			tagValues.addAll(getListTagValue(groupIds));
		}else
			tagValues.addAll(getItemTagValue(resourceType, groupIds));
		
		return tagValues;
	}
	static List<String> getLabels(List<String> labels){
		if(labels == null){
			return new ArrayList<String>();
		}
		for(int i = 0; i < labels.size(); i ++){
			labels.set(i, "LABEL:" + labels.get(i));
		}
		return labels;
	}
	private static List<String> getListTagValue(List<Integer> groupIds) {
		List<String> tagValues = new ArrayList<String>();
		tagValues.add(TagValuesConst.FMT_LIST);
		if (groupIds != null) {
			for (Integer groupId : groupIds) {
				tagValues.add(TagValuesConst.pGROUP + groupId); 
				tagValues.add(TagValuesConst.pGROUP_LIST + groupId);
			}
		}
		return tagValues;
	}

	private static List<String> getItemTagValue(Integer type, List<Integer> groupIds) {
		List<String> tagValues = new ArrayList<String>();
		tagValues.add(TagValuesConst.FMT_ITEM);
		tagValues.add(getItemTypeTagValue(type));
		// TODO 如果要添加的tag已经存在，则search的操作为 ??
		if (groupIds != null) {
			for (Integer groupId : groupIds) {
				tagValues.add(TagValuesConst.pGROUP + groupId); 
				tagValues.add(TagValuesConst.pGROUP_ITEM + groupId);
			}
		}
		return tagValues;
	}

	private static String getItemTypeTagValue(Integer type) {
		String tagvalue;
		switch (type) {
		case 0:
			tagvalue = TagValuesConst.pITEM_TOPIC;
			break;
		case 1:
			tagvalue = TagValuesConst.pITEM_DOC;
			break;
		case 2:
			tagvalue = TagValuesConst.pITEM_URL;
			break;
		case 3:
			tagvalue = TagValuesConst.pITEM_VIDEO;
			break;
		case 4:
			tagvalue = TagValuesConst.pITEM_TEXT;
			break;
		default:
			tagvalue = "";
			break;
		}
		return tagvalue;
	}

	private static String getOpTypeTagValue(Integer optype) {
		String tagvalue;
		switch (optype) {
		case 0:
			tagvalue = TagValuesConst.pCREATE;
			break;
		case 1:
			tagvalue = TagValuesConst.pEDIT;
			break;
		case 2:
			tagvalue = TagValuesConst.pOUTLIST;
			break;
		case 3:
			tagvalue = TagValuesConst.pINLIST;
			break;
		case 5:
			tagvalue = TagValuesConst.pNEWVERSION;
			break;
		default:
			tagvalue = "";
			break;
		}
		return tagvalue;
	}
	
}
