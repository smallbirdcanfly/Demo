package com.fz.cdh.pcdd.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.KefuInfo;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.ui.BindBankActivity;
import com.fz.cdh.pcdd.ui.BindMobileActivity;
import com.fz.cdh.pcdd.ui.CusSvrActivity;
import com.fz.cdh.pcdd.ui.EditWithdrawPwdActivity;
import com.fz.cdh.pcdd.weight.OpartionDialog;

/**
 * Created by hang on 2017/2/27.
 */

public class CheckUtil {

    /** 检查绑定情况 */
    public static boolean checkBind(Context context) {
        return checkBindMobile(context) && checkSetWithdrawPwd(context) && checkBindBank(context);
    }

    /** 是否绑定手机 */
    public static boolean checkBindMobile(final Context context) {
        UserInfo userInfo = UserInfoManager.getUserInfo(context);
        if(TextUtils.isEmpty(userInfo.mobile)) {
            //T.showShort("您还未绑定手机，请先绑定手机");
            OpartionDialog opartionDialog=new OpartionDialog(context, "提示", "为了您的资金安全，请绑定手机号", "1", new OpartionDialog.OnOptionListener() {
                @Override
                public void onOption() {
                    context.startActivity(new Intent(context, BindMobileActivity.class));
                }
            });
            opartionDialog.show();
           // context.startActivity(new Intent(context, BindMobileActivity.class));
            return false;
        }
        return true;
    }

    /** 是否设置提现密码 */
    public static boolean checkSetWithdrawPwd(Context context) {
        UserInfo userInfo = UserInfoManager.getUserInfo(context);
        if(TextUtils.isEmpty(userInfo.withdrawals_password)) {
            T.showShort("您还未设置提现密码，请先设置提现密码");
            context.startActivity(new Intent(context, EditWithdrawPwdActivity.class));
            return false;
        }
        return true;
    }

    /** 是否绑定银行卡 */
    public static boolean checkBindBank(Context context) {
        UserInfo userInfo = UserInfoManager.getUserInfo(context);
        if(TextUtils.isEmpty(userInfo.bank_no)) {
            T.showShort("您还未绑定银行卡，请先绑定银行卡");
            context.startActivity(new Intent(context, BindBankActivity.class));
            return false;
        }
        return true;
    }

    public static void startCusSvr(final Context context) {
        HttpResultCallback<KefuInfo> callback = new HttpResultCallback<KefuInfo>() {
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
            public void onNext(KefuInfo kefuInfo) {
                TransferTempDataUtil.getInstance().setData(kefuInfo);
                context.startActivity(new Intent(context, CusSvrActivity.class));
            }
        };
        MySubcriber s = new MySubcriber(context, callback, true, "加载数据");
        ApiInterface.getKefuQAList(new BaseRequest(), s);
    }
}
