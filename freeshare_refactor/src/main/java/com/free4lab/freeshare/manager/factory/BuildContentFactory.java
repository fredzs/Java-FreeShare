package com.free4lab.freeshare.manager.factory;

public class BuildContentFactory {
	final String SEARCH_CONTENT = "search";
	final String RECOMMEND_CONTENT = "recommend";
	String buildContent;
	
	public BuildContentFactory(String buildContent) {
		this.buildContent = buildContent;
	}

	public Context produce() {
		if (buildContent.equalsIgnoreCase(SEARCH_CONTENT)) {
			return new Context(
					new com.free4lab.freeshare.manager.search.BuildContent());
		} else {
			return new Context(
					new com.free4lab.freeshare.action.recommend.BuildContent());
		}
	}
}
