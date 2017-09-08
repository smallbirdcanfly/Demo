package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/2/21.
 */

public class CancelOrderRequest extends BaseRequest {
    public String choice_id;//用户撤销哪一期的数据id
    public String choice_no;//选择的期数
    public String game_type;//游戏类型，1：北京28。2：加拿大28
}
