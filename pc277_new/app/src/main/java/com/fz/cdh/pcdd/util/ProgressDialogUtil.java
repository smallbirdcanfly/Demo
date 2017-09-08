package com.fz.cdh.pcdd.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.fz.cdh.pcdd.ui.widget.dialog.CustomProgressDialog;

public class ProgressDialogUtil {

	private static ProgressDialog progressDlg;
	private static CustomProgressDialog progressDialog;
	private static String mMessage;
	public static void showProgressDlg(Context context, String message) {
		/*progressDlg = new ProgressDialog(context);
	//message = TextUtils.isEmpty(message)? "请稍等" : message;
		progressDlg.setMessage(message);
		progressDlg.show();*/
		message = TextUtils.isEmpty(message)? "请稍等..." : message;
		mMessage=message;
		//if (progressDialog == null) {
			progressDialog = CustomProgressDialog.show(context, message, false, 1, null);
		//	return;
		//}
		progressDialog.setMessage(mMessage);
		progressDialog.show();
	}
	
	public static void setCancelable(boolean flag) {
		if(progressDlg != null)
			progressDlg.setCancelable(flag);
	}
	
	public static void dismissProgressDlg() {
		/*if(progressDlg != null)
			progressDlg.dismiss();*/

		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.onDismiss();
			progressDialog.dismiss();
		}
	}
}
