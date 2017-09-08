package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.bean.NoticeInfo;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/1/18.
 */

public class MsgListAdapter extends BaseRecyclerAdapter<NoticeInfo> {

    public MsgListAdapter(Context context, List<NoticeInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_dynamic_msg);
    }

    @Override
    public void onBind(ViewHolder holder, final NoticeInfo item, int position) {
        holder.setText(R.id.tvMsgTitle, item.title);
        holder.setText(R.id.tvMsgDate, DateUtil.getTime(item.create_time, 0));
        holder.getView(R.id.ivMsgUnread).setVisibility(item.status==1? View.INVISIBLE : View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, item.title);
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_NOTICE_DETAIL
                        +"?notice_id="+item.id+"&user_id="+ UserInfoManager.getUserId(mContext));
                mContext.startActivity(it);
                item.status = 1;
                notifyDataSetChanged();
            }
        });
    }

}
