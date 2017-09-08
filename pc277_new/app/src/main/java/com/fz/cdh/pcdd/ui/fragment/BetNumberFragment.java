package com.fz.cdh.pcdd.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.impl.IBetOdds;
import com.fz.cdh.pcdd.network.bean.GameOddsInfo;
import com.fz.cdh.pcdd.ui.adapter.OddsDXDSAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.adapter.manager.FullyGridLayoutManager;
import com.fz.cdh.pcdd.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by hang on 2017/2/22.
 */

public class BetNumberFragment extends BaseFragment
        implements BaseRecyclerAdapter.OnRecyclerItemClickListener, IBetOdds, View.OnClickListener {

    private TextView tvResult;
    private RecyclerView rvOdds;
    public ViewPager viewPager;

    private ArrayList<GameOddsInfo> data;
    private OddsDXDSAdapter oddsAdapter;

    public static BetNumberFragment getInstance(ViewPager viewPager, ArrayList<GameOddsInfo> data) {
        BetNumberFragment instance = new BetNumberFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", data);
        instance.setArguments(b);
        instance.viewPager = viewPager;
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bet_number;
    }

    @Override
    protected void init(View rootView) {
        data = (ArrayList<GameOddsInfo>) getArguments().getSerializable("data");

        tvResult = getView(R.id.tvOddsResult);
        rvOdds = getView(R.id.rvBetOdds);

        rvOdds.setLayoutManager(new FullyGridLayoutManager(activity, 5));
        oddsAdapter = new OddsDXDSAdapter(activity, data);
        oddsAdapter.setOnRecyclerItemClickListener(this);
        rvOdds.setAdapter(oddsAdapter);

        tvResult.setText(getString(R.string.bet_result_number, data.get(0).result));

        getView(R.id.ivPreviousPage).setOnClickListener(this);
        getView(R.id.ivNextPage).setOnClickListener(this);
    }

    @Override
    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
        oddsAdapter.selectedIndex = position;
        oddsAdapter.notifyDataSetChanged();
        tvResult.setText(getString(R.string.bet_result_number, oddsAdapter.getmData().get(position).result));
    }

    @Override
    public GameOddsInfo getSelectedOdds() {
        return oddsAdapter.getSelectedItem();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivPreviousPage:
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                break;

            case R.id.ivNextPage:
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
