package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/3/4.
 * 充值转账记录
 */

public class RechargeResponseInfo {
    public String state;
    public String msg;
    public String img;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
