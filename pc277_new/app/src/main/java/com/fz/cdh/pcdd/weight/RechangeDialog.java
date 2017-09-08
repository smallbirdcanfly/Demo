package com.fz.cdh.pcdd.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * 充值界面的弹框
 *
 * @author xiaoge
 */
public class RechangeDialog extends Dialog {

    private Context mContext;
    private TextView tv_sure;


    public RechangeDialog(Context context) {
        super(context, R.style.myDialogTheme);
        this.mContext = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_rechange);
        tv_sure = (TextView) findViewById(R.id.tv);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        WindowManager.LayoutParams lp=  getWindow().getAttributes();
        lp.y=-280;
        getWindow().setAttributes(lp);
    }



}
