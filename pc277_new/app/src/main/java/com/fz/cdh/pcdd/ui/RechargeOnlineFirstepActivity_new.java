package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.alipay.AliPayHelper;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.OkHttpManager;
import com.fz.cdh.pcdd.network.bean.OrderInfo;
import com.fz.cdh.pcdd.network.bean.RechargeResponseInfo;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.network.request.DuobaoPayRequest;
import com.fz.cdh.pcdd.network.request.RechargeRequest;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.util.DigestUtil;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by hang on 2017/1/25.
 * 线上充值1
 */

public class RechargeOnlineFirstepActivity_new extends BaseTopActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, AliPayHelper.PayCompleteCallback {

    private static final int REQ_UNION_PAY = 0x100;

    private EditText edRechargeFee;
    private TextView tvPoint;
    private TextView tvTips;

    private int payType; //0 mo宝  1 爱益支付宝  2 多宝支付宝  3 多宝微信  4爱益微信
   // private String[] titles = {"微信", "支付宝"};
    private String[] titles = {"微信", "支付宝","QQ"};
    private int titleIndex; //0 微信 1 支付宝

    private AliPayHelper aliPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_online_firstep);
        init();
        initPayHelper();
    }

    private void init() {
        payType = getIntent().getIntExtra("type", 0);
        switch (payType) {
            case 1:

                titleIndex = 1;
                break;

            case 2:
                titleIndex = 0;
                break;
        }
        initTopBar("充值");

        edRechargeFee = getView(R.id.edRechargeFee);
        tvPoint = getView(R.id.tvUserPoint);
        tvTips = getView(R.id.tvRechargeTips);

        getView(R.id.btnNext).setOnClickListener(this);
        getView(R.id.btnRefreshPoint).setOnClickListener(this);
        ((RadioGroup) getView(R.id.rgPayType)).setOnCheckedChangeListener(this);

        RadioButton[] radioButton = new RadioButton[2];
        radioButton[0] = getView(R.id.rbWxPay);
        radioButton[1] = getView(R.id.rbAliPay);
        radioButton[titleIndex].setChecked(true);

        UserInfo userInfo = UserInfoManager.getUserInfo(this);
        setText(R.id.tvUserAccount, userInfo.account);
        tvPoint.setText(userInfo.point+"");

        if(payType == 0) {
            getView(R.id.llPayType).setVisibility(View.GONE);
        }
        setTipsText();
    }

    public void initPayHelper() {
        aliPay = new AliPayHelper(this);
        aliPay.setPayCallback(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId) {
            case R.id.rbWxPay:
              /*  if(payType == 1)
                    payType = 4;
                if(payType == 2)
                    payType = 3;*/
                titleIndex = 0;
                setTipsText();
                break;

            case R.id.rbAliPay:
               /* if(payType == 3)
                    payType = 2;
                if(payType == 4)
                    payType = 1;*/
                titleIndex = 1;
                setTipsText();
                break;
        }
    }

    public void setTipsText() {
        if(payType==2||payType==1) {
           /* initTopBar(titles[1] + "充值");
            setText(R.id.tvRechargeTips, getString(R.string.label_recharge_scan_qr, titles[1]));
            setText(R.id.tvRechargeStepTips, getString(R.string.recharge_step_tips, titles[1], titles[1]));*/
            tvTips.setText(getString(R.string.label_recharge_channel, titles[1]));
        }
        if(payType==3) {
            /*initTopBar(titles[0] + "充值");
            setText(R.id.tvRechargeTips, getString(R.string.label_recharge_scan_qr, titles[0]));
            setText(R.id.tvRechargeStepTips, getString(R.string.recharge_step_tips, titles[0], titles[0]));*/
            tvTips.setText(getString(R.string.label_recharge_channel, titles[0]));
        }
        if(payType==4) {
            /*initTopBar(titles[2] + "充值");
            setText(R.id.tvRechargeTips, getString(R.string.label_recharge_scan_qr, titles[2]));
            setText(R.id.tvRechargeStepTips, getString(R.string.recharge_step_tips, titles[2], titles[2]));*/
            tvTips.setText(getString(R.string.label_recharge_channel, titles[2]));
        }
        //tvTips.setText(getString(R.string.label_recharge_channel, titles[titleIndex]));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnNext:
                if(ViewUtil.checkEditEmpty(edRechargeFee, "请输入金额"))
                    return;
                recharge(Double.parseDouble(edRechargeFee.getText().toString()));
                break;

            case R.id.btnRefreshPoint:
                loadUserInfo();
                break;
        }
    }
    public void payByChoseType(final OrderInfo orderInfo, final double fee, final int payType){

      /*  //发送请求
        Intent it = new Intent(RechargeOnlineFirstepActivity_new.this, WebLoadActivity.class);
        it.putExtra(WebLoadFragment.PARAMS_TITLE, "扫码支付");
        it.putExtra(WebLoadFragment.PARAMS_URL, result);
        startActivityForResult(it, REQ_UNION_PAY);*/
      if(payType==1){
            Intent it2 = new Intent(RechargeOnlineFirstepActivity_new.this, WebLoadActivity.class);
            it2.putExtra(WebLoadFragment.PARAMS_TITLE, "支付宝支付");
            it2.putExtra(WebLoadFragment.PARAMS_URL, orderInfo.requestUrl);
            it2.putExtra("loadType","pay");
            startActivityForResult(it2, REQ_UNION_PAY);
        }else {
            OkHttpManager.getInstance();
            OkHttpManager.getAsync(orderInfo.requestUrl + "&format=json", new OkHttpManager.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {

                }

                @Override
                public void requestSuccess(String result) throws Exception {

                    Gson gson = new Gson();
                    try {
                    RechargeResponseInfo rechargeResponseInfo = gson.fromJson(result, RechargeResponseInfo.class);
                    /**
                     * payType = getIntent().getIntExtra("type", 0);
                     orderNo = getIntent().getStringExtra("orderNo");
                     fee = getIntent().getDoubleExtra("fee", 0);
                     qrUrl = getIntent().getStringExtra("qrUrl");
                     */
                    if ("1".equalsIgnoreCase(rechargeResponseInfo.getState())) {
                        Intent it = new Intent(RechargeOnlineFirstepActivity_new.this, RechargeOnlineSecondActivity.class);
                        it.putExtra("type", payType);
                        it.putExtra("orderNo", orderInfo.order_no);
                        it.putExtra("fee", fee);
                        it.putExtra("qrUrl", rechargeResponseInfo.getImg());
                        startActivity(it);
                    } else {
                        T.showShort("预支付订单创建失败");
                    }
                }catch (Exception e){
                        T.showShort("预支付订单创建失败");
                }
                }
            });
     }
    }
//首先生成预支付订单
    public void recharge(final double fee) {
        RechargeRequest req = new RechargeRequest();
        req.total_fee = fee+"";
        req.pay_type=payType+"";
        HttpResultCallback<OrderInfo> callback = new HttpResultCallback<OrderInfo>() {
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
            public void onNext(OrderInfo orderInfo) {
                payByChoseType(orderInfo,fee,payType);
              /*  if(payType == 0) {
                    //mo宝
                    Intent it = new Intent(RechargeOnlineFirstepActivity_new.this, WebLoadActivity.class);
                    it.putExtra(WebLoadFragment.PARAMS_TITLE, "扫码支付");
                    StringBuilder sb = new StringBuilder(ApiInterface.WAP_PAY_MO_BAO);
                    sb.append("?orderNo=").append(orderInfo.order_no).append("&amt=").append(fee);
                    it.putExtra(WebLoadFragment.PARAMS_URL, sb.toString());
                    startActivityForResult(it, REQ_UNION_PAY);
                } else if(payType == 1) {
                    //爱益
                    payByIYI(1, orderInfo.order_no, fee);
                } else if(payType == 4) {
                    payByIYI(2, orderInfo.order_no, fee);
                } else if(payType == 2) {
                    payByDuoBao(1, orderInfo.order_no, fee);
                } else if(payType == 3) {
                    payByDuoBao(2, orderInfo.order_no, fee);
                }*/
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "充值");
        ApiInterface.recharge(req, s);
    }

    /** 爱益支付 1支付宝 2微信 */
    public void payByIYI(final int payType, final String orderNo, final double fee) {
        DuobaoPayRequest req = new DuobaoPayRequest();
        req.pay_type = payType+"";
        req.order_no = orderNo;
        req.fee = fee+"";
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
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
            public void onNext(String s) {

                    Intent it = new Intent(RechargeOnlineFirstepActivity_new.this, RechargeOnlineSecondActivity.class);
                    it.putExtra("type", titleIndex);
                    it.putExtra("orderNo", orderNo);
                    it.putExtra("fee", fee);
                    it.putExtra("qrUrl", s);
                    startActivity(it);

            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.IYIPay(req, s);
    }

    /** 多宝支付 1支付宝 2微信 */
    public void payByDuoBao(final int payType, final String orderNo, final double fee) {
        DuobaoPayRequest req = new DuobaoPayRequest();
        req.pay_type = payType+"";
        req.order_no = orderNo;
        req.fee = fee+"";
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
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
            public void onNext(String s) {
                Intent it = new Intent(RechargeOnlineFirstepActivity_new.this, RechargeOnlineSecondActivity.class);
                it.putExtra("type", titleIndex);
                it.putExtra("orderNo", orderNo);
                it.putExtra("fee", fee);
                it.putExtra("qrUrl", s);
                startActivity(it);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.duobaoPay(req, s);
    }

    @Override
    public void paySuccess() {
        T.showShort("充值成功");
    }

    @Override
    public void payFailure() {
        T.showShort("未充值成功");
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
                tvPoint.setText(userInfo.point+"");
                UserInfoManager.saveUserInfo(RechargeOnlineFirstepActivity_new.this, userInfo);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "");
        ApiInterface.getUserInfo(new BaseRequest(), s);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if(resCode == RESULT_OK) {
            switch(reqCode) {
                case REQ_UNION_PAY:
                    T.showShort("充值成功");
                    finish();
                    break;
            }
        }
    }
}
