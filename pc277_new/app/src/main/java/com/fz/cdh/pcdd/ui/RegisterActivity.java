package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.LoginRequest;
import com.fz.cdh.pcdd.network.request.RegisterRequest;
import com.fz.cdh.pcdd.ui.base.BaseFragmentActivity;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.ui.fragment.WebLotteryFragment;
import com.fz.cdh.pcdd.util.CheckUtil;
import com.fz.cdh.pcdd.util.MD5Utils;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

/**
 * Created by hang on 2017/2/6.
 */

public class RegisterActivity extends BaseFragmentActivity implements View.OnClickListener {

    private EditText edAccount;
    private EditText edPassword1;
    private EditText edPassword2;
    private EditText edRecommCode;
    private CheckBox cbAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        edAccount = getView(R.id.edAccount);
        edPassword1 = getView(R.id.edPassword1);
        edPassword2 = getView(R.id.edPassword2);
        edRecommCode = getView(R.id.edRecommCode);
        cbAgreement = getView(R.id.cbRegistAgreement);

        ViewUtil.setEditWithClearButton(edAccount, R.drawable.btn_close_gray);
        ViewUtil.setEditWithClearButton(edPassword1, R.drawable.btn_close_gray);
        ViewUtil.setEditWithClearButton(edPassword2, R.drawable.btn_close_gray);

        getView(R.id.btnClose).setOnClickListener(this);
        getView(R.id.btnRegister).setOnClickListener(this);
        getView(R.id.tvCusSvr).setOnClickListener(this);
        getView(R.id.tvUserAgreement).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClose:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btnRegister:
                if(!cbAgreement.isChecked()) {
                    T.showShort("请同意勾选注册协议");
                    return;
                }
                if(TextUtils.isEmpty(edAccount.getText().toString()) || edAccount.getText().toString().length()<6) {
                    T.showShort("密码请输入6~12位字母或数字");
                    return;
                }
                if(TextUtils.isEmpty(edPassword1.getText().toString()) || edPassword1.getText().toString().length()<6) {
                    T.showShort("密码请输入6~12位字母或数字");
                    return;
                }
                if(TextUtils.isEmpty(edPassword2.getText().toString()) || !edPassword2.getText().toString().equals(edPassword1.getText().toString())) {
                    T.showShort("两次密码输入不一致");
                    return;
                }
                register();
                break;

            case R.id.tvCusSvr:
                CheckUtil.startCusSvr(this);
                break;

            case R.id.tvUserAgreement:
                Intent agreement = new Intent(this, WebLoadActivity.class);
                agreement.putExtra(WebLoadFragment.PARAMS_TITLE, "注册协议");
                agreement.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_USER_AGREEMENT);
                startActivity(agreement);
                break;
        }
    }

    public void register() {
        RegisterRequest req = new RegisterRequest();
        req.account = edAccount.getText().toString();
        req.password = edPassword1.getText().toString();
        if(!TextUtils.isEmpty(edRecommCode.getText().toString()))
            req.code = edRecommCode.getText().toString();
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("注册成功");
                finish();

            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(UserInfo o) {
                requestLogin();
                Intent it = new Intent(RegisterActivity.this, WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, "幸运大转盘");
                it.putExtra("loadType", "lottery");
                it.putExtra("userId",o.getId()+"");
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_LOTTERY_RULE);
                startActivity(it);
                finish();
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "注册中");
        ApiInterface.register(req, s);
    }
    private void requestLogin() {
        LoginRequest req = new LoginRequest();
        req.account = edAccount.getText().toString();
        String pwd = edPassword1.getText().toString();
        for(int i=0; i<3; i++)
            pwd = MD5Utils.getMD5String(pwd);
        req.password = pwd;
        req.registration_id = "1";
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
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
            public void onNext(UserInfo userInfo) {
                loginSucceed(userInfo);
            }
        };
        MySubcriber<UserInfo> s = new MySubcriber<UserInfo>(this, callback, true, "登录中");
        ApiInterface.login(req, s);
    }
    public void loginSucceed(UserInfo userInfo) {
        UserInfoManager.setUserName(RegisterActivity.this, edAccount.getText().toString());
        UserInfoManager.setUserPwd(RegisterActivity.this, edPassword1.getText().toString());
        UserInfoManager.saveUserInfo(RegisterActivity.this, userInfo);
        UserInfoManager.setLoginStatus(RegisterActivity.this, true);
      //  loginIM(userInfo);

    }
}
