package com.free4lab.freeshare.interceptor;

public enum ReadWritePermissionEnum {
	
	AUTHOR_PERMISSION(1),
	WRITABLE_PERMISSION(2),
	READABLE_PERMISSION(3),
	AVOID_PERMISSION(4);
	
	private final Integer value;
	private ReadWritePermissionEnum(Integer value){
		this.value = value;
	}
	public Integer getValue(){
		return value;
	}
}
