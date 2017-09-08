package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/2/7.
 */

public class ModifyUserInfoRequest extends BaseRequest {
    public String user_photo;
    public String nick_name;
    public String personal_sign;
    public String sex;  //1男 2女
    public String mobile;
    public String msg_id;
    public String msg_code;
}
