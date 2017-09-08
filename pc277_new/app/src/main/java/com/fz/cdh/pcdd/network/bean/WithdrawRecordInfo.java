package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/1/23.
 */

public class WithdrawRecordInfo {
    public int id;
    public int user_id;
    public double fee;      //提现金额
    public double real_fee; //实际到账金额
    public String account;
    public int status;      //0提现中 1成功 2失败
    public int source;      //1支付宝 2微信 3银行卡
    public long create_time;//提现时间
    public String real_name;
    public String mobile;
}
