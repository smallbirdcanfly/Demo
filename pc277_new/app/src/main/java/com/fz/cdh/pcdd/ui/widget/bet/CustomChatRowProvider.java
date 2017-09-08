package com.fz.cdh.pcdd.ui.widget.bet;

import android.content.Context;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.fz.cdh.pcdd.entity.BettingJson;

/**
 * Created by hang on 2017/2/21.
 */

public class CustomChatRowProvider implements EaseCustomChatRowProvider {

    private static final int ROW_BET_RECEIVE = 1;
    private static final int ROW_BET_SENT = 2;
    private static final int ROW_JOINED = 3;
    private static final int ROW_OPENING = 4;
    private static final int ROW_OPEN_INFO = 5;
    private static final int ROW_PAUSE = 6;

    private int rowTypeCount = 6;

    private Context context;

    public CustomChatRowProvider(Context context) {
        this.context = context;
    }

    @Override
    public int getCustomChatRowTypeCount() {
        return rowTypeCount;
    }

    @Override
    public int getCustomChatRowType(EMMessage message) {
        if(message.getType() == EMMessage.Type.TXT) {
            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
            BettingJson json = new Gson().fromJson(txtBody.getMessage(), BettingJson.class);
            switch (json.notice_type) {
                case 1:
                    //进出房间
                    return ROW_JOINED;
                case 2:
                    //下注
                    return message.direct() == EMMessage.Direct.RECEIVE ? ROW_BET_RECEIVE : ROW_BET_SENT;
                case 3:
                    //开奖信息
                    return ROW_OPEN_INFO;
                case 4:
                    //封盘信息
                    return ROW_PAUSE;
                case 5:
                    //开盘
                    return ROW_OPENING;
                default:
                    return message.direct() == EMMessage.Direct.RECEIVE ? ROW_BET_RECEIVE : ROW_BET_SENT;
            }
        }
        return 0;
    }

    @Override
    public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
        switch(getCustomChatRowType(message)) {
            case ROW_JOINED:
                return new EaseChatRowJoined(context, message, position, adapter);
            case ROW_BET_RECEIVE:
            case ROW_BET_SENT:
                return new EaseChatRowBet(context, message, position, adapter);
            case ROW_OPENING:
                return new EaseChatRowOpening(context, message, position, adapter);
            case ROW_OPEN_INFO:
                return new EaseChatRowOpenInfo(context, message, position, adapter);
            case ROW_PAUSE:
                return new EaseChatRowPause(context, message, position, adapter);
        }
        return null;
    }
}
