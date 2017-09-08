package com.fz.cdh.pcdd.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.ValueSet;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.BindBankRequest;
import com.fz.cdh.pcdd.ui.adapter.DialogListAdapter;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.DialogManager;
import com.fz.cdh.pcdd.util.MD5Utils;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 2017/1/23.
 * 绑定银行卡
 */

public class BindBankActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edRealName;
    private TextView edBankName;
    private EditText edBankNo;
    private EditText edBankAddr;
    private EditText edWithdrawPwd;
    private Dialog bankDialog;
    private List<ValueSet> bankList = new ArrayList<>();

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank);
        init();
    }

    /**
     * 中国农业银行
     中国建设银行
     中国工商银行
     中国银行
     交通银行
     中国邮政储蓄银行
     招商银行
     兴业银行
     中信银行
     中国光大银行
     上海浦东发展银行
     中国民生银行
     深圳发展银行
     民生银行
     广东发展银行
     华夏银行
     */
    private void init() {
        initTopBar("绑定银行卡");
        bankList.add(new ValueSet("0", "中国农业银行"));
        bankList.add(new ValueSet("1", "中国建设银行"));
        bankList.add(new ValueSet("3", "中国工商银行"));
        bankList.add(new ValueSet("4", "中国银行"));
        bankList.add(new ValueSet("5", "交通银行"));
        bankList.add(new ValueSet("6", "中国邮政储蓄银行"));
        bankList.add(new ValueSet("7", "招商银行"));
        bankList.add(new ValueSet("8", "兴业银行"));
        bankList.add(new ValueSet("9", "中信银行"));
        bankList.add(new ValueSet("10", "中国光大银行"));
        bankList.add(new ValueSet("11", "上海浦东发展银行"));
        bankList.add(new ValueSet("12", "中国民生银行"));
        bankList.add(new ValueSet("13", "广东发展银行"));
        bankList.add(new ValueSet("14", "华夏银行"));
        edRealName = getView(R.id.edRealName);
        edBankName = getView(R.id.edBankName);
        edBankNo = getView(R.id.edBankNo);
        edBankAddr = getView(R.id.edBankAddr);
        edWithdrawPwd = getView(R.id.edWithdrawPwd);
        userInfo = UserInfoManager.getUserInfo(this);
        edRealName.setText(userInfo.real_name);
        edBankName.setText(userInfo.bank_name);
        edBankNo.setText(userInfo.bank_no);
        edBankAddr.setText(userInfo.open_card_address);
        getView(R.id.btnOK).setOnClickListener(this);
        edBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankDialog = DialogManager.showListDialog(BindBankActivity.this,edBankName, "选择银行", Gravity.CENTER,0,bankList, new DialogManager.OnCancleClickListener() {
                    @Override
                    public void onClick() {
                        bankDialog.dismiss();
                    }
                }, new DialogListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, RecyclerView.Adapter adapter) {
                        ValueSet chooseBean = null;
                        for (int i = 0; i < bankList.size(); i++) {
                            if (position == i) {
                                bankList.get(i).setChoosed(true);
                                chooseBean = bankList.get(i);
                            } else {
                                bankList.get(i).setChoosed(false);
                            }
                        }
                            if (!chooseBean.getTypeCode().isEmpty()) {
                                edBankName.setText(chooseBean.getTypeName());
                            }
                        bankDialog.dismiss();
                    }
                });

            }
        });

    }






    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if(ViewUtil.checkEditEmpty(edRealName, "请填写真实姓名"))
                    return;
                if(edBankName.getText().toString().isEmpty()){
                    T.showLong("请填写银行");
                    return;
                }
                if(ViewUtil.checkEditEmpty(edBankNo, "请填写真实姓名"))
                    return;
                if(ViewUtil.checkEditEmpty(edBankAddr, "请填写真实姓名"))
                    return;
                if(ViewUtil.checkEditEmpty(edWithdrawPwd, "请填写真实姓名"))
                    return;
                bind();
                break;
        }
    }

    public void bind() {
        String pwd = edWithdrawPwd.getText().toString();
        for (int i=0; i<3; i++)
            pwd = MD5Utils.getMD5String(pwd);
        final BindBankRequest req = new BindBankRequest();
        req.withdrawals_password = pwd;
        req.real_name = edRealName.getText().toString();
        req.bank_name = edBankName.getText().toString();
        req.bank_no = edBankNo.getText().toString();
        req.open_card_address = edBankAddr.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("绑定成功");
                userInfo.real_name = req.real_name;
                userInfo.bank_name = req.bank_name;
                userInfo.bank_no = req.bank_no;
                userInfo.open_card_address = req.open_card_address;
                UserInfoManager.saveUserInfo(BindBankActivity.this, userInfo);
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
        MySubcriber s = new MySubcriber(this, callback, true, "绑定中");
        ApiInterface.bindBank(req, s);
    }
}
