package com.fz.cdh.pcdd.entity;

/**
 * Created by hang on 2017/3/8.
 */

public class QAChatMsg {
    public int type;    //1 接受  2 发送
    public String content;
    public long timestamp;

    public QAChatMsg(int type, String content, long timestamp) {
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
    }
}
