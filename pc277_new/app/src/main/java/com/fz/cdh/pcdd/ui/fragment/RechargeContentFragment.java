package com.fz.cdh.pcdd.ui.fragment;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.AliAcountListActivity;
import com.fz.cdh.pcdd.ui.BankAccountListActivity;
import com.fz.cdh.pcdd.ui.RechargeLogActivity;
import com.fz.cdh.pcdd.ui.RechargeOnlineFirstepActivity;
import com.fz.cdh.pcdd.ui.RechargeOnlineFirstepActivity_new;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.util.CheckUtil;
import com.fz.cdh.pcdd.util.ClickTimeSpanUtil;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.weight.RechangeDialog;

/**
 * Created by hang on 2017/1/23.
 */

public class RechargeContentFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private int payType = -1; //0 mo宝  1 爱益支付宝  2 多宝支付宝  3 多宝微信 4爱益微信
    private RechangeDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge_content;
    }

    @Override
    protected void init(View rootView) {
        if (!ClickTimeSpanUtil.isFastDoubleClick(1000)) {
         if (dialog == null) {
            dialog = new RechangeDialog(getActivity());
        }
        dialog.show();
        }
        getView(R.id.btnRecharge).setOnClickListener(this);
        ((RadioGroup) getView(R.id.rgPayType)).setOnCheckedChangeListener(this);
        getView(R.id.tvOfflineBank).setOnClickListener(this);
        getView(R.id.tvOfflineAli).setOnClickListener(this);
        getView(R.id.btnRechargeLog).setOnClickListener(this);
        getView(R.id.btnCusSvr).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id. btnRecharge:
                if(payType == -1) {
                    T.showShort("请选择支付方式");
                    return;
                }
                Intent it = new Intent(activity, RechargeOnlineFirstepActivity_new.class);
                it.putExtra("type", payType);
                startActivity(it);
                break;

            case R.id.tvOfflineAli:
                startActivity(new Intent(activity, AliAcountListActivity.class));
                break;

            case R.id.tvOfflineBank:
                startActivity(new Intent(activity, BankAccountListActivity.class));
                break;

            case R.id.btnRechargeLog:
                startActivity(new Intent(activity, RechargeLogActivity.class));
                break;

            case R.id.btnCusSvr:
                CheckUtil.startCusSvr(activity);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId) {
            case R.id.rbMoPay:
                payType = 0;
                break;

            case R.id.rbIyiAli:
               payType = 1;
              //  payType = 5;
                break;

            case R.id.rbIyiWx:
               payType = 3;
              //  payType = 6;
                break;

            case R.id.rbAliPay_scan:
                payType = 2;
              //  payType = 7;
                break;

            case R.id.rb_qq_scan:
                payType = 4;
             //   payType = 8;
                break;
            case R.id.rb_9:
                //  payType = 4;
                payType = 9;
                break;
        }
    }
}
