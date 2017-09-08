package com.fz.cdh.pcdd.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;


public class CustomProgressDialog extends Dialog {
    public static final int style_circle = 0;
    public static final int style_pic = 1;
    public static CustomProgressDialog dialog;
    private Context mContext;
    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.mContext=context;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        AnimationDrawable spinner = (AnimationDrawable) ((ImageView) findViewById(R.id.spinnerImageView)).getBackground();
        if (spinner != null) {
            spinner.start();
        }
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.tv_pop_msg).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.tv_pop_msg);
            txt.setText(message);
            txt.invalidate();
        }
        findViewById(R.id.tv_pop_msg).setVisibility(View.GONE);
    }

    public static CustomProgressDialog show(Context context, CharSequence message, boolean cancelable, int style, OnCancelListener cancelListener) {
       // if(dialog == null)
        dialog = new CustomProgressDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        if (style == 0) {
           // dialog.setContentView(R.layout.progress_custom_circle);
        } else if (style == style_pic) {
            dialog.setContentView(R.layout.progress_custom);
        }
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.tv_pop_msg).setVisibility(View.GONE);
        } else {
            ((TextView) dialog.findViewById(R.id.tv_pop_msg)).setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
    
    public static CustomProgressDialog showAtLocation(Context context, View parentView, CharSequence message, boolean cancelable, int style, OnCancelListener cancelListener) {
        if(dialog == null)
            dialog = new CustomProgressDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        if (style == 0) {
        //    dialog.setContentView(R.layout.progress_custom_circle);
        } else if (style == style_pic) {
            dialog.setContentView(R.layout.progress_custom);
        }
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.tv_pop_msg).setVisibility(View.GONE);
        } else {
            ((TextView) dialog.findViewById(R.id.tv_pop_msg)).setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        
		//获取父窗口尺寸
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		parentView.measure(w, h);  
		int width  = parentView.getMeasuredWidth();  
		int height = parentView.getMeasuredHeight();  
		//获取父窗口坐标
		int[] location = new int[2];  
		parentView.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
        
        LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        lp.x = x + (width - lp.width )/2;
        lp.y = y + (height- lp.height)/2;
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
      /*  Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);*/
     //   window.setContentView(R.layout.abc);
       /* WindowManager.LayoutParams lp2 = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);*/
        return dialog;
    }

    @Override
    public void show() {
        super.show();
      //  setBackgroundAlpha(mContext,0.6f);
        LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
    public void onDismiss(){
       // setBackgroundAlpha(mContext,1);
    }
   /* public void setBackgroundAlpha(Context context, float alpha) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity)context).getWindow().setAttributes(lp);
    }*/
}
