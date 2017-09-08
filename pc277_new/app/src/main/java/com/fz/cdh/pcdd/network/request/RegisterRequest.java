package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/6.
 */

public class RegisterRequest extends BaseRequest {
    public String account;
    public String code; //邀请码

    @Encrypt
    public String password;
}
