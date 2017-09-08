package com.fz.cdh.pcdd.network.request;

import com.fz.cdh.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/5.
 */

public class BaseRequest {
    @Encrypt
    public String timestamp;//时间戳 格式：yyyy-MM-dd HH:mm:ss
    @Encrypt
    public String user_id;  //用户id 如果没有 用0
    public String sign;     //签名 先aes256 加密  然后转base64    最后md5 2次
}
