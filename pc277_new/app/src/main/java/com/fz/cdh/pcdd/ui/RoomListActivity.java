package com.fz.cdh.pcdd.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.easeui.EaseConstant;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.RoomInfo;
import com.fz.cdh.pcdd.network.request.JoinRoomRequest;
import com.fz.cdh.pcdd.network.request.RoomListRequest;
import com.fz.cdh.pcdd.ui.adapter.RoomGridAdapter;
import com.fz.cdh.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/21.
 * 房间列表
 */

public class RoomListActivity extends BaseTopActivity implements BaseRecyclerAdapter.OnRecyclerItemClickListener {

    private RecyclerView rvData;

    private int gameType;
    private int areaId;
    private String switchStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        init();
    }

    public void init() {
        gameType = getIntent().getIntExtra("gameType", 1);
        areaId = getIntent().getIntExtra("areaId", 0);
        switchStr = getIntent().getStringExtra("switchStr");
        initTopBar("房间列表");
        rvData = getView(R.id.rvData);
        rvData.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        RoomListRequest req = new RoomListRequest();
        req.game_type = gameType+"";
        req.area_id = areaId+"";
        HttpResultCallback<List<RoomInfo>> callback = new HttpResultCallback<List<RoomInfo>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<RoomInfo> roomInfos) {
                RoomGridAdapter adapter = new RoomGridAdapter(RoomListActivity.this, roomInfos);
                adapter.setOnRecyclerItemClickListener(RoomListActivity.this);
                rvData.setAdapter(adapter);
            }
        };
        MySubcriber<List<RoomInfo>> s = new MySubcriber<List<RoomInfo>>(this, callback, true, "加载数据");
        ApiInterface.getRoomList(req, s);
    }

    @Override
    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
        if(TextUtils.isEmpty(UserInfoManager.getUserInfo(this).nick_name)) {
            //先设置昵称
            new AlertDialog.Builder(this).setMessage("您还未设置昵称，请先设置昵称")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(RoomListActivity.this, EditPersonActivity.class));
                        }
                    })
                    .show();
        } else {
            RoomInfo item = (RoomInfo) adapter.getmData().get(position);
            joinRoom(item);
        }
    }

    /**
     * 加入房间
     */
    public void joinRoom(final RoomInfo info) {
        JoinRoomRequest req = new JoinRoomRequest();
        req.room_id = info.id+"";
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                Intent it = new Intent(RoomListActivity.this, ChatActivity.class);
                it.putExtra("roomId", info.id);
                it.putExtra("areaId", info.area_id);
                it.putExtra("gameType", gameType);
                it.putExtra("minPoint", info.per_min_point);
                it.putExtra("maxPoint", info.per_max_point);
                it.putExtra("title", info.room_name);
                it.putExtra("switchStr",switchStr+","+info.room_name);
                it.putExtra(EaseConstant.EXTRA_USER_ID, info.im_gourp_id);
                it.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                startActivity(it);
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber<String> s = new MySubcriber<String>(this, callback, true, "加入房间");
        ApiInterface.joinRoom(req, s);
    }
}
