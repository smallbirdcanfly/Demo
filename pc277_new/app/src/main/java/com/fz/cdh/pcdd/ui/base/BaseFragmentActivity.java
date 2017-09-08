package com.fz.cdh.pcdd.ui.base;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.ImageLoadManager;
import com.fz.cdh.pcdd.network.RetrofitHelper;
import com.fz.cdh.pcdd.ui.widget.SwipeBackLayout;

import java.lang.reflect.Field;

import retrofit2.Retrofit;

/**
 * Created by hang on 2016/7/16.
 */
public class BaseFragmentActivity extends FragmentActivity implements SlidingPaneLayout.PanelSlideListener{

    protected Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        retrofit = RetrofitHelper.getInstance().getRetrofit();
       /* if (isOpenSwipeBack()) {
            layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                    R.layout.base, null);
            layout.attachToActivity(this);
        }*/
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，
            //默认是32dp
            try {
                //更改属性
                Field field = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                field.setAccessible(true);
                field.set(slidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置监听事件
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(Color.parseColor("#00000000"));

            View leftView = new View(this);
            slidingPaneLayout.addView(leftView, 0);
            //获取到最顶层的视图容器
            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            //获取到左边的视图
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            //设置左边的视图为透明
            decorChild.setBackgroundColor(Color.parseColor("#00000000"));
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            //在右边添加这个视图
            slidingPaneLayout.addView(decorChild, 1);
        }
    }
    /**
     * 是否支持滑动退出
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onPanelSlide(View view, float v) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        //设置Activity退出的动画
        // this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    @Override
    public void onPanelClosed(View view) {

    }
    // protected SwipeBackLayout layout;

    /**
     * 是否开启右滑返回
     * @return
     */
  /*  protected boolean isOpenSwipeBack(){
        return true;
    };*/

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
        View view  = findViewById(viewId);
        return (T)view;
    }
}
