package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.request.ResetLoginPwdRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.widget.CountDownButton;
import com.fz.cdh.pcdd.util.MD5Utils;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

/**
 * Created by hang on 2017/3/5.
 * 找回密码
 */

public class ResetLoginPwdActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edAccount;
    private EditText edMobile;
    private EditText edVCode;
    private CountDownButton btnVCode;
    private EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_login_pwd);
        init();
    }

    private void init() {
        initTopBar("找回密码");
        edAccount = getView(R.id.edMobile);
        edMobile = getView(R.id.edMobile);
        edVCode = getView(R.id.edVCode);
        btnVCode = getView(R.id.btnVCode);
        edPassword = getView(R.id.edPassword);

        btnVCode.setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnVCode.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnVCode.onStop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnVCode:
                if (ViewUtil.checkEditEmpty(edMobile, "请输入手机号"))
                    return;
                btnVCode.getVCode(edMobile.getText().toString(), null);
                break;

            case R.id.btnOK:
                if(ViewUtil.checkEditEmpty(edAccount, "请输入用户名"))
                    return;
                if(ViewUtil.checkEditEmpty(edMobile, "请输入手机号"))
                    return;
                if(ViewUtil.checkEditEmpty(edVCode, "请输入验证码"))
                    return;
                if(ViewUtil.checkEditEmpty(edPassword, "请输入新密码"))
                    return;
                submit();
                break;
        }
    }

    public void submit() {
        String pwd = edPassword.getText().toString();
        pwd = MD5Utils.getNTimesMd5(pwd, 3);

        ResetLoginPwdRequest req = new ResetLoginPwdRequest();
        req.account = edAccount.getText().toString();
        req.mobile = edMobile.getText().toString();
        req.password = pwd;
        req.msg_id = btnVCode.getVCodeId();
        req.msg_code = edVCode.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("设置成功");
                finish();
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "设置中");
        ApiInterface.resetLoginPwd(req, s);
    }
}
