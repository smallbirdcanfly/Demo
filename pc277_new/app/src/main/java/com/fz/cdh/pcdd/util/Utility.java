package com.fz.cdh.pcdd.util;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

public class Utility {
    public static void hideSoftInput(Activity context) {
		if(context.getCurrentFocus() != null) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
    
    public static String getPath(Activity context, Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
	}
    
    public static String replaceString(String str, String c, int begin, int end) {
    	if(TextUtils.isEmpty(str))
    		return null;
    	
    	if(begin>end || begin<0 || end>str.length())
    		return str;
    	
    	StringBuilder sb = new StringBuilder(str.substring(0, begin));
    	int n = end - begin+1;
    	for(int i=0; i<n; i++) {
    		sb.append(c);
    	}
    	sb.append(str.substring(end+1, str.length()));
    	return sb.toString();
    }
    
    /**
     * 按“，”拆分字符串，返回第一个字符串
     */
    public static String getFirstString(String str) {
    	if(TextUtils.isEmpty(str))
    		return "";
    	
    	String[] array = str.split(",");
    	return array[0];
    }
    
    public static void copy(Context context, String str) {
    	ClipboardManager board = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        board.setText(str);
    }
    
    public static void call(Context context, String tel) {
    	context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel)));
    }
    
    public static void openQQChat(Context context, String qq) {
    	String url="mqqwpa://im/chat?chat_type=wpa&uin="+qq;
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(it);
    }
}
