package com.fz.cdh.pcdd.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.VCodeInfo;
import com.fz.cdh.pcdd.network.request.VCodeRequest;
import com.fz.cdh.pcdd.util.T;

public class CountDownButton extends Button {
	
	private static final int HANDLER_COUNTDOWN = 0x100;
	
	private Context mContext;
	
	private boolean isPaying;
	private int waitTime;
	private String vcodeId;
	
	private CountDownListener listener;

	public CountDownButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CountDownButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CountDownButton(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		onStart();
	}
	
	public void onStart() {
		waitTime = 60;
		isPaying = true;
		this.setText("获取验证码");
	}
	
	public void onStop() {
		waitTime = 0;
		isPaying = false;
		this.setText("获取验证码");
	}
	
	/**
	 * 获取验证码
	 */
	public void getVCode(String mobile, final CountDownListener listener) {
		this.listener = listener;
		startCountDown();

		VCodeRequest req = new VCodeRequest();
		req.phone = mobile;
		HttpResultCallback<VCodeInfo> callback = new HttpResultCallback<VCodeInfo>() {
			@Override
			public void onStart() {
			}

			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				onStop();
				T.showShort(e.getMessage());
			}

			@Override
			public void onNext(VCodeInfo vCodeInfo) {
				vcodeId = vCodeInfo.id;
			}
		};
		MySubcriber s = new MySubcriber(mContext, callback, true, "");
		ApiInterface.getVCode(req, s);

	}
	
	public String getVCodeId() {
		return vcodeId;
	}
	
	/**
	 * 开始倒计时
	 */
	private void startCountDown() {
		this.setEnabled(false);
		waitTime = 60;
		isPaying = true;
		new Thread(new CountDownRunnable()).start();
		this.setText(waitTime+"s");
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case HANDLER_COUNTDOWN:
				if(waitTime > 0) {
					CountDownButton.this.setText(waitTime+"s");
				} else {
					CountDownButton.this.setText("获取验证码");
					CountDownButton.this.setEnabled(true);
				}
				break;
			}
		}
	};
	
	private class CountDownRunnable implements Runnable {
		@Override
		public void run() {
			while(waitTime > 0) {
				if(!isPaying)
					break;
				
				try {
					Thread.sleep(1000);
					waitTime--;
					mHandler.sendEmptyMessage(HANDLER_COUNTDOWN);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface CountDownListener {
		
	}
}
