package com.fz.cdh.pcdd.manager;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.fz.cdh.pcdd.MainActivity;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.util.T;

public class IMManager {
	
	protected static final String TAG = "IMManager";
	
	private static IMManager instance;
	
	private Context applicationContext;
	
	private EMClient emClient;
	private EaseUI easeUI;
	
	private IMManager() {
		
	}

	public static synchronized IMManager getInstance() {
		if(instance  == null)
			instance = new IMManager();
		return instance;
	}
	
	public void init(Context context) {
		this.applicationContext = context;
		emClient = EMClient.getInstance();
		easeUI = EaseUI.getInstance();
		
		EMOptions options = getChatoptions();
		emClient.init(applicationContext, options);
		easeUI.init(applicationContext, options);
		
		//调用easeui的api设置providers
	    setEaseUIProviders();
        //注册消息事件监听
	    setListener();
	}
	
	protected void setEaseUIProviders() {
		//需要easeui库显示用户头像和昵称设置此provider
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                //获取user信息，demo是从内存的好友列表里获取，
                //实际开发中，可能还需要从服务器获取用户信息,
                //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
                return EaseUserManager.getEaseUser(username);
            }
        });

		easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
			@Override
			public String getDisplayedText(EMMessage message) {
				return null;
			}

			@Override
			public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
				return null;
			}

			@Override
			public String getTitle(EMMessage message) {
				return null;
			}

			@Override
			public int getSmallIcon(EMMessage message) {
				return 0;
			}

			@Override
			public Intent getLaunchIntent(EMMessage message) {
				return null;
			}
		});
	}
	
	private EMOptions getChatoptions(){
	    //easeui库默认设置了一些options，可以覆盖
	    EMOptions options = new EMOptions();
	    return options;
	}
	
    /**
	 * 获取消息通知类
	 * @return
	 */
	public EaseNotifier getNotifier(){
	    return easeUI.getNotifier();
	}
	
	public void setListener() {
		emClient.addConnectionListener(new EMConnectionListener() {
			@Override
			public void onDisconnected(final int error) {
				switch(error) {
				case EMError.USER_REMOVED:
					T.showOnThread("您的账号已被删除，请退出重新注册", true);
					UserInfoManager.setLoginStatus(applicationContext, false);
					Intent it = new Intent(PcddApp.applicationContext, MainActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					PcddApp.applicationContext.startActivity(it);
					break;
					
				case EMError.USER_LOGIN_ANOTHER_DEVICE:
					T.showOnThread("您的账号被迫下线，请退出重新登录", true);
					UserInfoManager.setLoginStatus(applicationContext, false);
					Intent it2 = new Intent(PcddApp.applicationContext, MainActivity.class);
					it2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					PcddApp.applicationContext.startActivity(it2);
					break;
				}
			}
			
			@Override
			public void onConnected() {
			}
		});
	}
}
