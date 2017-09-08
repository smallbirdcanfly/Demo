package com.fz.cdh.pcdd.ui.widget.dialog;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.util.ShareHelper;


public class ShareMenuWindow extends CommonDialog implements OnClickListener {
	
	public String title;
	public String content;
	public String url;
	public String thumbnail;

	public ShareMenuWindow(Context context) {
		super(context, R.layout.dlg_share_menu, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void initDlgView() {
		getView(R.id.llShareWx).setOnClickListener(this);
		getView(R.id.llShareWxMoment).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.llShareWx:
			ShareHelper.shareWechat(context, title, content, url, thumbnail);
			break;
			
		case R.id.llShareWxMoment:
			ShareHelper.shareWechatMoment(context, title, content, url, thumbnail);
			break;
		}
	}
}
