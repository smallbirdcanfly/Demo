package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseFragmentActivity;
import com.fz.cdh.pcdd.ui.fragment.ChatBetFragment;
import com.fz.cdh.pcdd.util.CommonUtil;

public class ChatActivity extends BaseFragmentActivity {
	
	public static ChatActivity activityInstance;
    private ChatBetFragment chatFragment;

    String title;
    String toChatUsername;
    String switchStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		activityInstance = this;
        //房间id
		//聊天界面title
		title = getIntent().getExtras().getString("title");
        switchStr=getIntent().getExtras().getString("switchStr");

        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatBetFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
	}

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
	protected void onPause() {
		super.onPause();
		CommonUtil.closeKeyBoard(this);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
