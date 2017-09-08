package com.fz.cdh.pcdd.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.bean.GameOddsInfo;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/23.
 */

public class OddsDXDSAdapter extends BaseRecyclerAdapter<GameOddsInfo> {

    public int selectedIndex = 0;

    public OddsDXDSAdapter(Context context, List<GameOddsInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_odds_dxds);
    }

    @Override
    public void onBind(ViewHolder holder, GameOddsInfo item, final int position) {
        String name=item.bili_name;
        if("红".equals(name)){
            holder.setTextBackgroundResource(R.id.tvOddsKey, R.drawable.circle_red);
        }else if("绿".equals(name)){
            holder.setTextBackgroundResource(R.id.tvOddsKey, R.drawable.circle_green);
        }else if("蓝".equals(name)){
            holder.setTextBackgroundResource(R.id.tvOddsKey, R.drawable.circle_blue);
        }else if("豹子".equals(name)){
            holder.setTextBackgroundResource(R.id.tvOddsKey, R.drawable.circle_orange);
        }
        holder.setText(R.id.tvOddsKey, item.bili_name);
        holder.setText(R.id.tvOddsValue, "1:"+item.bili);

        LinearLayout llContainer = holder.getView(R.id.llOddsContainer);
        llContainer.setBackgroundResource(selectedIndex==position? R.drawable.selected_border : 0);
    }

    public GameOddsInfo getSelectedItem() {
        return mData.get(selectedIndex);
    }
}
