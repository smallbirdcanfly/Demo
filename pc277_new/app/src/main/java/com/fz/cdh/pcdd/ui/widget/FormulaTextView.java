package com.fz.cdh.pcdd.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;

/**
 * Created by hang on 2017/3/8.
 */

public class FormulaTextView extends LinearLayout {

    private Context context;

    private View view;
    private TextView[] tvNum;
    private TextView tvResult;

    private int[] bgRes = {R.drawable.circle_orange,
                        R.drawable.circle_red,
                        R.drawable.circle_green,
                        R.drawable.circle_blue,
                        R.drawable.circle_gray};

    public FormulaTextView(Context context) {
        super(context);
        init(context);
    }

    public FormulaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormulaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_formula_textview, this, true);
        tvNum = new TextView[3];
        tvNum[0] = (TextView) view.findViewById(R.id.tvNum1);
        tvNum[1] = (TextView) view.findViewById(R.id.tvNum2);
        tvNum[2] = (TextView) view.findViewById(R.id.tvNum3);
        tvResult = (TextView) view.findViewById(R.id.tvResult);
    }

    public void setText(String text, int colorType) {
        tvNum[0].setText("?");
        tvNum[1].setText("?");
        tvNum[2].setText("?");
        tvResult.setText("?");

        if(TextUtils.isEmpty(text))
            return;

        String[] str = text.split("=");
        String result = str[1];
        String[] nums = str[0].split("\\+");

        for(int i=0; i<nums.length; i++) {
            tvNum[i].setText(nums[i]);
        }

        tvResult.setText(result);
       // tvResult.setBackgroundResource(bgRes[colorType]);
    }
}
