package com.fz.cdh.pcdd.ui.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.ui.GameRecordActivity;
import com.fz.cdh.pcdd.ui.RechargeActivity;
import com.fz.cdh.pcdd.ui.RechargeRecordActivity;
import com.fz.cdh.pcdd.ui.WalletActivity;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.WithdrawActivity;
import com.fz.cdh.pcdd.ui.WithdrawRecordActivity;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2017/2/27.
 */

public class HomePageDlg extends CommonDialog implements View.OnClickListener {
    private Context mContext;
    private OnDismissListener mListener;
    public HomePageDlg(Context context,OnDismissListener listener) {
        super(context, R.layout.frag_home, 100, 86);
        this.mContext=context;
        this.mListener=listener;
    }

    @Override
    public void initDlgView() {
        getView(R.id.tvBetLog).setOnClickListener(this);
        getView(R.id.tvHowToPlay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tvBetLog:
                mContext.startActivity(new Intent(mContext, RechargeActivity.class));
              //  T.showLong("充值");
                dismiss();
                break;

            case R.id.tvHowToPlay:
                mContext. startActivity(new Intent(mContext, WithdrawActivity.class));
                //T.showLong("提现");
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(null!=mListener){
            mListener.onDismiss();
        }
    }

    public interface OnDismissListener{
        void onDismiss();
    }
}
