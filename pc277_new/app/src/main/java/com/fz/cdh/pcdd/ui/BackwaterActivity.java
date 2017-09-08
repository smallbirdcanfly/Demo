package com.fz.cdh.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.ui.fragment.BackwaterListFragment;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.ui.widget.MyTabView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hang on 2017/1/23.
 * 回水记录
 */

public class BackwaterActivity extends BaseTopActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backwater);
        init();
    }

    public void init() {
        initTopBar("我的回水");
        btnTopRight2.setVisibility(View.VISIBLE);
        btnTopRight2.setBackgroundResource(R.drawable.btn_cus_svr);

        initTabView();
        getView(R.id.tvBackWaterRule).setOnClickListener(this);
    }

    public void initTabView() {
        MyTabView tab = getView(R.id.tabBackwater);
        List<Map<String, Integer>> titles = new ArrayList<Map<String, Integer>>();
        List<Fragment> fragments = new ArrayList<Fragment>();

        Map<String, Integer> title = new HashMap<String, Integer>();
        title.put("玩法一", null);
        titles.add(title);
        title = new HashMap<String, Integer>();
        title.put("玩法二", null);
        titles.add(title);
        title = new HashMap<String, Integer>();
        title.put("玩法三", null);
        titles.add(title);

        fragments.add(BackwaterListFragment.getInstance(1));
        fragments.add(BackwaterListFragment.getInstance(2));
        fragments.add(BackwaterListFragment.getInstance(3));

        tab.createView(titles, fragments, getSupportFragmentManager(),false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tvBackWaterRule:
                Intent it = new Intent(this, WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, "回水规则");
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BACKWATER_RULE);
                startActivity(it);
                break;
        }
    }
}
