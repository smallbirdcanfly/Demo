package com.fz.cdh.pcdd.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import java.io.File;

public class ApkUtil {
	
	/**
	 * 瀹夎apk
	 */
	public static void installAPK(Context context, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	 * 瀹夎apk
	 */
	public static void installAPK(Context context, File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		((Activity) context).startActivityForResult(intent, 0);
	}
	
	/**
	 * 鍗歌浇apk
	 * @param packageName 鍖呭悕
	 */
	public static void uninstallAPKDialog(final Context context, final String packageName){ 
        new AlertDialog.Builder(context).setTitle("鎻愮ず").setIcon(android.R.drawable.ic_dialog_info)
		.setMessage("鏄惁纭畾瑕佸嵏杞借搴旂敤锛�").setPositiveButton("纭畾", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Uri uri=Uri.parse("package:"+packageName);  
		        Intent intent=new Intent(Intent.ACTION_DELETE,uri);  
		        context.startActivity(intent);
			}
		}).setNegativeButton("鍙栨秷", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
    }
	
	/**
	 * 鍗歌浇apk
	 * @param packageName 鍖呭悕
	 */
	public static void uninstallAPK(final Context context, final String packageName){ 
		Uri uri=Uri.parse("package:"+packageName);  
        Intent intent=new Intent(Intent.ACTION_DELETE,uri);  
        context.startActivity(intent);
	}
	
	/**
	 * 鏍规嵁鍖呭悕鍚姩搴旂敤绋嬪簭
	 * @param context
	 * @param packageName
	 */
	public static boolean startApp(final Context context, final String packageName){
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		if(intent == null)
			return false;
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return true;
	}
	
	/**
	 * 鏍规嵁鍖呭悕鍚姩搴旂敤绋嬪簭
	 * @param context
	 * @param packageName
	 */
	public static boolean startAppForResult(final Context context, final String packageName, final int requestCode){
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		if(intent == null)
			return false;
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Activity)context).startActivityForResult(intent, requestCode);
		return true;
	}
	
	/**
	 * 鏍规嵁鍖呭悕鍚姩搴旂敤绋嬪簭锛堥檮甯︽暟鎹級
	 * @param context
	 * @param packageName
	 */
	public static boolean startApp(final Context context, final String packageName, Bundle data){
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		if(intent == null)
			return false;
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("data", data);
		context.startActivity(intent);
		return true;
	}
	
	/**
	 * 鍒ゆ柇apk鏄惁宸茬粡瀹夎
	 * @param packageName
	 * @return
	 */
	public static boolean checkIsInstall(PackageManager pm, String packageName){
		PackageInfo packageInfo;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            return false;
        }else{
            return true;
        }
	}
	
	/**
	 * 楠岃瘉APK鏂囦欢瀹屾暣鎬�
	 */
	public static boolean verifyApkFile(Context context, String path) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			return info==null? false : true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 鏄剧ず搴旂敤璇︽儏
	 */
	public static void showAppDetails(Context context, String pkgName) {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", pkgName, null);
		intent.setData(uri);
		context.startActivity(intent);
	}
	
	public static String getVersionCode(Context context) throws NameNotFoundException {
		return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
	}
}
