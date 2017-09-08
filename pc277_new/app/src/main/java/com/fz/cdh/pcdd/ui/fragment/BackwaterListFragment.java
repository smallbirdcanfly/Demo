package com.fz.cdh.pcdd.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.BackwaterInfo;
import com.fz.cdh.pcdd.network.request.BackWaterListRequest;
import com.fz.cdh.pcdd.ui.adapter.BackwaterListAdapter;
import com.fz.cdh.pcdd.ui.adapter.divider.DividerItemDecoration;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.fz.cdh.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 */

public class BackwaterListFragment extends BaseFragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String PARAMS_LEVEL = "level";

    private PullLoadMoreRecyclerView rvData;

    private int level; //初 中 高

    private int pageNo = 1;
    private int pageSize = 10;
    private BackwaterListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_backwater_list;
    }

    public static BackwaterListFragment getInstance(int level) {
        BackwaterListFragment instance = new BackwaterListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARAMS_LEVEL, level);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected void init(View rootView) {
        if(getArguments() != null) {
            level = getArguments().getInt(PARAMS_LEVEL);
        }

        rvData = getView(R.id.rvData);

        rvData.setLinearLayout();
        rvData.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }

    public void loadData() {
        BackWaterListRequest req = new BackWaterListRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        req.type = level+"";
        HttpResultCallback<List<BackwaterInfo>> callback = new HttpResultCallback<List<BackwaterInfo>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                rvData.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(Throwable e) {
                rvData.setPullLoadMoreCompleted();
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<BackwaterInfo> data) {
                updateView(data);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getBackWaterList(req, s);
    }

    private void updateView(List<BackwaterInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new BackwaterListAdapter(activity, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }
}
