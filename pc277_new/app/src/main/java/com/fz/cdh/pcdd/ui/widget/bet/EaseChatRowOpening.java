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

/**
 * Created by hang on 2017/2/27.
 * 开盘
 */

public class EaseChatRowOpening extends EaseChatRow {

    private TextView tvGameCount;

    public EaseChatRowOpening(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.ease_row_opening, this);
    }

    @Override
    protected void onFindViewById() {
        tvGameCount = (TextView) findViewById(R.id.tvGameCount);
    }

    @Override
    protected void setUpBaseView() {
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        BettingJson json = new Gson().fromJson(txtBody.getMessage(), BettingJson.class);
        tvGameCount.setText("["+json.game_count+"]");
    }

    @Override
    protected void onBubbleClick() {

    }
}
