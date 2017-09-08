package com.fz.cdh.pcdd.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.ValueSet;
import com.fz.cdh.pcdd.ui.adapter.DialogListAdapter;
import com.fz.cdh.pcdd.ui.base.BaseTopActivity;
import com.fz.cdh.pcdd.util.DateUtil;
import com.fz.cdh.pcdd.util.DialogManager;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.weight.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hang on 2017/2/25.
 * 游戏记录筛选
 */

public class GameRecordFilterActivity extends BaseTopActivity implements View.OnClickListener {

    private TextView tvGameType;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private Dialog gameDialog;
    private List<ValueSet> gameList = new ArrayList<>();

    private int gameType = 0;   //1 北京快8   2 加拿大

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_record_filter);
        init();
    }

    private void init() {
        initTopBar("投注记录");
        gameList.add(new ValueSet("0", "全部"));
        gameList.add(new ValueSet("1", "北京28"));
        gameList.add(new ValueSet("2", "加拿大28"));
        tvGameType = getView(R.id.tvGameType);
        tvStartTime = getView(R.id.tvStartTime);
        tvEndTime = getView(R.id.tvEndTime);
        tvGameType.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
        showDateDlg(tvStartTime,false);
        showDateDlg(tvEndTime,false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tvGameType:
                showTypeDlg();
                break;

            case R.id.tvStartTime:
                showDateDlg(tvStartTime,true);
                break;

            case R.id.tvEndTime:
                showDateDlg(tvEndTime,true);
                break;

            case R.id.btnOK:
                if(!TextUtils.isEmpty(tvStartTime.getText().toString()) || !TextUtils.isEmpty(tvEndTime.getText().toString())) {
                    if(TextUtils.isEmpty(tvStartTime.getText().toString())) {
                        T.showShort("请选择开始时间");
                        return;
                    }
                    if(TextUtils.isEmpty(tvEndTime.getText().toString())) {
                        T.showShort("请选择结束时间");
                        return;
                    }
                    if (DateUtil.compareDay(tvStartTime.getText().toString(), tvEndTime.getText().toString()) < 0) {
                        T.showShort("结束时间不能小于开始时间");
                        return;
                    }
                }

                Intent it = new Intent(this, GameRecordActivity.class);
                it.putExtra("gameType", gameType);
                it.putExtra("startTime", tvStartTime.getText().toString());
                it.putExtra("endTime", tvEndTime.getText().toString());
                startActivity(it);
                break;
        }
    }

    public void showTypeDlg() {
        gameDialog = DialogManager.showListDialog(GameRecordFilterActivity.this,tvGameType ,"选择类型", Gravity.CENTER,0, gameList, new DialogManager.OnCancleClickListener() {
            @Override
            public void onClick() {
                gameDialog.dismiss();
            }
        }, new DialogListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, RecyclerView.Adapter adapter) {
                ValueSet chooseBean = null;
                for (int i = 0; i < gameList.size(); i++) {
                    if (position == i) {
                        gameList.get(i).setChoosed(true);
                        chooseBean = gameList.get(i);
                    } else {
                        gameList.get(i).setChoosed(false);
                    }
                }
                if (!chooseBean.getTypeCode().isEmpty()) {
                    tvGameType.setText(chooseBean.getTypeName());
                }
                gameDialog.dismiss();
            }
        });
    }

    public void showDateDlg(final TextView textView,boolean isShow) {
//        Calendar c = Calendar.getInstance();
//        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                textView.setText(DateUtil.getDate(year, monthOfYear, dayOfMonth));
//            }
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
//                .show();

        TimeSelector beginSelector = new TimeSelector(this,textView ,new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                String[] times = time.split(" ");
                textView.setText(times[0]);
            }
        }, "2000-1-1 00:00:00", "2050-12-31 23:59:59");
        beginSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        if(isShow) {
            beginSelector.show();
        }

    }
}
