package com.pvo.prototype;


public abstract class PVOService<T> {

	public abstract T executeService(String... params) throws Exception;
	
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}
}
