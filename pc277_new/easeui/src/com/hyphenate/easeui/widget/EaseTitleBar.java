package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;

import java.util.ArrayList;

/**
 * title bar
 *
 */
public class EaseTitleBar extends RelativeLayout{

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected LinearLayout rightLayout;
    public ImageView rightImage;
    public ImageView rightImage2;
    protected TextView titleView;
    protected RelativeLayout titleLayout;
    TextSwitcherView swtchTitle;
    public EaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseTitleBar(Context context) {
        super(context);
        init(context, null);
    }
    
    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (LinearLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        rightImage2 = (ImageView) findViewById(R.id.right_image2);
        titleView = (TextView) findViewById(R.id.title);
       // titleView.setVisibility(GONE);
         swtchTitle = (TextSwitcherView) findViewById(R.id.swtchTitle);
      //  swtchTitle.setVisibility(VISIBLE);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
        
        parseStyle(context, attrs);
    }
    
    private void parseStyle(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseTitleBar);
            String title = ta.getString(R.styleable.EaseTitleBar_titleBarTitle);
            titleView.setText(title);
            
            Drawable leftDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }
        
            Drawable background = ta.getDrawable(R.styleable.EaseTitleBar_titleBarBackground);
            if(null != background) {
                titleLayout.setBackgroundDrawable(background);
            }
            
            ta.recycle();
        }
    }
    
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }
    
    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }
    
    public void setLeftLayoutClickListener(OnClickListener listener){
        leftLayout.setOnClickListener(listener);
    }
    
    public void setRightLayoutClickListener(OnClickListener listener){
        rightLayout.setOnClickListener(listener);
    }
    
    public void setLeftLayoutVisibility(int visibility){
        leftLayout.setVisibility(visibility);
    }
    
    public void setRightLayoutVisibility(int visibility){
        rightLayout.setVisibility(visibility);
    }
    
    public void setTitle(String title){
        String[] titles= title.split(",");
        if(null!=titles&&titles.length>1) {
            ArrayList<String> titleArr = new ArrayList<>();
            titleArr.add(titles[0]);
            titleArr.add(titles[1]);
            titleArr.add(titles[2]);
            swtchTitle.getResource(titleArr);
            swtchTitle.setVisibility(VISIBLE);
            titleView.setVisibility(GONE);
        }else {
            swtchTitle.setVisibility(GONE);
            titleView.setVisibility(VISIBLE);
            titleView.setText(title);
        }
    }
    
    public void setBackgroundColor(int color){
        titleLayout.setBackgroundColor(color);
    }
    
    public RelativeLayout getLeftLayout(){
        return leftLayout;
    }
    
    public LinearLayout getRightLayout(){
        return rightLayout;
    }
}
