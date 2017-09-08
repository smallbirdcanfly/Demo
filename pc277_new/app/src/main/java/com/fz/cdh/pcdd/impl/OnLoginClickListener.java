package com.fz.cdh.pcdd.impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.ui.LoginActivity;
import com.fz.cdh.pcdd.util.T;


/**
 * @author hang
 * 未登录状态跳转到登录页
 */
public class OnLoginClickListener implements OnClickListener, OnItemClickListener {
	
	private Context context;
	private OnClickListener listener;
	private OnItemClickListener itemListener;
	
	public OnLoginClickListener(Context context, OnClickListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	public OnLoginClickListener(Context context, OnItemClickListener itemListener) {
		this.context = context;
		this.itemListener = itemListener;
	} 

	@Override
	public void onClick(View v) {
		if(UserInfoManager.isLogin(PcddApp.applicationContext)) {
			listener.onClick(v);
		} else {
            T.showShort("您当前未登录，请先登录");
            context.startActivity(new Intent(context, LoginActivity.class));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(UserInfoManager.isLogin(PcddApp.applicationContext)) {
			itemListener.onItemClick(arg0, arg1, arg2, arg3);
		} else {
			context.startActivity(new Intent(context, LoginActivity.class));
		}
	}
}
