package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.EditUserInfoRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.MD5Utils;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2017/2/27.
 * 设置提现密码
 */

public class EditWithdrawPwdActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edOldPwd;
    private EditText edNewPwd1;
    private EditText edNewPwd2;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_withdraw_pwd);
        init();
    }

    private void init() {
        initTopBar("修改提现密码");
        edOldPwd = getView(R.id.edOldPwd);
        edNewPwd1 = getView(R.id.edNewPwd1);
        edNewPwd2 = getView(R.id.edNewPwd2);

        userInfo = UserInfoManager.getUserInfo(this);
        if(TextUtils.isEmpty(userInfo.withdrawals_password))
            getView(R.id.llOldPwd).setVisibility(View.GONE);


        getView(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if(!TextUtils.isEmpty(userInfo.withdrawals_password)) {
                    if(TextUtils.isEmpty(edOldPwd.getText().toString()) || edOldPwd.getText().toString().length()<6) {
                        T.showShort("请输入6~12位原密码");
                        return;
                    }
                }
                if(TextUtils.isEmpty(edNewPwd1.getText().toString()) || edNewPwd1.getText().toString().length()<6) {
                    T.showShort("请输入6~12位新密码");
                    return;
                }
                if(!edNewPwd1.getText().toString().equals(edNewPwd2.getText().toString())) {
                    T.showShort("确认密码不一致");
                    return;
                }
                submit();
                break;
        }
    }

    public void submit() {
        final EditUserInfoRequest req = new EditUserInfoRequest();
        if(!TextUtils.isEmpty(userInfo.withdrawals_password)) {
            String oldPwd = edOldPwd.getText().toString();
            for(int i=0; i<3; i++)
                oldPwd = MD5Utils.getMD5String(oldPwd);
            req.old_password = oldPwd;
        }
        req.withdrawals_password = edNewPwd1.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("设置成功");
                userInfo.withdrawals_password = req.withdrawals_password;
                UserInfoManager.saveUserInfo(EditWithdrawPwdActivity.this, userInfo);
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
        ApiInterface.editUserInfo(req, s);
    }
}
