package com.fz.cdh.pcdd.ui.widget.bet;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.BettingJson;
import com.fz.cdh.pcdd.manager.ImageLoadManager;

/**
 * Created by hang on 2017/2/21.
 * 投注
 */

public class EaseChatRowBet extends EaseChatRow {

    //下注
    private EaseImageView eivAvatar;
    private TextView tvNickname;
    private TextView tvGameCount;
    private TextView tvBetType;
    private TextView tvBetPoint;
    private ImageView ivLevelLabel;

    private int[] levelThumbRes = {
            0,
            R.drawable.label_level_1,
            R.drawable.label_level_2,
            R.drawable.label_level_3,
            R.drawable.label_level_4,
            R.drawable.label_level_5,
            R.drawable.label_level_6,
    };

    public EaseChatRowBet(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_bet : R.layout.ease_row_sent_bet, this);
    }

    @Override
    protected void onFindViewById() {
        //下注
        tvGameCount = (TextView) findViewById(R.id.tvGameCount);
        tvBetType = (TextView) findViewById(R.id.tvBetType);
        tvBetPoint = (TextView) findViewById(R.id.tvBetPoint);
        eivAvatar = (EaseImageView) findViewById(R.id.iv_userhead);
        tvNickname = (TextView) findViewById(R.id.tv_userid);
        ivLevelLabel = (ImageView) findViewById(R.id.ivLevelLabel);
    }

    @Override
    protected void onUpdateView() {
    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        BettingJson json = new Gson().fromJson(txtBody.getMessage(), BettingJson.class);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnFail(R.drawable.ease_default_avatar)
                .showImageOnLoading(R.drawable.ease_default_avatar)
                .showImageForEmptyUri(R.drawable.ease_default_avatar)
                .build();
        ImageLoadManager.getInstance().displayImage(json.user_photo, eivAvatar, options);
        tvNickname.setText(json.nick_name);
        tvGameCount.setText(json.game_count+"期");
        tvBetType.setText("投注类型："+json.game_type);
        tvBetPoint.setText("金额："+json.point);

        if(json.level>0 && json.level<=6) {
            ivLevelLabel.setVisibility(VISIBLE);
            ivLevelLabel.setImageResource(levelThumbRes[json.level]);
        } else {
            ivLevelLabel.setVisibility(GONE);
        }

        handleTextMessage();
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onBubbleClick() {

    }
}
