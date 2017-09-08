package com.fz.cdh.pcdd.util;


public class TransferTempDataUtil<T> {

	private static TransferTempDataUtil<?> instance;
	
	private T data;
	
	private TransferTempDataUtil() {
		
	}
	
	public static synchronized <T> TransferTempDataUtil<T> getInstance() {
		if(instance == null)
			instance = new TransferTempDataUtil();
		return (TransferTempDataUtil<T>) instance;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public void recycle() {
		data = null;
	}
}
