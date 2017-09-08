package com.fz.cdh.pcdd.ui.widget.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.impl.IBetOdds;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.bean.GameOddsInfo;
import com.fz.cdh.pcdd.network.bean.GameTypeInfo;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.adapter.BetPanelPagerAdapter;
import com.fz.cdh.pcdd.ui.fragment.BetDXDSFragment;
import com.fz.cdh.pcdd.ui.fragment.BetNumberFragment;
import com.fz.cdh.pcdd.ui.fragment.BetSpecialFragment;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.util.Arith;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 2017/2/21.
 */

public class BettingOddsDlg extends DialogFragment implements View.OnClickListener {

    private ViewPager vpPanel;
    private EditText edPoint;
    private Button btnBetting;
    private Button btnMinPoint;
    private Button btnDoublePoint;
   // private Button btnCancleOrder;
    private GameTypeInfo gameTypeInfo;

    public int areaId;
    public double minPoint;  //个人下注下限
    public double maxPoint;  //个人下注上限

    public static BettingOddsDlg getInstance(GameTypeInfo gameTypeInfo) {
        BettingOddsDlg instance = new BettingOddsDlg();
        Bundle b = new Bundle();
        b.putSerializable("data", gameTypeInfo);
        instance.setArguments(b);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCanceledOnTouchOutside(true);

        gameTypeInfo = (GameTypeInfo) getArguments().getSerializable("data");

        View view = inflater.inflate(R.layout.dlg_betting_odds, container);
        vpPanel = (ViewPager) view.findViewById(R.id.vpBetPanel);
        edPoint = (EditText) view.findViewById(R.id.edBetPoint);
        btnBetting = (Button) view.findViewById(R.id.btnBetting);
        btnMinPoint = (Button) view.findViewById(R.id.btnMinPoint);
        btnDoublePoint = (Button) view.findViewById(R.id.btnDoublePoint);
       // btnCancleOrder = (Button) view.findViewById(R.id.btnCancleOrder);
        List<Fragment> fragments = new ArrayList<Fragment>();
        if(gameTypeInfo.da_xiao!=null && gameTypeInfo.da_xiao.size()>0)
            fragments.add(BetDXDSFragment.getInstance(vpPanel, gameTypeInfo.da_xiao));
        if(gameTypeInfo.shu_zi!=null && gameTypeInfo.shu_zi.size()>0)
            fragments.add(BetNumberFragment.getInstance(vpPanel, gameTypeInfo.shu_zi));
        if(gameTypeInfo.te_shu!=null && gameTypeInfo.te_shu.size()>0)
            fragments.add(BetSpecialFragment.getInstance(vpPanel, gameTypeInfo.te_shu));
        vpPanel.setAdapter(new BetPanelPagerAdapter(getChildFragmentManager(), fragments));

        btnBetting.setOnClickListener(this);
        btnMinPoint.setOnClickListener(this);
        btnDoublePoint.setOnClickListener(this);
       // btnCancleOrder.setOnClickListener(this);
        view.findViewById(R.id.btnOddsExplain).setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, BitmapTool.dp2px(PcddApp.applicationContext, 450));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBetting:
                if(ViewUtil.checkEditEmpty(edPoint, "请输入下注金额"))
                    return;
                if(callback != null) {
                    IBetOdds iBetOdds = (IBetOdds) ((BetPanelPagerAdapter) vpPanel.getAdapter()).getItem(vpPanel.getCurrentItem());
                    callback.onBet(iBetOdds.getSelectedOdds(), Double.parseDouble(edPoint.getText().toString()));
                }
                break;

            case R.id.btnMinPoint:
                IBetOdds iBetOdds = (IBetOdds) ((BetPanelPagerAdapter) vpPanel.getAdapter()).getItem(vpPanel.getCurrentItem());
                edPoint.setText(iBetOdds.getSelectedOdds().min_point+"");
                break;

            case R.id.btnDoublePoint:
                if(ViewUtil.checkEditEmpty(edPoint, "请输入投注金额"))
                    return;
                double point = Double.parseDouble(edPoint.getText().toString());
                edPoint.setText(Arith.mul(point, 2)+"");
                break;

            case R.id.btnOddsExplain:
                Intent it = new Intent(getActivity(), WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, "赔率说明");
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_ODDS_EXPLAIN+"?room_id="+areaId);
                startActivity(it);
                break;

        }
    }

    private BetOddsCallback callback;

    public void setBetOddsCallback(BetOddsCallback callback) {
        this.callback = callback;
    }

    public interface BetOddsCallback {
        public void onBet(GameOddsInfo oddsInfo, double betPoint);
    }
}
