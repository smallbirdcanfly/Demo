package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeRequest extends BaseRequest {
    @Encrypt
    public String total_fee;
    public String pay_type;
}
