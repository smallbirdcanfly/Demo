package com.fz.cdh.pcdd.ui.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fz.cdh.pcdd.manager.ImageLoadManager;
import com.fz.cdh.pcdd.network.RetrofitHelper;

import retrofit2.Retrofit;


public abstract class BaseFragment extends Fragment {
	
	protected FragmentActivity activity;
	protected View rootView;

	protected Retrofit retrofit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
		retrofit = RetrofitHelper.getInstance().getRetrofit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(getLayoutId(), container, false);
		init(rootView);
		return rootView;
	}
	
	protected abstract int getLayoutId();
	
	protected abstract void init(View rootView);
	
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
		if(getActivity()==null || TextUtils.isEmpty(url))
			return;
		
		ImageView iv = getView(viewId);
		ImageLoadManager.getInstance().displayImage(url, iv);
	}

	public <T extends View>T getView(int viewId) {
		View view  = rootView.findViewById(viewId);
		return (T)view;
	}
}
