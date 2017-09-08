package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/2/25.
 */

public class GameRecordRequest extends BaseRequest {
    public String page_no;
    public String page_size;
    public String game_type;
    public String start_time;
    public String end_time;
    public String room_id;  //对应的房间id  可选
}
