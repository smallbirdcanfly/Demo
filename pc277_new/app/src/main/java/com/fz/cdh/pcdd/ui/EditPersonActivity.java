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
import com.fz.cdh.pcdd.network.request.ModifyUserInfoRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

/**
 * Created by hang on 2017/1/22.
 * 修改资料
 */

public class EditPersonActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edNickName;
    private EditText edPersonalSign;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        init();
    }

    public void init() {
        userInfo = UserInfoManager.getUserInfo(this);

        initTopBar("修改资料");
        edNickName = getView(R.id.edNickName);
        edPersonalSign = getView(R.id.edPersonalSign);

        ViewUtil.setEditWithClearButton(edNickName, R.drawable.btn_close_gray);
        ViewUtil.setEditWithClearButton(edPersonalSign, R.drawable.btn_close_gray);

        setText(R.id.tvAccount, userInfo.account);
        edNickName.setText(userInfo.nick_name);
        edPersonalSign.setText(userInfo.personal_sign);

        getView(R.id.btnOK).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if(TextUtils.isEmpty(edNickName.getText().toString()) && TextUtils.isEmpty(edPersonalSign.getText().toString())) {
                    T.showShort("请填写昵称");
                    return;
                }
                submit(edNickName.getText().toString(), edPersonalSign.getText().toString());
                break;
        }
    }

    public void submit(final String nickname, final String personalSign) {
        ModifyUserInfoRequest req = new ModifyUserInfoRequest();
        req.nick_name = nickname;
        req.personal_sign = personalSign;
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("设置成功");
                userInfo.nick_name = nickname;
                userInfo.personal_sign = personalSign;
                UserInfoManager.saveUserInfo(EditPersonActivity.this, userInfo);
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
        MySubcriber s = new MySubcriber(this, callback, true, "提交中");
        ApiInterface.modifyUserInfo(req, s);
    }
}
