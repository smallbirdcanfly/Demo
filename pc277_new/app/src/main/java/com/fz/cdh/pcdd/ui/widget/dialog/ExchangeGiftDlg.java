package com.fz.cdh.pcdd.ui.widget.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.GiftInfo;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.ExchangeGiftRequest;
import com.fz.cdh.pcdd.util.Arith;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2017/1/25.
 */

public class ExchangeGiftDlg extends CommonDialog implements View.OnClickListener, TextWatcher {

    private EditText edExchangeCount;
    private EditText edAddr;
    private TextView tvNeedPoint;

    private GiftInfo giftInfo;

    public ExchangeGiftDlg(Context context, GiftInfo giftInfo) {
        super(context, R.layout.dlg_exchange_gift, 250, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.giftInfo = giftInfo;
    }

    @Override
    public void initDlgView() {
        edExchangeCount = getView(R.id.edExchangeCount);
        edAddr = getView(R.id.edReceiveAddr);
        tvNeedPoint = getView(R.id.tvNeedPoint);

        UserInfo userInfo = UserInfoManager.getUserInfo(context);
        setText(R.id.tvGiftPoint, giftInfo.gift_point+"币");
        setText(R.id.tvRealName, userInfo.real_name);
        setText(R.id.tvMobile, userInfo.mobile);

        getView(R.id.btnClose).setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
        getView(R.id.btnCancel).setOnClickListener(this);
        edExchangeCount.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnClose:
            case R.id.btnCancel:
                dismiss();
                break;

            case R.id.btnOK:
                if(TextUtils.isEmpty(edExchangeCount.getText().toString())) {
                    T.showShort("请输入兑换数");
                    return;
                }
                if(TextUtils.isEmpty(edAddr.getText().toString())) {
                    T.showShort("请输入收货地址");
                    return;
                }
                exchange();
                break;
        }
    }

    public void exchange() {
        ExchangeGiftRequest req = new ExchangeGiftRequest();
        req.gift_id = giftInfo.id+"";
        req.gift_count = edExchangeCount.getText().toString();
        req.address = edAddr.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("兑换成功，等待客服审核");
                dismiss();
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(context, callback, true, "兑换中");
        ApiInterface.exchangeGift(req, s);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = edExchangeCount.getText().toString();
        if(!TextUtils.isEmpty(str)) {
            int count = Integer.parseInt(str);
            tvNeedPoint.setText(Arith.mul(count, giftInfo.gift_point)+"");
        }
    }
}
