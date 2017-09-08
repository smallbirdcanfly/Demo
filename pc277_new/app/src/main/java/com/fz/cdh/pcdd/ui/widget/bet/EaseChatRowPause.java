package com.fz.cdh.pcdd.ui.widget.bet;

import android.content.Context;
import android.text.Html;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.BettingJson;

/**
 * Created by hang on 2017/2/27.
 * 封盘
 */

public class EaseChatRowPause extends EaseChatRow {

    private TextView tvPauseTip;

    public EaseChatRowPause(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.ease_row_pause, this);
    }

    @Override
    protected void onFindViewById() {
        tvPauseTip = (TextView) findViewById(R.id.tvBetPauseTip);
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

        tvPauseTip.setText(Html.fromHtml(context.getString(R.string.bet_pause_tip, json.game_count+"")));
    }

    @Override
    protected void onBubbleClick() {

    }
}
