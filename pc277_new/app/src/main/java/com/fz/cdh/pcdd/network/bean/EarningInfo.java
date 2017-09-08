package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/4/14.
 */

public class EarningInfo {

    public int id;
    public int user_id;
    public double point;
    public double fenxiao_point;
    public int status;
    public double zuhe_point;
    public int point_num;
    public double get_point;
    public double xhibit_point;
    public Object fenxiao_user_id;
    public long create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getFenxiao_point() {
        return fenxiao_point;
    }

    public void setFenxiao_point(double fenxiao_point) {
        this.fenxiao_point = fenxiao_point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getZuhe_point() {
        return zuhe_point;
    }

    public void setZuhe_point(double zuhe_point) {
        this.zuhe_point = zuhe_point;
    }

    public int getPoint_num() {
        return point_num;
    }

    public void setPoint_num(int point_num) {
        this.point_num = point_num;
    }

    public double getGet_point() {
        return get_point;
    }

    public void setGet_point(double get_point) {
        this.get_point = get_point;
    }

    public double getXhibit_point() {
        return xhibit_point;
    }

    public void setXhibit_point(double xhibit_point) {
        this.xhibit_point = xhibit_point;
    }

    public Object getFenxiao_user_id() {
        return fenxiao_user_id;
    }

    public void setFenxiao_user_id(Object fenxiao_user_id) {
        this.fenxiao_user_id = fenxiao_user_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
