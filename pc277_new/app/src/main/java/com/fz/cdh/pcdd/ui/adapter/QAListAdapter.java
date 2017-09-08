package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.QAChatMsg;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hang on 2017/3/8.
 */

public class QAListAdapter extends BaseRecyclerAdapter<QAChatMsg> {

    public static final int TYPE_RECEIVE = 1;
    public static final int TYPE_SEND = 2;

    private SimpleDateFormat format;

    public QAListAdapter(Context context, List<QAChatMsg> data) {
        super(context, data);
        putItemLayoutId(TYPE_RECEIVE, R.layout.item_qa_receive);
        putItemLayoutId(TYPE_SEND, R.layout.item_qa_send);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getItemViewType(int position, QAChatMsg item) {
        return item.type;
    }

    @Override
    public void onBind(ViewHolder holder, QAChatMsg item, int position) {
        switch(holder.viewType) {
            case TYPE_RECEIVE:
                bindReceiveView(holder, item, position);
                break;

            case TYPE_SEND:
                bindSendView(holder, item, position);
                break;
        }
    }

    private void bindReceiveView(ViewHolder holder, QAChatMsg item, int position) {
        holder.setText(R.id.timestamp, format.format(new Date(item.timestamp)));
        holder.setImageResource(R.id.iv_userhead, R.drawable.avatar_cus_svr);
        holder.setText(R.id.tv_chatcontent, item.content);
    }

    private void bindSendView(ViewHolder holder, QAChatMsg item, int position) {
        holder.setText(R.id.timestamp, format.format(new Date(item.timestamp)));
        holder.setText(R.id.tv_chatcontent, item.content);
        UserInfo userInfo = UserInfoManager.getUserInfo(mContext);
        if(userInfo!=null && !TextUtils.isEmpty(userInfo.user_photo)) {
            holder.setImageByURL(R.id.iv_userhead, userInfo.user_photo);
        } else {
            holder.setImageResource(R.id.iv_userhead, R.drawable.ease_default_avatar);
        }
    }
}
