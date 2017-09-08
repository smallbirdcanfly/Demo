package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeOfflineRequest extends BaseRequest {
    public String account_id;
    public String account;
    public String account_type; //1银行卡 2支付宝
    public String real_name;
    public String bank_name;
    @Encrypt
    public String point;        //转账金额
}
