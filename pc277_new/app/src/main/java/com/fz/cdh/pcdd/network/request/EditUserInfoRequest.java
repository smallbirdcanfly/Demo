package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/2/27.
 */

public class EditUserInfoRequest extends BaseRequest {
    public String user_photo;
    public String nick_name;
    public String sex;      //1男 2女
    public String mobile;
    public String msg_id;
    public String msg_code;
    public String withdrawals_password; //提现密码 明文
    public String old_password;     //原登录密码/原提现密码（首次添加提现密码，不需要填）md5加密
    public String password;         //登录密码  明文
}
