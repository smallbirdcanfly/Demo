package com.fz.cdh.pcdd.ui.base;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;

/**
 * 通用标题栏
 */
public class BaseTopActivity extends BaseFragmentActivity {
	
	protected LinearLayout llTopBack;
	protected TextView tvTopTitle;
	protected Button btnTopRight1;
	protected Button btnTopRight2;
	protected CheckBox btnTopRight3;
	
	protected void initTopBar(String title) {
		llTopBack = (LinearLayout) findViewById(R.id.llTopBack);
		tvTopTitle = (TextView) findViewById(R.id.tvTopTitle);
		btnTopRight1 = (Button) findViewById(R.id.btnTopRight1);
		btnTopRight2 = (Button) findViewById(R.id.btnTopRight2);
		btnTopRight3 = (CheckBox) findViewById(R.id.btnTopRight3);

		tvTopTitle.setText(title);
		
		llTopBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
