package com.fz.cdh.pcdd.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.entity.ValueSet;
import com.fz.cdh.pcdd.ui.adapter.DialogListAdapter;

import java.util.List;

/**
 * Created by xiaoge on 2017/7/28.
 */

public class DialogManager {

    /**
     * 二维码保存
     */
    public static void showChoosePicDialog(final Activity ctx, final Bitmap mBitmap, final String savePath) {
        final Dialog dialog = new Dialog(ctx, R.style.action_dialog);
        dialog.show();
        View contentView = LayoutInflater.from(ctx).inflate(R.layout.dialog_choose_pic, null);
        TextView tv_photograph = ( TextView ) contentView.findViewById(R.id.tv_photograph);
        TextView tv_cancle = ( TextView ) contentView.findViewById(R.id.tv_cancle);
        tv_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//保存二维码
                if(BitmapTool.saveBitmap2Local(ctx, mBitmap, savePath)){
                    T.showLong("已保存");
                }else{
                    T.showLong("二维码保存失败，请手动截图");
                }

                dialog.dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.action_sheet_animation); // 添加动画
    }

    /**
     * SweetSheet 自定义列表弹出框
     *
     * @param ctx
     * @param title               标题
     * @param datas               数据源
     * @param onItemClickListener 列表点击事件
     * @return
     */
    public static Dialog showListDialog(Context ctx, View view,String title,int grivaty, int offsetY,final List<ValueSet> datas, OnCancleClickListener onCancleClickListener, DialogListAdapter.OnItemClickListener onItemClickListener) {
        return showListDialog(ctx,view, title, grivaty,offsetY,datas, onCancleClickListener, null, onItemClickListener);
    }

    public static Dialog showListDialog(Context ctx, View v,String title, int grivaty,int offsetY,final List<ValueSet> datas, final OnCancleClickListener onCancleClickListener, final View.OnClickListener onOkClickListener, DialogListAdapter.OnItemClickListener onItemClickListener) {
        final Dialog dialog = new Dialog(ctx, R.style.alert_dialog);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_choose_list, null, false);
        RelativeLayout rl_title = ( RelativeLayout ) view.findViewById(R.id.rl_title);
        TextView tv_cancle = ( TextView ) view.findViewById(R.id.tv_cancle);
        TextView tv_ok = ( TextView ) view.findViewById(R.id.tv_ok);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onCancleClickListener != null) {
                    onCancleClickListener.onClick();
                }
            }
        });//取消点击事件
        if (onOkClickListener != null) {
            tv_ok.setVisibility(View.VISIBLE);
            tv_ok.setOnClickListener(onOkClickListener);
        }//确定点击事件
        TextView tv_title = ( TextView ) view.findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);//设置标题
        } else {//如果标题为Null。则隐藏title布局
            rl_title.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = ( RecyclerView ) view.findViewById(R.id.rv_sort_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        DialogListAdapter adapter = new DialogListAdapter(datas);
        adapter.setOnItemClickListener(onItemClickListener);//列表点击事件
        recyclerView.setAdapter(adapter);

        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
        Window window = dialog.getWindow();
     //  window.setGravity( grivaty);
        window.setGravity( Gravity.LEFT|Gravity.TOP);
        //设置宽高
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = PcddApp.screenWidth-420;
        if (height > PcddApp.screenHeight / 3) {
            lp.height = PcddApp.screenHeight / 3;
        } else {
            lp.height = height;
        }
        //设置窗口的属性，以便设设置
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
       layoutParams.x = x;//x 位置设置
        layoutParams.y= y+50;//y 位置设置
        //layoutParams.y=offsetY;
        window.setAttributes(layoutParams);
//        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.action_sheet_animation); // 添加动画
        return dialog;
    }

    /**
     * 取消点击事件
     */
    public interface OnCancleClickListener {
        public void onClick();
    }
}
