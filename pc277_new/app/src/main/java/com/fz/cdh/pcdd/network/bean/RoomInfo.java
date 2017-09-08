package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/2/7.
 */

public class RoomInfo {
    public int id;
    public int area_id;
    public String im_gourp_id;   //环信 群组id
    public String room_name;
    public String room_photo;
    public int people_count;     //总人数
    public int people_max_count; //人数上限
    public double per_max_point;   //个人下注上限
    public double per_min_point;   //个人下注下限
    public long all_max_point;   //房间下注总额上限
}
