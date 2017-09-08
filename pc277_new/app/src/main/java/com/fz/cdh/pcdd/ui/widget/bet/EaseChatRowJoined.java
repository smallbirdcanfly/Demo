package com.fz.cdh.pcdd.ui.widget.bet;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.BettingJson;

/**
 * Created by hang on 2017/2/27.
 * 新成员加入
 */

public class EaseChatRowJoined extends EaseChatRow {

    //进出房间
    private ImageView ivLevelThumb;
    private TextView tvJoinedMember;

    private int[] levelThumbRes = {
            0,
            R.drawable.ic_level_1,
            R.drawable.ic_level_2,
            R.drawable.ic_level_3,
            R.drawable.ic_level_4,
            R.drawable.ic_level_5,
            R.drawable.ic_level_6,
    };

    public EaseChatRowJoined(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.ease_row_member_joined, this);
    }

    @Override
    protected void onFindViewById() {
        ivLevelThumb = (ImageView) findViewById(R.id.ivLevelThumb);
        tvJoinedMember = (TextView) findViewById(R.id.tvJoinedMember);
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

        tvJoinedMember.setText(json.nick_name);
        if(json.level>0 && json.level<=6) {
            ivLevelThumb.setVisibility(VISIBLE);
            ivLevelThumb.setImageResource(levelThumbRes[json.level]);
        } else {
            ivLevelThumb.setVisibility(GONE);
        }
    }

    @Override
    protected void onBubbleClick() {
    }
}
