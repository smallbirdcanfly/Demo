package com.fz.cdh.pcdd.entity;

public class MessageEvent {
	
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_REFRESH = 0x104;
	public static final int TYPE_SHARE = 0x105;

	public int type = TYPE_DEFAULT;
	public int code;
	public Object obj;
	
	public MessageEvent() {}
	
	public MessageEvent(int type, int code, Object obj) {
		this.type = type;
		this.code = code;
		this.obj = obj;
	}
}
