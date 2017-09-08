package com.fz.cdh.pcdd.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.math.BigDecimal;

/**
 * Description:
 * Date: 2015-10-22  17:46
 * User: wushan
 */
public class CommonUtil {

    /**
     * 获取设备密度
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyBoard(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param ctx
     * @param view
     */
    public static void closeKeyBoard(Context ctx, View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 123000转1.23万,6位有效数字
     */
    public static String TenthousandToYuan(String wan) {
        if (TextUtils.isEmpty(wan)||Double.parseDouble(wan)==0) {
            return "-";
        }
        Double wanNum = Double.parseDouble(wan);

        BigDecimal bd = new BigDecimal((double) wanNum / 10000 * 100 / 100 + "");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

        String price= bd.toString();
        if (price.length()>=6){
           price = price.substring(0,5);
        }
        return price;
    }

    /**
     * 判断网络是否可用
     */
    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager con = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = con.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            // 当前网络不可用
            return false;
        }
        return true;
    }
}
