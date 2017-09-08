package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.RechargeAccountInfo;
import com.fz.cdh.pcdd.ui.TransferAliActivity;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/28.
 */

public class AliAccountAdapter extends BaseRecyclerAdapter<RechargeAccountInfo> {

    public AliAccountAdapter(Context context, List<RechargeAccountInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_ali_account);
    }

    @Override
    public void onBind(ViewHolder holder, final RechargeAccountInfo item, int position) {
        holder.setText(R.id.tvAliAccount, item.account);
        holder.setText(R.id.tvRealName, item.real_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, TransferAliActivity.class);
                it.putExtra("data", item);
                mContext.startActivity(it);
            }
        });
    }
}
