package com.fz.cdh.pcdd.manager;

import android.os.AsyncTask;

import com.hyphenate.easeui.domain.EaseUser;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.fz.cdh.pcdd.entity.MessageEvent;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.bean.ImUserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class EaseUserManager {
	
	public static List<EaseUser> cacheContactList = new ArrayList<EaseUser>();

	public static EaseUser getEaseUser(final String username) {
		EaseUser user = getEaseUserFromCache(username);
		if(user == null) {
			user = getEaseUserFromFromDisk(username);
			if(user == null)
				user = new EaseUser(username);
			getEaseUserFromNet(user);
		}
		return user;
	}
	
	public static EaseUser getEaseUserFromCache(String username) {
		for(EaseUser item : cacheContactList) {
			if(item.getUsername().equals(username))
				return item;
		}
		return null;
	}
	
	/**
	 * 从数据库查找用户
	 */
	public static EaseUser getEaseUserFromFromDisk(String username) {
		DbUtils db = DBManager.getInstance().getDB();
		try {
			return db.findFirst(Selector.from(EaseUser.class).where("username", "=", username));
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void getEaseUserFromNet(final EaseUser user) {
//		IMUserListRequest req = new IMUserListRequest();
//		req.account_type = "1";
//		req.im_account = user.getUsername();
//		RequestParams params = new RequestParams("utf-8");
//		try {
//			params.setBodyEntity(new StringEntity(req.toJson(), "utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		new HttpUtils().send(HttpMethod.POST, Api.IM_USER, params, new RequestCallBack<String>() {
//			@Override
//			public void onFailure(HttpException arg0, String arg1) {
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> resp) {
//				ImUserListResponse bean = new Gson().fromJson(resp.result, ImUserListResponse.class);
//				if(Api.SUCCEED == bean.result_code && bean.data.size()>0) {
//					ImUserInfo item = bean.data.get(0);
//					if(item != null) {
//						putEaseUser(item);
//					}
//				}
//			}
//		});
	}
	
	public static void putEaseUser(final ImUserInfo info) {
		new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... strings) {
				EaseUser easeUser = new EaseUser(info.im_account);
				easeUser.setNick(info.user_name);
				easeUser.setAvatar(ApiInterface.HOST+info.user_photo);
				
				for(EaseUser item : cacheContactList) {
					if(item.getUsername().equals(info.im_account)) {
						cacheContactList.remove(item);
						break;
					}
				}
				cacheContactList.add(easeUser);
				
				EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_REFRESH, 0, null));
				
				DbUtils db = DBManager.getInstance().getDB();
				try {
					EaseUser temp = db.findFirst(Selector.from(EaseUser.class).where("username", "=", info.im_account));
					if(temp != null)
						db.delete(temp);
					db.save(easeUser);
				} catch (DbException e) {
					e.printStackTrace();
				}
				return "";
			}
		}.execute();
	}
}
