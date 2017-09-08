package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.ui.fragment.WebLotteryFragment;

public class WebLoadActivity extends BaseTopActivity {
	
	private String title;
	private String url;
	
	private BaseFragment fragment;
	private View view ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_load);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		view = findViewById(R.id.bar);
		title = getIntent().getStringExtra(WebLoadFragment.PARAMS_TITLE);
		url = getIntent().getStringExtra(WebLoadFragment.PARAMS_URL);
		//loadType
		String loadType=getIntent().getStringExtra("loadType");
//		if("幸运大转盘".equals(title)){
//			view.setVisibility(View.INVISIBLE);
//		}else{
			initTopBar(title);
//			view.setVisibility(View.VISIBLE);
//		}
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		if("lottery".equalsIgnoreCase(loadType)){
            String userId=getIntent().getStringExtra("userId");
			fragment = WebLotteryFragment.getInstance(url,userId);
			t.replace(R.id.rlContent, fragment);
			t.commit();
		}else if("pay".equalsIgnoreCase(loadType)) {
			fragment = WebLoadFragment.getInstance(url,loadType);
			t.replace(R.id.rlContent, fragment);
			t.commit();
		}else {
			fragment = WebLoadFragment.getInstance(url);
			t.replace(R.id.rlContent, fragment);
			t.commit();
		}
	}

	@Override
	public void onBackPressed() {
		if(fragment instanceof WebLoadFragment) {
			((WebLoadFragment)fragment).goBack();
		}
		if(fragment instanceof WebLotteryFragment) {
			((WebLotteryFragment)fragment).goBack();
		}
	}
}
