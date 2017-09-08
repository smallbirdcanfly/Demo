package com.fz.cdh.pcdd.ui.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.ImageLoadManager;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.Utility;


/**
 * Created by hang on 2014/11/24.
 * dialog base class
 */
public abstract class CommonDialog implements OnDismissListener {
	
	public Context context;
	public PopupWindow dlg;
	public View dlgView;
	
	private boolean isInitComplete;

	public CommonDialog(Context context, int layoutId) {
		this(context, layoutId, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	public CommonDialog(Context context, int layoutId, int width, int height) {
		this.context = context;
		dlgView = LayoutInflater.from(context).inflate(layoutId, null);
		int w = width;
		int h = height;
		if(width >= 0)
			w = BitmapTool.dp2px(context, width);
		if(height >= 0)
			h = BitmapTool.dp2px(context, height);
		dlg = new PopupWindow(dlgView, w, h, true);
		dlg.setAnimationStyle(R.style.anim_window_fade);
		dlg.setBackgroundDrawable(new BitmapDrawable());
		dlg.setOnDismissListener(this);
		setBackgroundAlpha(context, 1);
		isInitComplete = false;
	}
	
	public void setWindowAnimation(int styleId) {
		dlg.setAnimationStyle(styleId);
	}
	
	public void setBackgroundAlpha(Context context, float alpha) {
		WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
		lp.alpha = alpha;
		((Activity)context).getWindow().setAttributes(lp);
	}
	
	public void show() {
		show(Gravity.CENTER);
	}
	
	public void showAtBottom() {
		setWindowAnimation(R.style.anim_slide_bottom);
		show(Gravity.BOTTOM);
	}
	
	public void show(int gravity) {
		show(gravity, null);
	}
	
	public void show(int gravity, View parent) {
		if(dlg != null) {
			if(!isInitComplete) {
				initDlgView();
				isInitComplete = true;
			}
			if(parent == null)
				parent = ((ViewGroup)((Activity)context).findViewById(android.R.id.content)).getChildAt(0);
			dlg.showAtLocation(parent, gravity, 0, 0);
			setBackgroundAlpha(context, 0.7f);
		}
		Utility.hideSoftInput((Activity) context);
	}
	
	public void showAsDropDown(View anchor) {
		if(dlg != null) {
			if(!isInitComplete) {
				initDlgView();
				isInitComplete = true;
			}
			dlg.showAsDropDown(anchor);
			setBackgroundAlpha(context, 0.7f);
		}
		Utility.hideSoftInput((Activity) context);
	}
	
	public void dismiss() {
		if(dlg!=null && dlg.isShowing()) {
			dlg.dismiss();
		}

	}

	@Override
	public void onDismiss() {
		setBackgroundAlpha(context, 1);
        dismiss();
	}

	public abstract void initDlgView();
	
	public void setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text);
	}

	public void setText(int viewId, int textId) {
		TextView tv = getView(viewId);
		tv.setText(textId);
	}

	public void setImageResource(int viewId, int drawableId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(drawableId);
	}

	public void setImageBitmap(int viewId, Bitmap bm) {
		ImageView iv = getView(viewId);
		iv.setImageBitmap(bm);
	}

	public void setImageByURL(int viewId, final String url) {
		ImageView iv = getView(viewId);
		ImageLoadManager.getInstance().displayImage(url, iv);
	}

	public <T extends View>T getView(int viewId) {
		View view  = dlgView.findViewById(viewId);
		return (T)view;
	}
}
