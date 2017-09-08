package com.fz.cdh.pcdd.util;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {
	
	private static Toast toast;
	private static Handler handler;

    /**
     * 必须先在UI线程中调用
     */
    public static void init(Context context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                toast.setDuration(msg.what);
                toast.setText(msg.obj.toString());
                toast.show();
            }

        };
    }
    
    /**
     * 显示Toast
     *
     * @param shortTime
     */
    public static void showOnThread(CharSequence message, boolean shortTime) {
        if (TextUtils.isEmpty(message))
            return;

        Message msg = Message.obtain();
        msg.obj = message;
        msg.what = shortTime ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
        handler.sendMessage(msg);
    }

	/**
	 * 短时间显示Toast
	 * 
	 * @param message
	 */
	public static void showShort(CharSequence message) {
		toast.setText(message);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param message
	 */
	public static void showShort(int message) {
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setText(message);
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(CharSequence message) {
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setText(message);
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(int message) {
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setText(message);
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void show(CharSequence message, int duration) {
		toast.setDuration(duration);
		toast.setText(message);
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void show(int message, int duration) {
		toast.setDuration(duration);
		toast.setText(message);
		toast.show();
	}

	/** Hide the toast, if any. */
	public static void hideToast() {
		if (null != toast) {
			toast.cancel();
		}
	}
	
	public static void showNetworkError() {
		showOnThread("网络异常，请重试", true);
	}
}
