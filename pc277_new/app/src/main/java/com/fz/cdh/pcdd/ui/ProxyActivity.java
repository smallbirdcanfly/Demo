package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.ProxyRuleInfo;
import com.fz.cdh.pcdd.network.bean.ShareParamsInfo;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.fragment.ProxyOpenAccountFragment;
import com.fz.cdh.pcdd.ui.fragment.ProxyShareFragment;
import com.fz.cdh.pcdd.ui.widget.dialog.ShareMenuWindow;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2017/3/29.
 * 分享代理
 */

public class ProxyActivity extends BaseTopActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioButton rbProxy1;
    private RadioButton rbProxy2;

    private ProxyRuleInfo ruleInfo;
    private ProxyOpenAccountFragment proxyOpenAccountFragment;
    private ProxyShareFragment proxyShareFragment;

    private ShareMenuWindow shareWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        init();
        loadData();
    }

    public void init() {
        initTopBar("VIP分享");
//        btnTopRight2.setBackgroundResource(R.drawable.wode_share);
//        btnTopRight2.setVisibility(View.VISIBLE);
//        btnTopRight2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(shareWindow == null) {
//                    loadShareParams();
//                } else {
//                    shareWindow.showAtBottom();
//                }
//            }
//        });

        rbProxy1 = getView(R.id.rbProxy1);
        rbProxy2 = getView(R.id.rbProxy2);

        rbProxy1.setEnabled(false);
        rbProxy2.setEnabled(false);

        ((RadioGroup)getView(R.id.rgProxy)).setOnCheckedChangeListener(this);

    }

    public void loadData() {
        HttpResultCallback<ProxyRuleInfo> callback = new HttpResultCallback<ProxyRuleInfo>() {
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
            public void onNext(ProxyRuleInfo proxyRuleInfo) {
                ruleInfo = proxyRuleInfo;

                proxyOpenAccountFragment = new ProxyOpenAccountFragment();
                proxyOpenAccountFragment.leastTime = proxyRuleInfo.num;
                proxyOpenAccountFragment.list = proxyRuleInfo.bili_list;

                proxyShareFragment = new ProxyShareFragment();
                proxyShareFragment.shareUrl = proxyRuleInfo.share_url;
                String from = getIntent().getStringExtra("from");
                if(!TextUtils.isEmpty(from)){
                    getView(R.id.rbProxy2).performClick();
                }else {
                    switchFragment(proxyOpenAccountFragment);
                }
                rbProxy1.setEnabled(true);
                rbProxy2.setEnabled(true);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "加载数据");
        ApiInterface.getProxyRule(new BaseRequest(), s);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbProxy1:
                switchFragment(proxyOpenAccountFragment);
                break;

            case R.id.rbProxy2:
                switchFragment(proxyShareFragment);
                break;
        }
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.flContent, fragment).commit();
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
                shareWindow = new ShareMenuWindow(ProxyActivity.this);
                shareWindow.title = data.share_title;
                shareWindow.content = data.share_content;
                shareWindow.url = data.share_url;
                shareWindow.showAtBottom();
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "");
        ApiInterface.getShareParams(new BaseRequest(), s);
    }
}
