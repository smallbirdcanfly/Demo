package com.fz.cdh.pcdd.util;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.weight.PopUpwindowLayout;

import java.util.ArrayList;
public class CustomTools {
	public static void showupview(final Context context , View v) {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("充值");
        titles.add("提现");
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.layout_popupwindow, null);
        PopUpwindowLayout popUpwindowLayout = (PopUpwindowLayout) view.findViewById(R.id.llayout_popupwindow);
        popUpwindowLayout.initViews(context, titles, false);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        int[] location = new int[2];
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 获得位置
        v.getLocationOnScreen(location);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        popUpwindowLayout.setClickListener(new PopUpwindowLayout.OnClickCallback() {

            @Override
            public void onItemClick(LinearLayout parentView, int size, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(context, "充值", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "提现", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });


    }

    public static void showdownview(final Context context , View v) {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("充值");
        titles.add("提现");
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.layout_popupwindow, null);
        PopUpwindowLayout popUpwindowLayout = (PopUpwindowLayout) view.findViewById(R.id.llayout_popupwindow);
        popUpwindowLayout.initViews(context, titles, false);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        int[] location = new int[2];
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 获得位置
        v.getLocationOnScreen(location);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//				popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        popupWindow.showAsDropDown(v);
        popUpwindowLayout.setClickListener(new PopUpwindowLayout.OnClickCallback() {

            @Override
            public void onItemClick(LinearLayout parentView, int size, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(context, "充值", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
