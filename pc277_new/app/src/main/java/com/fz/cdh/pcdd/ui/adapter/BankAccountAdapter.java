package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.RechargeAccountInfo;
import com.fz.cdh.pcdd.ui.TransferBankActivity;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/28.
 */

public class BankAccountAdapter extends BaseRecyclerAdapter<RechargeAccountInfo> {

    public BankAccountAdapter(Context context, List<RechargeAccountInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_bank_account);
    }

    @Override
    public void onBind(ViewHolder holder, final RechargeAccountInfo item, int position) {
        holder.setText(R.id.tvBankName, item.bank_name);
        holder.setText(R.id.tvBranchBank, item.open_card_address);
        holder.setText(R.id.tvBankAccount, item.account);
        holder.setText(R.id.tvRealName, item.real_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, TransferBankActivity.class);
                it.putExtra("data", item);
                mContext.startActivity(it);
            }
        });
    }
}
