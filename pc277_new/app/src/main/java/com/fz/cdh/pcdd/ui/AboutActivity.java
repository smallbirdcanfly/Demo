package com.fz.cdh.pcdd.ui;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.KefuInfo;
import com.fz.cdh.pcdd.network.bean.ShareParamsInfo;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.DialogManager;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.Utility;

import java.io.File;

/**
 * Created by hang on 2017/3/4.
 */

public class AboutActivity extends BaseTopActivity implements View.OnClickListener {

    private String website = "";
    private String qq = "";
    private String wechat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
        loadKefuInfo();
        loadShareParams();
    }

    private void init() {
        initTopBar("关于");

        try {
            setText(R.id.tvVersion, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getView(R.id.btnCopyWebSite).setOnClickListener(this);
        getView(R.id.btnCopyQQ).setOnClickListener(this);
        getView(R.id.btnCopyWechat).setOnClickListener(this);
    }

    public void loadKefuInfo() {
        HttpResultCallback<KefuInfo> callback = new HttpResultCallback<KefuInfo>() {
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
            public void onNext(KefuInfo kefuInfo) {
                website = kefuInfo.kefu_guanwang;
                qq = kefuInfo.kefu_qq;
                wechat = kefuInfo.kefu_weixin;

                ((TextView) getView(R.id.textView7)).setText(website);
                //textView8
                ((TextView) getView(R.id.textView8)).setText(qq);
                ((TextView) getView(R.id.textView9)).setText(wechat);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "加载数据");
        ApiInterface.getKefuInfo(new BaseRequest(), s);
    }

    public void loadShareParams() {
        HttpResultCallback<ShareParamsInfo> callback = new HttpResultCallback<ShareParamsInfo>() {
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
            public void onNext(ShareParamsInfo data) {
                if(!TextUtils.isEmpty(data.share_url)) {
                    setupSharePanel(data.share_url);
                }
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getShareParams(new BaseRequest(), s);
    }

    public void setupSharePanel(String url) {
        final String savePath = Environment.getExternalStorageDirectory() + File.separator + "share_url.png";
        ((ViewStub)getView(R.id.stubShare)).inflate();
        final ImageView ivQrCode = getView(R.id.ivQrCode);
        ivQrCode.setImageBitmap(BitmapTool.createQRCodeBitmap(url));
        getView(R.id.ivSaveQrCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap=((BitmapDrawable)ivQrCode.getDrawable()).getBitmap();
                DialogManager.showChoosePicDialog(AboutActivity.this,bitmap, savePath);
//                if(BitmapTool.saveBitmap2Local(AboutActivity.this,
//                        ((BitmapDrawable)ivQrCode.getDrawable()).getBitmap(), savePath)) {
//                    T.showShort("二维码已保存至"+savePath);
//                } else {
//                    T.showLong("二维码保存失败，请手动截图");
//                }
            }
        });
        ivQrCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap=((BitmapDrawable)ivQrCode.getDrawable()).getBitmap();
                DialogManager.showChoosePicDialog(AboutActivity.this,bitmap, savePath);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCopyWebSite:
                Utility.copy(this, website);
                T.showShort("已复制");
                break;

            case R.id.btnCopyQQ:
                Utility.copy(this, qq);
                T.showShort("已复制");
                break;

            case R.id.btnCopyWechat:
                Utility.copy(this, wechat);
                T.showShort("已复制");
                break;
        }
    }
}
