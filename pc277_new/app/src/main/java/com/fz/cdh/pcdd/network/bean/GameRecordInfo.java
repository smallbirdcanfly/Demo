package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/1/23.
 */

public class GameRecordInfo {
    public int id;
    public long choice_no;  //期数
    public String choice_result;
    public String choice_name;      //投注类型
    public int bili_id;
    public double bili;
    public double point;               //投注金额
    public double get_point;           //中奖金额
    public String real_result;
    public String result_type;
    public long create_time;        //下注时间
    public int game_type;           //1北京快8   2加拿大
    public int is_zhong;            //是否中奖 -1待开奖 1中奖 0未中
}
