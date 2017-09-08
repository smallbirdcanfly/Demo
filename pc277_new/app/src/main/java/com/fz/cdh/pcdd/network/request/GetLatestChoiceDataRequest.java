package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/2/21.
 */

public class GetLatestChoiceDataRequest extends BaseRequest {
    public String area_id;
    public String game_type;    //1北京快乐8 2加拿大快乐8
    public String room_id;//房间号
    public String choice_no;//当前下注期数
}
