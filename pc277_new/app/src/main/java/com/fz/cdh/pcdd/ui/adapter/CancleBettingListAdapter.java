package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.AccountRecordInfo;
import com.fz.cdh.pcdd.network.bean.LatestChoiceInfo;
import com.fz.cdh.pcdd.ui.TransferAliActivity;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;
import com.fz.cdh.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by xiaoge on 2017/1/23.
 */

public class CancleBettingListAdapter extends BaseRecyclerAdapter<LatestChoiceInfo> {
    private Context mContext;

    public CancleBettingListAdapter(Context context, List<LatestChoiceInfo> data) {
        super(context, data);
        this.mContext=context;
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.dialog_cancle_item);

    }

    @Override
    public void onBind(ViewHolder holder, LatestChoiceInfo item, int position) {
        holder.setText(R.id.tvAccountTime, "12345");
        holder.setText(R.id.tvAccountContent,"122");
        holder.setText(R.id.tvAccountMoney, "122");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"DIANJIN",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
