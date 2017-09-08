package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/23.
 */

public class BettingRequest extends BaseRequest {
    public String room_id;
    public String area_id;
    @Encrypt
    public String choice_no;    //下注期数
    @Encrypt
    public String point;        //下注金额
    public String bili_id;      //选择的比例id
}
