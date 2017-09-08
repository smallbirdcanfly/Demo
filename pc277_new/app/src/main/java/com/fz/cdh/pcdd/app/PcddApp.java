package com.fz.cdh.pcdd.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.fz.cdh.pcdd.manager.IMManager;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.Iterator;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by hang on 2017/1/16.
 */

public class PcddApp extends Application {

    private static final String PKG_NAME = "com.fz.cdh.wa28";

   /* public static final String APP_KEY = "fzpcdd";

    public static final String APP_SECRET = "fzInBa#oy@W@1J@M";*/
   public static final String APP_KEY = "pcdd";

    public static final String APP_SECRET = "OjInBa#oy@W@1J@M";
    public static Context applicationContext;
    public static int screenWidth;//屏幕宽度
    public static int screenHeight;//屏幕高度

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        screenWidth = ViewUtil.getScreenWidth(this);
        screenHeight = ViewUtil.getScreenHeight(this);
        T.init(this);
        initIM();
        ShareSDK.initSDK(this);
    }

    private void initIM() {
        String processAppName = getAppName(android.os.Process.myPid());
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
        if (processAppName != null && processAppName.equalsIgnoreCase(PKG_NAME)) {
            IMManager.getInstance().init(this);
        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
