package com.fz.cdh.pcdd.ui.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.manager.UserInfoManager;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.FileUtil;
import com.fz.cdh.pcdd.util.PictureUtil;
import com.fz.cdh.pcdd.util.T;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PickPhotoWindow extends CommonDialog implements OnClickListener {
	
	public static final int REQUEST_TAKE_CAMERA = 1;
	public static final int REQUEST_PICK_LOCAL = 2;

	private Button btnPhoto, btnAlbum, btnCancel;
	
	private Context context;
	private Fragment fragment;
	
	private PhotoUploadCallback upCallback;

	public PickPhotoWindow(Context context) {
		super(context, R.layout.window_modify_avatar, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.context = context;
	}

	@Override
	public void initDlgView() {
		btnPhoto = (Button) dlgView.findViewById(R.id.btnAvatarPhoto);
		btnAlbum = (Button) dlgView.findViewById(R.id.btnAvatarAlbum);
		btnCancel = (Button) dlgView.findViewById(R.id.btnAvatarCancel);
		
		btnPhoto.setOnClickListener(this);
		btnAlbum.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnAvatarPhoto:
			takePhoto();
			dismiss();
			break;
			
		case R.id.btnAvatarAlbum:
			pickAlbum();
			dismiss();
			break;
			
		case R.id.btnAvatarCancel:
			dismiss();
			break;
		}
	}
	
	public void setFragmentContext(Fragment fragment) {
		this.fragment = fragment;
	}
	
	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(fragment != null)
			fragment.startActivityForResult(intent, REQUEST_TAKE_CAMERA);
		else
			((Activity)context).startActivityForResult(intent, REQUEST_TAKE_CAMERA);
	}
	
	public void pickAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		if(fragment != null)
			fragment.startActivityForResult(intent, REQUEST_PICK_LOCAL);
		else
			((Activity)context).startActivityForResult(intent, REQUEST_PICK_LOCAL);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && data != null) {
			if (requestCode == PickPhotoWindow.REQUEST_PICK_LOCAL || requestCode == PickPhotoWindow.REQUEST_TAKE_CAMERA) {
				// 修改头像
				String path = FileUtil.getPath((Activity) context, data.getData());
				if (path == null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bmp = extras.getParcelable("data");
						if (bmp != null) {
							upPhoto(BitmapTool.Bitmap2File(context, bmp));
						}
					}
				} else {
					upPhoto(new File(path));
				}
			}
		}
	}
	
	public void upPhoto(File f) {
		try {
            // 压缩图片
            String compressPath = PictureUtil.compressImage(context, f.getPath(), f.getName(), 95);
            f = new File(compressPath);
        } catch(Exception e) {
            e.printStackTrace();
        }
		
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", f.getName(), requestFile);
        RequestBody busiType = RequestBody.create(MediaType.parse("multipart/form-data"), UserInfoManager.getUserId(context)+"");
		HttpResultCallback<String> callback = new HttpResultCallback<String>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				T.showShort(e.getMessage());
			}

			@Override
			public void onNext(String string) {
				if(upCallback != null)
					upCallback.uploadSucceed(string);
			}
		};
		MySubcriber s = new MySubcriber(context, callback, true, "");
		ApiInterface.uploadFile(body, busiType, s);
	}
	
	public void setPhotoUploadCallback(PhotoUploadCallback callback) {
		this.upCallback = callback;
	}
	
	public interface PhotoUploadCallback {
		public void uploadSucceed(String fileUrl);
	}
}
