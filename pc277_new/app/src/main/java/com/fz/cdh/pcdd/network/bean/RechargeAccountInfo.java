package com.fz.cdh.pcdd.network.bean;

import java.io.Serializable;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeAccountInfo implements Serializable {
    public int id;
    public String account;  //银行卡或者支付宝帐号
    public String real_name;
    public String bank_name;
    public String open_card_address;
}
