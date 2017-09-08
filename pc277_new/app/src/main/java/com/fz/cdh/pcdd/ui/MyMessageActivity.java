package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.fragment.MsgListFragment;

/**
 * Created by hang on 2017/2/27.
 */

public class MyMessageActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);

        initTopBar("我的消息");

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.flContent, MsgListFragment.getInstance(2)).commit();
    }
}
