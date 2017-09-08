package com.hyphenate.easeui.widget;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleCountDownTextView extends LinearLayout {

    private final static String DATE_MS = "mm分ss秒";

	private Context mContext;
	private CountDownTimer mTimer;
	
	private TextView tvHour;

	private OnCountDownListener listener;

	public SimpleCountDownTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public SimpleCountDownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public SimpleCountDownTextView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public void init(Context context, AttributeSet attrs, int defStyleAttr) {
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.simple_text_count_down, this);
		tvHour = (TextView) findViewById(R.id.tvCountDownHour);
	}

	public void setText(String timeStr) {
		tvHour.setText(timeStr);
	}

	public void setText(String text, int i) {
		tvHour.setText(text);
		stopCountDown();
	}

	public void startCountDown(final long second) {
		if(mTimer != null)
			return;

		long time = second*1000;

		if(time <= 0) {
			if(listener != null)
				listener.onFinish();
			return;
		}

		mTimer = new CountDownTimer(time, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
//				millisUntilFinished = millisUntilFinished / 1000;
				tvHour.setText(longToString(millisUntilFinished));
			}

			@Override
			public void onFinish() {
				tvHour.setText("封盘中");
				if(listener != null)
					listener.onFinish();
			}
		};
		mTimer.start();
	}
	
	public void stopCountDown() {
		if(mTimer != null) {
			mTimer.cancel();
			mTimer = null;
			tvHour.setText("");
		}
	}

	public void setTextColor(int color) {
		tvHour.setTextColor(getResources().getColor(color));
	}

	public void setTextSize(int size) {
		tvHour.setTextSize(size);
	}

	public void setOnCountDownListener(OnCountDownListener listener) {
		this.listener = listener;
	}

	public interface OnCountDownListener {
		public void onFinish();
	}

	public String longToString(long millisecond) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_MS);
        return format.format(new Date(millisecond));
    }
}
