package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/3/4.
 * 充值转账记录
 */

public class RechargeLogInfo {
    public int id;
    public String account;
    public int account_type;    //1银行卡 2支付宝
    public String real_name;
    public String bank_name;
    public double point;
    public int status;          //0待确认 1确认收到  2未收到
    public long create_time;
    public RechargeAccountInfo account_info;
}
