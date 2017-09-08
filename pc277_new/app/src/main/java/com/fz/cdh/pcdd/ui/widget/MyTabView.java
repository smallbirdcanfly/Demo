package com.fz.cdh.pcdd.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.util.BitmapTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MyTabView extends LinearLayout {

	private Context context;
	private FragmentManager fm;
	
	private View view;
	private HorizontalScrollView hScrollView;
	private LinearLayout ll_mytab_title;
	private LinearLayout ll_mytab_slide;
	private LinearLayout ll_mytab_pager;
	public ViewPager mytab_pager;
	private TextView tv_slider;
	
	private List<Fragment> fragments;
	private List<Map<String,Integer>> titles;
	private List<LinearLayout> llTitles;
	private List<TextView> textList;
	private List<ImageView> iconList;
	private int pageCount;
	private int curIndex=0;
	private int sliderWidth;
	private int sliderHeight = 5;
	
	public List<Integer> titleColorList;
	private int titleColor = R.color.selector_order_tab_title;
	private int slideColor = R.color.text_blue;
	private int screenIndex = 0;
	private int tabCount = 4;
	private boolean mIsLineSlider=false;//这里是判断,下面的滑块是线,还是块的背景
	
	private PageChangeListener pageChangeListener;
	
	public MyTabView(Context context)
	{
		super(context);
		view=LayoutInflater.from(context).inflate(R.layout.view_mytab, this, true);
		this.context=context;
	}
	
	public MyTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		view=LayoutInflater.from(context).inflate(R.layout.view_mytab, this, true);
		this.context=context;
	}

	public void createView(List<Map<String,Integer>> titles, List<Fragment> fragments, FragmentManager fm,boolean isLineSlider)
	{
		this.fm=fm;
		pageCount=titles.size();
		tabCount = fragments.size();
		this.titles=titles;
		this.fragments=fragments;
		this.mIsLineSlider=isLineSlider;
		sliderWidth = BitmapTool.getScreenWidthPX(context)/tabCount;
		llTitles = new ArrayList<LinearLayout>();
		textList=new ArrayList<TextView>();
		iconList = new ArrayList<ImageView>();
		
		hScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView1);
		ll_mytab_title=(LinearLayout)findViewById(R.id.ll_mytab_title);
		ll_mytab_slide=(LinearLayout)findViewById(R.id.ll_mytab_slide);
		ll_mytab_pager=(LinearLayout)findViewById(R.id.ll_mytab_pager);
		tv_slider=new TextView(context);
		mytab_pager=(ViewPager)view.findViewById(R.id.pager_mytab_content);
		
		initTitle();
		if(isLineSlider){
			initSlider();
		}
		initPager();
	}
	
	public void setTitleVisible(int type){
		ll_mytab_title.setVisibility(type);
	}
	
	public void initTitle()
	{
		for(int i=0; i<pageCount-1; i++)
		{
			setTitleText(i, false);
		}
		setTitleText(pageCount-1, false);
		textList.get(0).setSelected(true);
		iconList.get(0).setSelected(true);
		if(!mIsLineSlider){
			textList.get(0).setBackgroundResource(R.drawable.rect_white);
		}
	}


	private void setTitleText(int index, boolean isDivide) {
		Iterator<Entry<String, Integer>> entryIterator = titles.get(index).entrySet().iterator();
		Entry entry = entryIterator.next();
		LinearLayout ll = new LinearLayout(context);
		ImageView icon = new ImageView(context);
		TextView t=new TextView(context);
//		ll.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
		ll.setLayoutParams(new LayoutParams(sliderWidth, LayoutParams.MATCH_PARENT));
		ll.setOnClickListener(new MyOnClickListener(index));
		ll.setGravity(Gravity.CENTER_HORIZONTAL);
		ll.setOrientation(VERTICAL);
		ll.setPadding(0, BitmapTool.dp2px(context, 5), 0, BitmapTool.dp2px(context, 5));
		if(entry.getValue() != null) {
			icon.setBackgroundResource((Integer) entry.getValue());
			icon.setLayoutParams(new LayoutParams(BitmapTool.dp2px(context, 30), BitmapTool.dp2px(context, 30)));
		}
		t.setText(entry.getKey()+"");
		t.setTextSize(16);
		t.setHeight(BitmapTool.dp2px(context, 35));
		t.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1);
		lp.setMargins(5, 0, 5, 0);
		t.setLayoutParams(lp);
		llTitles.add(ll);
		iconList.add(icon);
		textList.add(t);
		ll.addView(icon);
		ll.addView(t);
		ll_mytab_title.addView(ll);
		if(isDivide) {
			ImageView divide = new ImageView(context);
//			divide.setBackgroundResource(R.drawable.tab_title_divide);
			ll_mytab_title.addView(divide);
		}
		if(!mIsLineSlider){
			t.setBackgroundResource(R.drawable.rect_gray_light);
		}

		if(Build.VERSION.SDK_INT >= 23)
			t.setTextColor(getResources().getColorStateList(titleColorList != null? titleColorList.get(index) : titleColor, null));
		else
			t.setTextColor(getResources().getColorStateList(titleColorList != null? titleColorList.get(index) : titleColor));

	}
	
	public void initSlider()
	{
		tv_slider.setWidth(sliderWidth);
		tv_slider.setHeight(sliderHeight);
		tv_slider.setBackgroundResource(slideColor);
		ll_mytab_slide.addView(tv_slider);
	}
	
	public void initPager()
	{
		mytab_pager.setAdapter(new MyPagerAdapter(fm, fragments));
		mytab_pager.setCurrentItem(0);
		mytab_pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	public void showPageContent(int position) {
		mytab_pager.setCurrentItem(position);
	}
	
	public void setPageChangeListener(PageChangeListener pageChangeListener) {
		this.pageChangeListener = pageChangeListener;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> mFragments;
		
		public MyPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		public MyPagerAdapter(FragmentManager fm, List<Fragment> mFragments)
		{
			super(fm);
			this.mFragments=mFragments;
		}
		
		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			mytab_pager.getParent().requestDisallowInterceptTouchEvent(true);
		}

		@Override
		public void onPageSelected(int selectedIndex) {
			iconList.get(curIndex).setSelected(false);
			textList.get(curIndex).setSelected(false);
			iconList.get(selectedIndex).setSelected(true);
			textList.get(selectedIndex).setSelected(true);
			if(!mIsLineSlider){
				setBg(selectedIndex);
			}
			Animation animation=new TranslateAnimation(curIndex*sliderWidth, selectedIndex*sliderWidth, 0, 0);
			curIndex=selectedIndex;
			animation.setFillAfter(true);
			animation.setDuration(200);
			tv_slider.startAnimation(animation);
			
			if(curIndex>=screenIndex+tabCount || curIndex<screenIndex)
				scroll();
			
			if(pageChangeListener != null)
				pageChangeListener.onPageChanged(curIndex, textList.get(curIndex).getText().toString());
		}
		
		private void scroll() {
			screenIndex = curIndex;
			View v = llTitles.get(curIndex);
			hScrollView.smoothScrollTo((int)v.getX(), (int)v.getY());
		}

		private void setBg(int selectedIndex){
			textList.get(selectedIndex).setBackgroundResource(R.drawable.rect_white);
			textList.get(curIndex).setBackgroundResource(R.drawable.rect_gray_dark);

		}
	}
	
	public class MyOnClickListener implements OnClickListener {
		
		private int index;
		
		public MyOnClickListener(int i)
		{
			this.index=i;
		}
		@Override
		public void onClick(View v) {
			mytab_pager.setCurrentItem(index);
		}
	}
	
	public interface PageChangeListener {
		public void onPageChanged(int index, String tabTitle);
	}
}
 