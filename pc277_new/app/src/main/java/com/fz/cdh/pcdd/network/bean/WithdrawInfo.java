package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/2/27.
 */

public class WithdrawInfo {
    public double point;
    public int free_count;          //剩余免费次数
    public int tixian_free_count;   //每天提现免费次数
    public double tixian_min_fee;   //提现最低额度
    public double tixian_bili;      //提现比例 0.2 表示 20%
}
