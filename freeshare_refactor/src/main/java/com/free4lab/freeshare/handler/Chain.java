package com.free4lab.freeshare.handler;

import java.util.LinkedList;

/**
 * @author zhaowei
 *
 */
public class Chain {
	private static ThreadLocal<Chain> local = new ThreadLocal<Chain>();
	private LinkedList<String> chainList;
	private boolean next;

	public Chain(LinkedList<String> chainList, boolean next) {
		this.chainList = chainList;
		this.next = next;
	}

	public static void set(Chain chain) {
		local.set(chain);
	}

	public static LinkedList<String> getChainList() {
		Chain chain = local.get();
		return chain.getLocalChainList();
	}

	private LinkedList<String> getLocalChainList() {
		return chainList;
	}

	public static boolean isNext() {
		return local.get().isLocaNext();
	}

	private boolean isLocaNext() {
		return next;
	}

}
