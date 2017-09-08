package com.fz.cdh.pcdd.manager;

import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.exception.DbException;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.ui.LoginActivity;
import com.fz.cdh.pcdd.util.PreferenceUtils;
import com.fz.cdh.pcdd.util.T;

public class UserInfoManager {

	private static final String KEY_LOGIN_STATUS = "login_status";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USER_NAME = "user_name";
	private static final String KEY_USER_PWD = "user_pwd";
	private static final String KEY_LOGIN_TYPE = "login_type";

	public static void setLoginStatus(Context context, boolean isLogin) {
		PreferenceUtils.setPrefBoolean(PcddApp.applicationContext, KEY_LOGIN_STATUS, isLogin);
	}

	public static boolean isLogin(Context context) {
		return PreferenceUtils.getPrefBoolean(PcddApp.applicationContext, KEY_LOGIN_STATUS, false);
	}

	public static void setUserId(Context context, int userId) {
		PreferenceUtils.setPrefInt(PcddApp.applicationContext, KEY_USER_ID, userId);
	}
	
	public static int getUserId(Context context) {
		return PreferenceUtils.getPrefInt(PcddApp.applicationContext, KEY_USER_ID, 0);
	}
	
	public static void setUserName(Context context, String userName) {
		PreferenceUtils.setPrefString(PcddApp.applicationContext, KEY_USER_NAME, userName);
	}
	
	public static String getUserName(Context context) {
		return PreferenceUtils.getPrefString(PcddApp.applicationContext, KEY_USER_NAME, "");
	}
	
	public static void setUserPwd(Context context, String pwd) {
		PreferenceUtils.setPrefString(PcddApp.applicationContext, KEY_USER_PWD, pwd);
	}
	
	public static String getUserPwd(Context context) {
		return PreferenceUtils.getPrefString(PcddApp.applicationContext, KEY_USER_PWD, "");
	}
	
	public static void setLoginType(Context context, int loginType) {
		PreferenceUtils.setPrefInt(PcddApp.applicationContext, KEY_LOGIN_TYPE, loginType);
	}
	
	public static int getLoginType(Context context) {
		return PreferenceUtils.getPrefInt(PcddApp.applicationContext, KEY_LOGIN_TYPE, 0);
	}
	
	public static UserInfo getUserInfo(Context context) {
		try {
			return DBManager.getInstance().getDB().findFirst(UserInfo.class);
		} catch (DbException e) {
			e.printStackTrace();
			return new UserInfo();
		}
	}
	
	public static void saveUserInfo(Context context, final UserInfo userInfo) {
		ThreadPoolManager.execute(new Runnable() {
			@Override
			public void run() {
				try {
					DBManager.getInstance().getDB().deleteAll(UserInfo.class);
					DBManager.getInstance().getDB().save(userInfo);
					setUserId(PcddApp.applicationContext, userInfo.id);
					setUserName(PcddApp.applicationContext, userInfo.account);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void clearUserInfo(Context context) {
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBManager.getInstance().getDB().deleteAll(UserInfo.class);
                    setUserId(PcddApp.applicationContext, 0);
                    setUserPwd(PcddApp.applicationContext, "");
                    setLoginType(PcddApp.applicationContext, 0);
                    setLoginStatus(PcddApp.applicationContext, false);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
	}

	public static boolean toLogin(Context context) {
		if(!UserInfoManager.isLogin(PcddApp.applicationContext)) {
			T.showShort("您当前未登录，请先登录");
			context.startActivity(new Intent(context, LoginActivity.class));
			return true;
		}
		return false;
	}
}
