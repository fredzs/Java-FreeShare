package com.free4lab.freeshare.handler;

import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * 启动发布操作的处理链，请按照 {@link ResourceWrapper} 构造函数构造adapter
 * @author zhaowei
 *
 */
public class HandlerBootstrap {
	private static HandlerBootstrap instance = null;
	private HandlerBootstrap(){}
	public synchronized static HandlerBootstrap getInstance(){
		if(instance == null){
			instance = new HandlerBootstrap();
		}
		return instance;
	}
	
	public void handler(ResourceWrapper wrapper){
		Chain chain = HandlerInit.init();
		new InnerHandler().next(wrapper, chain);
	}
	
	class InnerHandler extends ChainHandler{
		public void process(ResourceWrapper wrapper, Chain chain) {
			//DO NOTHING
		}
	}
}
