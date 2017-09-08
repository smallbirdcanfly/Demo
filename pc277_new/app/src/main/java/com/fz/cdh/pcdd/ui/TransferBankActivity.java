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
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.RechargeAccountInfo;
import com.fz.cdh.pcdd.network.request.RechargeOfflineRequest;
import com.fz.cdh.pcdd.ui.adapter.DialogListAdapter;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.DialogManager;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.Utility;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 2017/2/28.
 * 线下银行账号转账
 */

public class TransferBankActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edBankName;
    private EditText edRealName;
    private EditText edBankAccount;
    private EditText edTransferFee;
    private TextView edTransferWay;


    private RechargeAccountInfo data;
    private Dialog bankDialog;
    private List<ValueSet> bankList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_bank);
        init();
    }

    public void init() {
        data = (RechargeAccountInfo) getIntent().getSerializableExtra("data");

        initTopBar("填写存款信息");
        edBankName = getView(R.id.edBankName);
        edRealName = getView(R.id.edRealName);
        edBankAccount = getView(R.id.edBankAccount);
        edTransferFee = getView(R.id.edTransferFee);
        edTransferWay = getView(R.id.edTransferWay);

        setText(R.id.tvBankName, "银行："+data.bank_name);
        setText(R.id.tvRealName, "收款人："+data.real_name);
        setText(R.id.tvBankAccount, "账号："+data.account);
        setText(R.id.tvBranchBank, "开户行："+data.open_card_address);

        getView(R.id.btnCopyName).setOnClickListener(this);
        getView(R.id.btnCopyAccount).setOnClickListener(this);
        getView(R.id.btnCopyBranch).setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
        getView(R.id.edTransferWay).setOnClickListener(this);
        bankList.add(new ValueSet("0", "网银转账"));
        bankList.add(new ValueSet("1", "银行柜台"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCopyName:
                Utility.copy(this, data.real_name);
                T.showShort("复制成功");
                break;

            case R.id.btnCopyAccount:
                Utility.copy(this, data.account);
                T.showShort("复制成功");
                break;

            case R.id.btnCopyBranch:
                Utility.copy(this, data.open_card_address);
                T.showShort("复制成功");
                break;
            case R.id.edTransferWay:
                bankDialog = DialogManager.showListDialog(TransferBankActivity.this,edTransferWay ,"选择方式", Gravity.CENTER,0, bankList, new DialogManager.OnCancleClickListener() {
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
                            edTransferWay.setText(chooseBean.getTypeName());
                        }
                        bankDialog.dismiss();
                    }
                });

                break;



            case R.id.btnOK:
                if(ViewUtil.checkEditEmpty(edBankName, "请填写所属银行"))
                    return;
                if(ViewUtil.checkEditEmpty(edRealName, "请填写户名"))
                    return;
                if(ViewUtil.checkEditEmpty(edBankAccount, "请填写银行号后4位"))
                    return;
                if(edBankAccount.getText().toString().length() != 4) {
                    T.showShort("请填写银行号后4位");
                    return;
                }
                if(ViewUtil.checkEditEmpty(edTransferFee, "请填写汇款金额"))
                    return;
                submit();
                break;
        }
    }

    public void submit() {
        RechargeOfflineRequest req = new RechargeOfflineRequest();
        req.account_id = data.id+"";
        req.account = edBankAccount.getText().toString();
        req.account_type = "1";
        req.real_name = edRealName.getText().toString();
        req.bank_name = edBankName.getText().toString();
        req.point = edTransferFee.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("提交成功，请等待客服审核");
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
        ApiInterface.rechargeOffline(req, s);
    }
}
