package com.fz.cdh.pcdd.network.bean;

/**
 * Created by hang on 2017/1/23.
 * 回水记录
 */

public class BackwaterInfo {
    public int id;
    public int type;
    public int user_id;
    public double point;
    public double bili;
    public double hui_shui_point;
    public int status;      //0待处理 1处理 2未满足
    public long create_time;
    public double point_num;
    public double zuhe_point;
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeDouble(bili);
//    }
//
//    public static final Parcelable.Creator<BackwaterInfo> CREATOR = new Creator<BackwaterInfo>() {
//        @Override
//        public BackwaterInfo createFromParcel(Parcel source) {
//            BackwaterInfo info = new BackwaterInfo();
//            info.id = source.readInt();
//            info.bili = source.readDouble();
//            return info;
//        }
//
//        @Override
//        public BackwaterInfo[] newArray(int size) {
//            return new BackwaterInfo[size];
//        }
//    };
}
