package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.BetDetailInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;
import com.fz.cdh.pcdd.ui.widget.FormulaTextView;

import java.util.List;

/**
 * Created by hang on 2017/2/27.
 */

public class LotteryLogAdapter extends BaseRecyclerAdapter<BetDetailInfo.OpenTime> {

    public LotteryLogAdapter(Context context, List<BetDetailInfo.OpenTime> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_lottery_log);
    }

    @Override
    public void onBind(ViewHolder holder, BetDetailInfo.OpenTime item, int position) {
        TextView tvNum = holder.getView(R.id.tvLotteryNum);
        tvNum.setText(Html.fromHtml(mContext.getString(R.string.pre_game_num, item.game_num+"")));
        FormulaTextView tvFormula = holder.getView(R.id.tvLotteryResult);
        tvFormula.setText(item.game_result_desc, item.color);
        holder.setText(R.id.tvBetResultType, "("+item.result_type+")");
    }
}
