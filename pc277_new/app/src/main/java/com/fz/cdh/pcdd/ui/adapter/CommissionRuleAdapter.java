package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.CommissionInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by hang on 2017/4/1.
 */

public class CommissionRuleAdapter extends BaseRecyclerAdapter<CommissionInfo> {

    public CommissionRuleAdapter(Context context, List<CommissionInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_commission_rule);
    }
    @Override
    public void onBind(ViewHolder holder, CommissionInfo item, int position) {
        holder.setText(R.id.tvLevel, item.level);
        holder.setText(R.id.tvUsrPoint,format1(Double.parseDouble(item.start_point),0)+"-"+format1(Double.parseDouble(item.end_point),0));
        holder.setText(R.id.tvCommissionPoint, format1(Double.parseDouble(item.get_point),2));
       // holder.setText(R.id.tvUsrPoint, item.start_point+"-"+item.end_point);
      //  holder.setText(R.id.tvCommissionPoint, item.get_point);
        //
        //new java.text.DecimalFormat("#.00").format(item.get_point);
    }
    public static String format1(double value,int pointNum) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(pointNum, RoundingMode.HALF_UP);
        return bd.toString();
    }
}
