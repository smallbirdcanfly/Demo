package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/1/23.
 * 充值记录
 */

public class RechargeRecordInfo {
    public double total_fee;    //充值金额
    public long create_time; //充值时间
    public long update_time;
    public int pay_type;
    public int status;      //0待确认  1成功
    public double result_fee;
}
