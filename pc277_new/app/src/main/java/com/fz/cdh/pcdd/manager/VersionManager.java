package com.fz.cdh.pcdd.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.VersionInfo;
import com.fz.cdh.pcdd.network.request.VersionRequest;
import com.fz.cdh.pcdd.ui.RechargeOnlineFirstepActivity_new;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.util.ApkUtil;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2017/4/2.
 */

public class VersionManager {

    public static void checkVersion(final Context context) {
        try {
            VersionRequest req = new VersionRequest();
            req.client_no = ApkUtil.getVersionCode(context)+"";
            req.client_type="1";
            HttpResultCallback<VersionInfo> callback = new HttpResultCallback<VersionInfo>() {
                @Override
                public void onStart() {
                }

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    T.showShort(e.getMessage());
                }

                @Override
                public void onNext(VersionInfo versionInfo) {
                   showTipDlg(context, versionInfo);
                }
            };
            MySubcriber s = new MySubcriber(callback);
            ApiInterface.checkVersion(req, s);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
     static AlertDialog updataDialog;
    private static  boolean isClick=false;
    public static void showTipDlg(final Context context, final VersionInfo versionInfo) {
        isClick=false;
        try {
            if(ApkUtil.getVersionCode(context).equals(versionInfo.version_no)){
                return;
            }
        if(versionInfo.status == 1) {
            //强制升级
            T.showShort("发现新版本，此版本为强制升级");
            upgrade(context, versionInfo.version_url);
        } else {
            // 建议升级
            if(null!=updataDialog){
                updataDialog.dismiss();
                updataDialog=null;
            }
             updataDialog=   new AlertDialog .Builder(context).setTitle("发现新版本："+versionInfo.version_no)
                    .setMessage(versionInfo.update_content)
                    .setPositiveButton("取消", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isClick=true;
                            if(versionInfo.status == 1){
                                System.exit(0);
                            }
                        }
                    }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(versionInfo.status == 1&&!isClick){
                        System.exit(0);
                    }
                }
            })
                    .setNegativeButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isClick=true;
                            upgrade(context, versionInfo.version_url);
                        }
                    }).show();
        }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void upgrade(Context context, String url) {
        /*Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        it.setData(uri);
        context.startActivity(Intent.createChooser(it, "请选择浏览器"));*/
        Intent it2 = new Intent(context, WebLoadActivity.class);
        it2.putExtra(WebLoadFragment.PARAMS_TITLE, "官网");
        it2.putExtra(WebLoadFragment.PARAMS_URL, url);
        it2.putExtra("loadType","update");
        context.startActivity(it2);
    }
}
