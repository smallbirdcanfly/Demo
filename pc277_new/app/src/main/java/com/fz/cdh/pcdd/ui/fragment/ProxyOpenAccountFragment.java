package com.fz.cdh.pcdd.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.CommissionInfo;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.RegisterRequest;
import com.fz.cdh.pcdd.ui.adapter.CommissionRuleAdapter;
import com.fz.cdh.pcdd.ui.adapter.manager.FullyLinearLayoutManager;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.List;

/**
 * Created by hang on 2017/3/29.
 */

public class ProxyOpenAccountFragment extends BaseFragment implements View.OnClickListener {

    private EditText edAccount;
    private EditText edPassword1;
    private EditText edPassword2;
    private RecyclerView rvData;

    public int leastTime;
    public List<CommissionInfo> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_proxy_open_account;
    }

    @Override
    protected void init(View rootView) {
        edAccount = getView(R.id.edAccount);
        edPassword1 = getView(R.id.edPassword1);
        edPassword2 = getView(R.id.edPassword2);
        rvData = getView(R.id.rvData);

        ViewUtil.setEditWithClearButton(edAccount, R.drawable.btn_close_gray);
        ViewUtil.setEditWithClearButton(edPassword1, R.drawable.btn_close_gray);
        ViewUtil.setEditWithClearButton(edPassword2, R.drawable.btn_close_gray);

        rvData.setLayoutManager(new FullyLinearLayoutManager(activity));
        rvData.setAdapter(new CommissionRuleAdapter(activity, list));
        setText(R.id.tvProxyTip, getString(R.string.rule_vip_share, leastTime));

        getView(R.id.btnRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
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
        }
    }

    public void register() {
        RegisterRequest req = new RegisterRequest();
        req.account = edAccount.getText().toString();
        req.password = edPassword1.getText().toString();
        req.code = UserInfoManager.getUserId(activity)+"";
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("开户成功");
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(UserInfo o) {
            }
        };
        MySubcriber s = new MySubcriber(activity, callback, true, "开户中");
        ApiInterface.register(req, s);
    }
}
