package com.fz.cdh.pcdd.network.request;

/**
 * Created by hang on 2017/4/2.
 */

public class VersionRequest extends BaseRequest {
    public String client_no;    //版本号
    public String client_type = "1";  //1安卓  2ios
}
