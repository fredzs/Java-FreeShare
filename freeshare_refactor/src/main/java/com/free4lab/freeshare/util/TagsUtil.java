package com.free4lab.freeshare.util;

import java.util.*;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class TagsUtil {
	private static TagsUtil instance;

	private TagsUtil() {
	}

	public synchronized static TagsUtil getInstance() {
		if (instance == null) {
			instance = new TagsUtil();
		}
		return instance;
	}

	public List<String> getTags(ResourceWrapper adapter) {
		List<String> tagValues = new ArrayList<String>();
		tagValues = TagsUtil.getTagValue(adapter.getType(),
				BehaviorConst.TYPE_CREATE, adapter.getAuthorId(),
				adapter.getGroupIds());
		return tagValues;
	}

	public static List<String> getTagValue(Integer resourceType,
			Integer optype, Integer uid, List<Integer> groupIds) {
		List<String> tagValues = new ArrayList<String>();
		if (optype == 0) {
			tagValues.add(TagValuesConst.pAUTHOR + uid);
		}
		// tagValues.add(getOpTypeTagValue(optype));
		tagValues.addAll(getResourceTagValue(resourceType, groupIds));
		return tagValues;
	}

	private static List<String> getResourceTagValue(Integer type,
			List<Integer> groupIds) {
		List<String> tagValues = new ArrayList<String>();
		tagValues.add(TagValuesConst.FMT_RESOURCE);
		tagValues.add(getResourceTypeTagValue(type));
		if (groupIds != null) {
			for (Integer groupId : groupIds) {
				tagValues.add(TagValuesConst.pGROUP + groupId);
			}
		}
		return tagValues;
	}

	private static String getResourceTypeTagValue(Integer type) {
		String tagvalue;
		switch (type) {
		case -1:
			tagvalue = TagValuesConst.pRESOURCE_LIST;
			break;
		case 0:
			tagvalue = TagValuesConst.pRESOURCE_TOPIC;
			break;
		case 1:
			tagvalue = TagValuesConst.pRESOURCE_DOC;
			break;
		case 2:
			tagvalue = TagValuesConst.pRESOURCE_URL;
			break;
		case 3:
			tagvalue = TagValuesConst.pRESOURCE_VIDEO;
			break;
		case 4:
			tagvalue = TagValuesConst.pRESOURCE_TEXT;
			break;
		default:
			tagvalue = "";
			break;
		}
		return tagvalue;
	}

	static List<String> getLabels(List<String> labels) {
		if (labels == null) {
			return new ArrayList<String>();
		}
		for (int i = 0; i < labels.size(); i++) {
			labels.set(i, "LABEL:" + labels.get(i));
		}
		return labels;
	}

	@SuppressWarnings("unused")
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
