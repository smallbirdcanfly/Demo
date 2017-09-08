package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.GameRecordInfo;
import com.fz.cdh.pcdd.network.request.GameRecordRequest;
import com.fz.cdh.pcdd.ui.adapter.GameRecordListAdapter;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.fz.cdh.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 * 游戏记录
 */

public class GameRecordActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int gameType = 0;   //1 北京快8   2 加拿大
    private String startTime;   //2017-02-20 开始时间（可选）
    private String endTime;
    private int roomId;

    private int pageNo = 1;
    private int pageSize = 10;
    private GameRecordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_record);
        init();
    }

    private void init() {
        gameType = getIntent().getIntExtra("gameType", 0);
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        roomId = getIntent().getIntExtra("roomId", 0);

        initTopBar("投注记录");
        rvData = getView(R.id.rvData);
        rvData.setLinearLayout();
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
        GameRecordRequest req = new GameRecordRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        req.game_type = gameType+"";
        if(roomId != 0)
            req.room_id = roomId+"";
        if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            req.start_time = startTime;
            req.end_time = endTime;
        }
        HttpResultCallback<List<GameRecordInfo>> callback = new HttpResultCallback<List<GameRecordInfo>>() {
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
            public void onNext(List<GameRecordInfo> gameRecordInfos) {
                updateView(gameRecordInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getGameRecord(req, s);
    }

    private void updateView(List<GameRecordInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new GameRecordListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }
}
