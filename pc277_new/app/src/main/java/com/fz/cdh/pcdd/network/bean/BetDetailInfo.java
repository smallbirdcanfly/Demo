package com.fz.cdh.pcdd.network.bean;

import java.util.List;

/**
 * Created by hang on 2017/2/23.
 */

public class BetDetailInfo {

    public List<OpenTime> open_time;
    public double point;   //余额
    public long seconds; //倒计时 单位秒
    public long game_num;
    public int status;  //1正常 2封盘 3停售
    public BetResultInfo first_result;  //开奖记录第一条
    public double per_min_point;
    public double per_max_point;
    public long all_max_point;

    public class OpenTime {
        public int id;
        public int user_id;
        public int game_type;
        public long open_time;      //开奖时间
        public long game_num;       //游戏期数
        public String game_result;  //游戏结果
        public String game_result_desc;    //游戏结果（式子）
        public String result_type;
        public int color;           //1红 2绿 3蓝 4无
    }
}
