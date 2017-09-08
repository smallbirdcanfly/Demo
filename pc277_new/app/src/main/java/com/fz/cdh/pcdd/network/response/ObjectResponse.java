package com.fz.cdh.pcdd.network.response;

/**
 * Created by hang on 2016/12/1.
 */
public class ObjectResponse<T> {
    public String request_id;
    public int result_code;
    public String result_desc;
    public String timestamp;

    public T data;
}
