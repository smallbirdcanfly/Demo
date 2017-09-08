package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.CheckUtil;

/**
 * Created by hang on 2017/1/23.
 * 钱包
 */

public class WalletActivity extends BaseTopActivity implements View.OnClickListener {

    private TextView tvMyPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    public void init() {
        initTopBar("钱包");
        tvMyPoint = getView(R.id.tvMyPoint);

        getView(R.id.llRechargeRecord).setOnClickListener(this);
        getView(R.id.llWithdrawRecord).setOnClickListener(this);
        getView(R.id.llBankCards).setOnClickListener(this);
        getView(R.id.llRecharge).setOnClickListener(this);
        getView(R.id.llWithdraw).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.llRechargeRecord:
                startActivity(new Intent(this, RechargeRecordActivity.class));
                break;

            case R.id.llWithdrawRecord:
                startActivity(new Intent(this, WithdrawRecordActivity.class));
                break;

            case R.id.llBankCards:
                startActivity(new Intent(this, BindBankActivity.class));
                break;

            case R.id.llRecharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;

            case R.id.llWithdraw:
                if(CheckUtil.checkBind(this))
                    startActivity(new Intent(this, WithdrawActivity.class));
                break;
        }
    }

    public void loadUserInfo() {
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserInfo userInfo) {
                tvMyPoint.setText(userInfo.point+"元宝");
                UserInfoManager.saveUserInfo(WalletActivity.this, userInfo);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, false, "");
        ApiInterface.getUserInfo(new BaseRequest(), s);
    }
}
