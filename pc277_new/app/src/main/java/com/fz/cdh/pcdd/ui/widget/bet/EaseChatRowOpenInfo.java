package com.fz.cdh.pcdd.ui.widget.bet;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.BettingJson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hang on 2017/2/27.
 */

public class EaseChatRowOpenInfo extends EaseChatRow {

    private TextView tvGameTime;
    private TextView tvOpeningTime;
    private TextView tvOpeningDesc;

    public EaseChatRowOpenInfo(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.ease_row_open_info, this);
    }

    @Override
    protected void onFindViewById() {
        tvGameTime = (TextView) findViewById(R.id.tvGameCount);
        tvOpeningTime = (TextView) findViewById(R.id.tvOpeningTime);
        tvOpeningDesc = (TextView) findViewById(R.id.tvOpeningDesc);
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void setUpBaseView() {
    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        BettingJson json = new Gson().fromJson(txtBody.getMessage(), BettingJson.class);

        tvGameTime.setText("["+json.game_count+"]");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        tvOpeningTime.setText("开奖["+ format.format(new Date(json.create_time))+"]:");
        tvOpeningDesc.setText(json.ext_content);
    }

    @Override
    protected void onBubbleClick() {

    }
}
