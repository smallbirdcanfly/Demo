package com.fz.cdh.pcdd.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.ui.AboutActivity;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.DialogManager;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.Utility;

import java.io.File;

/**
 * Created by hang on 2017/3/29.
 */

public class ProxyShareFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivQrCode;

    public String shareUrl;

    private String savePath;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_proxy_share;
    }

    @Override
    protected void init(View rootView) {
        ivQrCode = getView(R.id.ivQrCode);

        ivQrCode.setImageBitmap(BitmapTool.createQRCodeBitmap(shareUrl));
        setText(R.id.tvShareUrl, shareUrl);
        setText(R.id.tvShareId, UserInfoManager.getUserId(activity)+"");

        getView(R.id.btnSaveQrCode).setOnClickListener(this);
        getView(R.id.tvCopyUrl).setOnClickListener(this);

        savePath = Environment.getExternalStorageDirectory() + File.separator + "share.png";
        ivQrCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap=((BitmapDrawable)ivQrCode.getDrawable()).getBitmap();
                DialogManager.showChoosePicDialog(getActivity(),bitmap, savePath);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveQrCode:
                Bitmap bitmap=((BitmapDrawable)ivQrCode.getDrawable()).getBitmap();
                DialogManager.showChoosePicDialog(getActivity(),bitmap, savePath);
                break;
            case R.id.tvCopyUrl:
                Utility.copy(activity, shareUrl);
                T.showShort("已复制");
                break;
        }
    }
}
