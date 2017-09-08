package com.fz.cdh.pcdd.entity;

/**
 * Created by hang on 2017/2/21.
 */

public class BettingJson {
    public int notice_type; //1进出房间消息 2下注 3开奖信息 4封盘信息 5开盘
    public long game_count;
    public String game_type;
    public int point;
    public String im_account;
    public String nick_name;
    public String user_photo;
    public String ext_content;
    public long create_time;
    public int level;
}
