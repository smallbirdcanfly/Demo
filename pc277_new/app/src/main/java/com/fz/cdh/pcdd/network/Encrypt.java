package com.fz.cdh.pcdd.network;

import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.util.DateUtil;
import com.fz.cdh.pcdd.util.MD5Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hang on 2017/2/5.
 */

public class Encrypt {
    public static <T extends BaseRequest>void encrypt(T req) {
        req.user_id = UserInfoManager.getUserId(PcddApp.applicationContext)+"";
        req.timestamp = DateUtil.getCurrDateTime();

        StringBuilder sb = new StringBuilder(PcddApp.APP_KEY);
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clz = req.getClass();
        //按顺序拼接参数，父类的在前
        for(; clz!=Object.class; clz=clz.getSuperclass()) {
            Field[] fields = clz.getDeclaredFields();
            fieldList.addAll(0, Arrays.asList(fields));
        }
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(com.fz.cdh.pcdd.annotation.Encrypt.class)) {
                try {
                    String value = (String) field.get(req);
                    sb.append(",").append(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        String sign = sb.toString();

        try {
            sign = MD5Utils.BASE64Encode(MD5Utils.aesEncrypt(sign, PcddApp.APP_SECRET));
            sign = sign.replaceAll("\r\n", "");
            sign = MD5Utils.getMD5String(sign);
            sign = MD5Utils.getMD5String(sign);
            req.sign = sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
