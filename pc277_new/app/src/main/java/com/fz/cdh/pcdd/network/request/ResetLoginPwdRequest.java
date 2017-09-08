package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/3/5.
 */

public class ResetLoginPwdRequest extends BaseRequest {
    public String account;
    public String mobile;
    public String password;
    public String msg_id;
    public String msg_code;
}
