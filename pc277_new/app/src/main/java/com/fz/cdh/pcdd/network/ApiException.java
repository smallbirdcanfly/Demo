package com.fz.cdh.pcdd.network;

/**
 * Created by hang on 2016/12/1.
 */
public class ApiException extends RuntimeException {

    private int code;
    private String msg;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
