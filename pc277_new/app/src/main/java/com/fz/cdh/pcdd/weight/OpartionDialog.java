package com.fz.cdh.pcdd.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;


/**
 * 充值界面的弹框
 *
 * @author xiaoge
 */
public class OpartionDialog extends Dialog {

    private Context mContext;
    private TextView tv_sure;
    private TextView tv_title;
    private TextView tv_content;


    private String mTitle;
    private String mContent;
    private String mType;
    private  OnOptionListener mOnOptionListener;
    public OpartionDialog(Context context,String title,String contenr,String type,OnOptionListener onOptionListener) {
        super(context, R.style.myDialogTheme);
        this.mContext = context;
        this.mTitle = title;
        this.mContent = contenr;
        this.mType = type;
        this.mOnOptionListener=onOptionListener;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_op);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title.setText(mTitle);
        tv_content.setText(mContent);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnOptionListener){
                    mOnOptionListener.onOption();
                }
                dismiss();
            }
        });


    }


public interface OnOptionListener{
    void onOption();
}
}
