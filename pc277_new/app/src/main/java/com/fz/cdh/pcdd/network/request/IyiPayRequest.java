package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/3/18.
 */

public class IyiPayRequest extends BaseRequest {
    @Encrypt
    public String order_no;
    public String fee;
}
