package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/3/4.
 */

public class RechargeLogRequest extends BaseRequest {
    public String page_no;
    public String page_size;
    public String account_type; //（选填）1银行卡 2支付宝
}
