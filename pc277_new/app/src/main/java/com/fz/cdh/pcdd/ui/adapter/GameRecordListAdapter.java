package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.GameRecordInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hang on 2017/1/23.
 */

public class GameRecordListAdapter extends BaseRecyclerAdapter<GameRecordInfo> {

    private String[] gameTypes;
    private SimpleDateFormat format;

    public GameRecordListAdapter(Context context, List<GameRecordInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_game_record);
        gameTypes = context.getResources().getStringArray(R.array.game_type);
        format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    }

    @Override
    public void onBind(ViewHolder holder, GameRecordInfo item, int position) {
        holder.setText(R.id.tvChoiceNo, gameTypes[item.game_type - 1] + "--" + item.choice_no + "期");
        holder.setText(R.id.tvLotteryResult, item.real_result);
        holder.setText(R.id.tvLotteryType, item.result_type);
        holder.setText(R.id.tvBetType, item.choice_name);
        holder.setText(R.id.tvBetPoint, item.point + "元宝");
        String date =format.format(item.create_time);
        holder.setText(R.id.time,  date);
        if (TextUtils.isEmpty(item.real_result)) {
            //未开奖
            holder.setText(R.id.tvWinningPoint, "--元宝");
            holder.setText(R.id.tvRealPoint, "等待开奖");
        } else {
            holder.setText(R.id.tvWinningPoint, item.get_point + "元宝");
            if ((item.get_point - item.point) > 0) {
                holder.setText(R.id.tvRealPoint, "+" + (item.get_point - item.point) + "元宝");
            } else {
                holder.setText(R.id.tvRealPoint, (item.get_point - item.point) + "元宝");
            }

        }
    }
}
