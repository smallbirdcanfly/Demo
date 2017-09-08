package com.fz.cdh.pcdd.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.QAChatMsg;
import com.fz.cdh.pcdd.network.bean.KefuInfo;
import com.fz.cdh.pcdd.network.bean.KefuQAInfo;
import com.fz.cdh.pcdd.ui.adapter.QAListAdapter;
import com.fz.cdh.pcdd.ui.adapter.manager.FullyLinearLayoutManager;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.TransferTempDataUtil;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hang on 2017/3/8.
 * 客服
 */

public class CusSvrActivity extends BaseTopActivity implements View.OnClickListener {

    private RecyclerView rvList;
    private EditText edContent;
    private Button btnSend;
    private ImageView iv_emo;

    private KefuInfo kefuInfo;

    private String greetMsg;
    private HashMap<String, String> qaList;
    private QAListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_svr);
        initData();
        initView();
    }

    private void initData() {
        kefuInfo = (KefuInfo) TransferTempDataUtil.getInstance().getData();
        TransferTempDataUtil.getInstance().recycle();

        qaList = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder(getString(R.string.cus_svr_greet));
        for (KefuQAInfo item: kefuInfo.quest_list) {
            sb.append(item.title).append(" (请回复数字").append(item.id).append(")\n");
            qaList.put(item.id, item.content);
        }
        sb.append("\n更多问题请联系24小时在线客服微信号").append(kefuInfo.kefu_weixin)
                .append("或QQ").append(kefuInfo.kefu_qq);
        greetMsg = sb.toString();
    }


    private void initView() {
        initTopBar("客服");
        rvList = getView(R.id.rvQAList);
        edContent = getView(R.id.edContent);
        btnSend = getView(R.id.btnSend);
        iv_emo=getView(R.id.iv_emo);

        rvList.setLayoutManager(new FullyLinearLayoutManager(this));
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new QAListAdapter(this, new ArrayList<QAChatMsg>());
        rvList.setAdapter(adapter);

        btnSend.setOnClickListener(this);
        iv_emo.setOnClickListener(this);

        adapter.insert(new QAChatMsg(QAListAdapter.TYPE_RECEIVE, greetMsg, System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSend:
                if(ViewUtil.checkEditEmpty(edContent, "请输入对应数字"))
                    return;

                String id = edContent.getText().toString().trim();
                adapter.insert(new QAChatMsg(QAListAdapter.TYPE_SEND, id, System.currentTimeMillis()));
                edContent.setText("");

                String content = qaList.get(id);
                if(TextUtils.isEmpty(content))
                    content = greetMsg;
                adapter.insert(new QAChatMsg(QAListAdapter.TYPE_RECEIVE, content, System.currentTimeMillis()));

                rvList.scrollToPosition(adapter.getCount()-1);
                break;

            case R.id.iv_emo:
                Toast.makeText(CusSvrActivity.this,"功能待开发",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
