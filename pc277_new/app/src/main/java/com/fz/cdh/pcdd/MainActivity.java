package com.fz.cdh.pcdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.manager.VersionManager;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.ui.LoadingActivity;
import com.fz.cdh.pcdd.ui.LoginActivity;
import com.fz.cdh.pcdd.ui.base.BaseFragmentActivity;
import com.fz.cdh.pcdd.ui.fragment.DynamicFragment;
import com.fz.cdh.pcdd.ui.fragment.HomeFragment;
import com.fz.cdh.pcdd.ui.fragment.MineFragment;
import com.fz.cdh.pcdd.ui.fragment.RechargeFragment;
import com.fz.cdh.pcdd.util.T;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseFragmentActivity implements View.OnTouchListener {

    public static final int REQ_PERSONAL_CENTER = 0x100;

    public FragmentTabHost fTabHost;
    private String[] tabTags = {"tab_home", "tab_recharge", "tab_dynamic", "tab_mine"};
    private Class[] tabFragments = {HomeFragment.class, RechargeFragment.class, DynamicFragment.class, MineFragment.class};
    private String[] tabTitles = {"首页", "充值", "动态", "我的"};
    private int[] tabIcons = {R.drawable.tab_home, R.drawable.tab_recharge, R.drawable.tab_dynamic, R.drawable.tab_mine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  startActivity(new Intent(this, LoadingActivity.class));

        initTabHost();

        connectIMServer();

       // VersionManager.checkVersion(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  initTabHost();

        connectIMServer();

        VersionManager.checkVersion(this);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    public void initTabHost() {
        fTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for(int i=0; i<tabFragments.length; i++) {
            TabHost.TabSpec spec = fTabHost.newTabSpec(tabTags[i]);
            spec.setIndicator(getTabView(i));
            fTabHost.addTab(spec, tabFragments[i], null);
            fTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }

        fTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    public View getTabView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_tab_main, null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivMainTabIcon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvMainTabTitle);
        ivIcon.setImageResource(tabIcons[i]);
        tvTitle.setText(tabTitles[i]);
        return view;
    }

    public void connectIMServer() {
        if(!UserInfoManager.isLogin(this))
            return;

        UserInfo userInfo = UserInfoManager.getUserInfo(this);
        if(userInfo == null)
            return;

        EMClient.getInstance().login(userInfo.im_account, "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onProgress(int arg0, String arg1) {
            }

            @Override
            public void onError(int arg0, String arg1) {
                if(null!=arg1&&arg1.contains("already")&&arg1.contains("login")){
                    EMClient.getInstance().logout(true);
                    connectIMServer();
                }else {
                    UserInfoManager.clearUserInfo(null);
                    Log.e("", "环信login error " + arg1);
                    T.showOnThread("登录验证失败：" + arg1, true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitBy2Click();
    }

    private static boolean isExit = false;

    private void exitBy2Click() {
        if(!isExit) {
            isExit = true;
            T.showShort("再按一次退出程序");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 1500);
        } else {
            finish();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
            if(!UserInfoManager.isLogin(this) &&
                    (fTabHost.getTabWidget().getChildAt(1)==v || fTabHost.getTabWidget().getChildAt(2)==v || fTabHost.getTabWidget().getChildAt(3)==v)) {
                T.showShort("您当前未登录，请先登录");
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if(resCode == RESULT_OK) {
            switch(reqCode) {
                case REQ_PERSONAL_CENTER:
                    startActivity(new Intent(this, LoginActivity.class));
                    fTabHost.setCurrentTab(0);
                    break;
            }
        }
    }
}
