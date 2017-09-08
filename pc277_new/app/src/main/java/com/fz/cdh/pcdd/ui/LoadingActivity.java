package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fz.cdh.pcdd.MainActivity;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseFragmentActivity;

public class LoadingActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//finish();
				startActivity(new Intent(LoadingActivity.this, MainActivity.class));
				finish();
			}
		}, 1500);
	}

	@Override
	public void onBackPressed() {
	}
}
