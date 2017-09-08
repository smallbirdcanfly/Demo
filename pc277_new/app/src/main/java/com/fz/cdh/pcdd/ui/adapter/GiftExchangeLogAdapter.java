package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.GiftLogInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;
import com.fz.cdh.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/3/4.
 */

public class GiftExchangeLogAdapter extends BaseRecyclerAdapter<GiftLogInfo> {

    private String[] status = {"待处理", "已处理"};

    public GiftExchangeLogAdapter(Context context, List<GiftLogInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_gift_exchange_log);
    }

    @Override
    public void onBind(ViewHolder holder, GiftLogInfo item, int position) {
        holder.setText(R.id.tvGiftName, item.gift_name);
        holder.setText(R.id.tvExchangeStatus, status[item.status]);
        holder.setText(R.id.tvGiftPoint, item.gift_point+"");
        holder.setText(R.id.tvExchangeCount, item.gift_count+"");
        holder.setText(R.id.tvExchangePoint, item.point+"");
        holder.setText(R.id.tvExchangeTime, DateUtil.getTime(item.create_time, 0));
    }
}
