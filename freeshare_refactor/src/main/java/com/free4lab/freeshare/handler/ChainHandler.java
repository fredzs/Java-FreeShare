package com.free4lab.freeshare.handler;

import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * @author zhaowei
 *
 */
public abstract class ChainHandler {
	public abstract void process(ResourceWrapper adapter, Chain chain);
	
	public void next(ResourceWrapper adapter, Chain chain){
		try {
			if(Chain.getChainList().size() > 0){
				String clz = Chain.getChainList().removeFirst();
				ChainHandler hc = (ChainHandler) Class.forName(clz).newInstance();
				hc.process(adapter, chain);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
