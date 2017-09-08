package com.fz.cdh.pcdd.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author xiaoge
 * @category 设置浮层引导页工具类
 */
public class GuideViewUtil {
    private SharedPreferences mGuideView_SP;
    private Activity mActivity;
    private int mimageViewId;
    private ImageView mimageView;
    private int mtype; //1首页,2//聊天
    private  int mTopMargin=0;
    private int mBottomMargin=0;
    public GuideViewUtil(Activity mActivity, int mimageViewId,int type,int topMargin,int bottomMargin) {
        super();
        this.mActivity = mActivity;
        this.mimageViewId = mimageViewId;
        this.mtype=type;
        this.mTopMargin=topMargin;
        this.mBottomMargin=bottomMargin;
        mGuideView_SP = mActivity.getSharedPreferences("GuideView", Context.MODE_PRIVATE);
        setGuideView();
    }
    public GuideViewUtil(Activity mActivity, int mimageViewId,int type) {
        super();
        this.mActivity = mActivity;
        this.mimageViewId = mimageViewId;
        this.mtype=type;
        mGuideView_SP = mActivity.getSharedPreferences("GuideView", Context.MODE_PRIVATE);
        setGuideView();
    }
    /**
     * @return 返回最顶层视图
     */
    public View getDeCorView() {
        return (ViewGroup) mActivity.getWindow().getDecorView();
    }

    /**
     * @return 返回内容区域根视图
     */
    public View getRootView() {
        return (ViewGroup) mActivity.findViewById(android.R.id.content);
    }

    /**
     * 设置浮层引导页
     */
    public void setGuideView() {
        View view = getRootView();
        if (view == null) {
            return;
        }
        String guide_flag = mGuideView_SP.getString("Guide", "");
        String chat_flag=mGuideView_SP.getString("ChatGuid","");
        if(mtype==1){
            if (guide_flag.equals("isNoFirst")) {
                //不是第一次了就不弹出来
                return;
            }
        }else{
            if (chat_flag.equals("isNoChatFirst")) {
                //不是第一次了就不弹出来
                return;
            }
        }

        final FrameLayout frameLayout = (FrameLayout) view;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        mimageView = new ImageView(mActivity);
        mimageView.setImageResource(mimageViewId);
        mimageView.setScaleType(ScaleType.FIT_XY);
        mimageView.setLayoutParams(layoutParams);
        mimageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                frameLayout.removeView(mimageView);
                if(mtype==1){
                    mGuideView_SP.edit().putString("Guide", "isNoFirst").commit();
                }else {
                    mGuideView_SP.edit().putString("ChatGuid", "isNoChatFirst").commit();
                }

            }
        });
        layoutParams.topMargin=mTopMargin;
        layoutParams.bottomMargin=mBottomMargin;
        frameLayout.addView(mimageView,layoutParams);

    }

    /**
     * 移除浮层引导页
     */
    public void CancleGuideView() {
        String guide_flag = mGuideView_SP.getString("Guide", null);
        if (guide_flag != mActivity.getClass().getName()) {
            return;
        }
        FrameLayout view = (FrameLayout) getRootView();
        if (view == null) {
            return;
        }
        view.removeView(mimageView);

    }
}
