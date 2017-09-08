package com.fz.cdh.pcdd.network;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.network.bean.AccountRecordInfo;
import com.fz.cdh.pcdd.network.bean.BackwaterInfo;
import com.fz.cdh.pcdd.network.bean.BannerInfo;
import com.fz.cdh.pcdd.network.bean.BetDetailInfo;
import com.fz.cdh.pcdd.network.bean.CancelOrderResult;
import com.fz.cdh.pcdd.network.bean.EarningInfo;
import com.fz.cdh.pcdd.network.bean.GameRecordInfo;
import com.fz.cdh.pcdd.network.bean.GameTypeInfo;
import com.fz.cdh.pcdd.network.bean.GiftInfo;
import com.fz.cdh.pcdd.network.bean.GiftLogInfo;
import com.fz.cdh.pcdd.network.bean.HomeDataInfo;
import com.fz.cdh.pcdd.network.bean.KefuInfo;
import com.fz.cdh.pcdd.network.bean.KefuQAInfo;
import com.fz.cdh.pcdd.network.bean.LatestChoiceInfo;
import com.fz.cdh.pcdd.network.bean.NoticeInfo;
import com.fz.cdh.pcdd.network.bean.OrderInfo;
import com.fz.cdh.pcdd.network.bean.ProxyRuleInfo;
import com.fz.cdh.pcdd.network.bean.RechargeAccountInfo;
import com.fz.cdh.pcdd.network.bean.RechargeLogInfo;
import com.fz.cdh.pcdd.network.bean.RechargeRecordInfo;
import com.fz.cdh.pcdd.network.bean.RoomInfo;
import com.fz.cdh.pcdd.network.bean.RoomLevelInfo;
import com.fz.cdh.pcdd.network.bean.ShareParamsInfo;
import com.fz.cdh.pcdd.network.bean.UserInfo;
import com.fz.cdh.pcdd.network.bean.VCodeInfo;
import com.fz.cdh.pcdd.network.bean.VersionInfo;
import com.fz.cdh.pcdd.network.bean.WithdrawInfo;
import com.fz.cdh.pcdd.network.request.AccountListRequest;
import com.fz.cdh.pcdd.network.request.AccountRecordRequest;
import com.fz.cdh.pcdd.network.request.BackWaterListRequest;
import com.fz.cdh.pcdd.network.request.BannerListRequest;
import com.fz.cdh.pcdd.network.request.BaseRequest;
import com.fz.cdh.pcdd.network.request.BettingRequest;
import com.fz.cdh.pcdd.network.request.BindBankRequest;
import com.fz.cdh.pcdd.network.request.BindMobileRequest;
import com.fz.cdh.pcdd.network.request.CancelOrderRequest;
import com.fz.cdh.pcdd.network.request.DuobaoPayRequest;
import com.fz.cdh.pcdd.network.request.EditUserInfoRequest;
import com.fz.cdh.pcdd.network.request.ExchangeGiftRequest;
import com.fz.cdh.pcdd.network.request.ExitRoomRequest;
import com.fz.cdh.pcdd.network.request.GameRecordRequest;
import com.fz.cdh.pcdd.network.request.GameTypeDataRequest;
import com.fz.cdh.pcdd.network.request.GetLatestChoiceDataRequest;
import com.fz.cdh.pcdd.network.request.GiftListRequest;
import com.fz.cdh.pcdd.network.request.HomeDataRequest;
import com.fz.cdh.pcdd.network.request.JoinRoomRequest;
import com.fz.cdh.pcdd.network.request.LoginOtherRequest;
import com.fz.cdh.pcdd.network.request.LoginRequest;
import com.fz.cdh.pcdd.network.request.ModifyUserInfoRequest;
import com.fz.cdh.pcdd.network.request.NoticeListRequest;
import com.fz.cdh.pcdd.network.request.PayCheckRequest;
import com.fz.cdh.pcdd.network.request.RechargeLogRequest;
import com.fz.cdh.pcdd.network.request.RechargeOfflineRequest;
import com.fz.cdh.pcdd.network.request.RechargeRecordRequest;
import com.fz.cdh.pcdd.network.request.RechargeRequest;
import com.fz.cdh.pcdd.network.request.RegisterRequest;
import com.fz.cdh.pcdd.network.request.ResetLoginPwdRequest;
import com.fz.cdh.pcdd.network.request.RoomBetDetailRequest;
import com.fz.cdh.pcdd.network.request.RoomLevelListRequest;
import com.fz.cdh.pcdd.network.request.RoomListRequest;
import com.fz.cdh.pcdd.network.request.VCodeRequest;
import com.fz.cdh.pcdd.network.request.VersionRequest;
import com.fz.cdh.pcdd.network.request.WithdrawRecordRequest;
import com.fz.cdh.pcdd.network.request.WithdrawRequest;
import com.fz.cdh.pcdd.network.response.ArrayResponse;
import com.fz.cdh.pcdd.network.response.ObjectResponse;
import com.fz.cdh.pcdd.network.response.WithdrawRecordResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApiInterface {
	
	public static int SUCCEED = 0;

	/*public static String HOST = "http://125.88.181.58:50001/";
	public static String BASE_URL = HOST + "pcdd-api-client-interface/";*/
	//public static String HOST = "http://47.94.216.214:8080/";
	//198.44.250.252:8080
	public static String HOST = "http://198.44.250.252:8080/";
	//public static String HOST = "http://192.168.1.104:8080/";
	public static String BASE_URL = HOST + "pcdd_interface/";
	public static String WAP_LOTTERY_RULE = HOST + "pcdd_cms/lottery/index.html";
    public static String WAP_BACKWATER_RULE = HOST + "pcdd-wap/views/module/index/huishui.html";
	public static String WAP_NOTICE_DETAIL = HOST + "pcdd-wap/views/module/index/noticeDetails.html";
	public static String WAP_BEIJING28_EXPLAIN = HOST + "pcdd-wap/views/module/index/beijingDetails.html";
	public static String WAP_CANADA28_EXPLAIN = HOST + "pcdd-wap/views/module/index/jndDetails.html";
	public static String WAP_TREND_CHART = HOST + "pcdd-wap/views/module/index/zoushi.html";
	public static String WAP_BANNER_DETAIL = HOST + "pcdd-wap/views/module/index/bannerDetails.html";
	public static String WAP_CUS_SVR = "http://kefu.easemob.com/webim/im.html?tenantId=36922";
    public static String WAP_ODDS_EXPLAIN = HOST + "pcdd-wap/views/module/index/peilv.html";
    public static String WAP_USER_AGREEMENT = HOST + "pcdd-wap/views/module/index/yonghuXieyi.html";
	public static String WAP_PAY_MO_BAO = HOST + "MobaoPayExample/pay.jsp";
	public static void uploadFile(MultipartBody.Part fileBody, RequestBody paramsBody, MySubcriber<String> s) {
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createUpload(fileBody, paramsBody).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	/**
	 * 切换环境
	 */

	public  static void setSwitchHost(String host){
		HOST=host;
		  BASE_URL = HOST + "pcdd_interface/";
		  WAP_LOTTERY_RULE = HOST + "pcdd_cms/lottery/index.html";
		  WAP_BACKWATER_RULE = HOST + "pcdd-wap/views/module/index/huishui.html";
		  WAP_NOTICE_DETAIL = HOST + "pcdd-wap/views/module/index/noticeDetails.html";
		  WAP_BEIJING28_EXPLAIN = HOST + "pcdd-wap/views/module/index/beijingDetails.html";
		  WAP_CANADA28_EXPLAIN = HOST + "pcdd-wap/views/module/index/jndDetails.html";
		  WAP_TREND_CHART = HOST + "pcdd-wap/views/module/index/zoushi.html";
		  WAP_BANNER_DETAIL = HOST + "pcdd-wap/views/module/index/bannerDetails.html";
		  WAP_CUS_SVR = "http://kefu.easemob.com/webim/im.html?tenantId=36922";
		  WAP_ODDS_EXPLAIN = HOST + "pcdd-wap/views/module/index/peilv.html";
		  WAP_USER_AGREEMENT = HOST + "pcdd-wap/views/module/index/yonghuXieyi.html";
		  WAP_PAY_MO_BAO = HOST + "MobaoPayExample/pay.jsp";
			RetrofitHelper.getInstance(true);

	}

	/** 首页数据 */
	public static void getHomeData(HomeDataRequest req, MySubcriber<HomeDataInfo> subcriber) {
		Encrypt.encrypt(req);
		Observable observable = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createHomeData(req).map(new ObjectRespFunc<HomeDataInfo>());
		toSubscribe(observable, subcriber);
	}

    /** 广告列表 */
    public static void getBannerList(BannerListRequest req, MySubcriber<List<BannerInfo>> subcriber) {
		Encrypt.encrypt(req);
        Observable observable = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createBannerList(req).map(new ArrayRespFunc<BannerInfo>());
        toSubscribe(observable, subcriber);
	}

	public static void register(RegisterRequest req, MySubcriber<UserInfo> subcriber) {
		Encrypt.encrypt(req);
		Observable observable = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRegister(req).map(new ObjectRespFunc<UserInfo>());
		toSubscribe(observable, subcriber);
	}

	public static void login(LoginRequest req, MySubcriber<UserInfo> subcriber) {
		Encrypt.encrypt(req);
		Observable observable = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createLogin(req).map(new ObjectRespFunc<UserInfo>());
		toSubscribe(observable, subcriber);
	}

	public static void loginOther(LoginOtherRequest req, MySubcriber<UserInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createLoginOther(req).map(new ObjectRespFunc<UserInfo>());
		toSubscribe(o, s);
	}

	public static void getUserInfo(BaseRequest req, MySubcriber<UserInfo> subcriber) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createUserInfo(req).map(new ObjectRespFunc<UserInfo>());
		toSubscribe(o, subcriber);
	}

	public static void modifyUserInfo(ModifyUserInfoRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createModifyUserInfo(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void getNoticeList(NoticeListRequest req, MySubcriber<List<NoticeInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createNoticeList(req).map(new ArrayRespFunc<NoticeInfo>());
		toSubscribe(o, s);
	}

	public static void getRoomLevelList(RoomLevelListRequest req, MySubcriber<List<RoomLevelInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRoomLevelList(req).map(new ArrayRespFunc<RoomLevelInfo>());
		toSubscribe(o, s);
	}

	public static void getRoomList(RoomListRequest req, MySubcriber<List<RoomInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRoomList(req).map(new ArrayRespFunc<RoomInfo>());
		toSubscribe(o, s);
	}

	public static void joinRoom(JoinRoomRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createJoinRoom(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void exitRoom(ExitRoomRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createExitRoom(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void getGameTypeData(GameTypeDataRequest req, MySubcriber<GameTypeInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createGameTypeData(req).map(new ObjectRespFunc<GameTypeInfo>());
		toSubscribe(o, s);
	}
	public static void getLatestChoiceData(GetLatestChoiceDataRequest req, MySubcriber<List<LatestChoiceInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.getLatestChoiceData(req).map(new ArrayRespFunc<LatestChoiceInfo>());
		toSubscribe(o, s);
	}

	/**
	 * 撤单
	 * @param req
	 * @param s
	 */
	public static void cancelOrder(CancelOrderRequest req, MySubcriber<CancelOrderResult> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.cancleOrder(req).map(new ObjectRespFunc<CancelOrderResult>());
		toSubscribe(o, s);
	}
	public static void getBetDetail(RoomBetDetailRequest req, MySubcriber<BetDetailInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createBetDetail(req).map(new ObjectRespFunc<BetDetailInfo>());
		toSubscribe(o, s);
	}

	public static void betting(BettingRequest req, MySubcriber<String> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createBetting((req)).map(new ObjectRespFunc<String>());
        toSubscribe(o, s);
    }

    public static void getAccountRecord(AccountRecordRequest req, MySubcriber<List<AccountRecordInfo>> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createAccountRecord(req).map(new ArrayRespFunc<AccountRecordInfo>());
        toSubscribe(o, s);
    }

    public static void getGameRecord(GameRecordRequest req, MySubcriber<List<GameRecordInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createGameRecord(req).map(new ArrayRespFunc<GameRecordInfo>());
		toSubscribe(o, s);
	}

	public static void getRechargeRecord(RechargeRecordRequest req, MySubcriber<List<RechargeRecordInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRechargeRecord(req).map(new ArrayRespFunc<RechargeRecordInfo>());
		toSubscribe(o, s);
	}

	public static void getWithdrawRecord(WithdrawRecordRequest req, MySubcriber<WithdrawRecordResponse> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createWithdrawRecord(req).map(new ObjectRespFunc<WithdrawRecordResponse>());
		toSubscribe(o, s);
	}

	public static void getBackWaterList(BackWaterListRequest req, MySubcriber<List<BackwaterInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createBackWaterList(req).map(new ArrayRespFunc<BackwaterInfo>());
		toSubscribe(o, s);
	}

	public static void editUserInfo(EditUserInfoRequest req, MySubcriber<String> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createEditUserInfo(req).map(new ObjectRespFunc<String>());
        toSubscribe(o, s);
    }

    public static void getVCode(VCodeRequest req, MySubcriber<VCodeInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createVCode(req).map(new ObjectRespFunc<VCodeInfo>());
		toSubscribe(o, s);
	}

	public static void bindMobile(BindMobileRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createBindMobile(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void bindBank(BindBankRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createBindBank(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void getWithdrawInfo(BaseRequest req, MySubcriber<WithdrawInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createWithdrawInfo(req).map(new ObjectRespFunc<WithdrawInfo>());
		toSubscribe(o, s);
	}

	public static void withdraw(WithdrawRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createWithdraw(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void getGiftList(GiftListRequest req, MySubcriber<List<GiftInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createGiftList(req).map(new ArrayRespFunc<GiftInfo>());
		toSubscribe(o, s);
	}

	public static void exchangeGift(ExchangeGiftRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createExchangeGift(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void getAccountList(AccountListRequest req, MySubcriber<List<RechargeAccountInfo>> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createAccountList(req).map(new ArrayRespFunc<RechargeAccountInfo>());
		toSubscribe(o, s);
	}

	public static void rechargeOffline(RechargeOfflineRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRechargeOffline(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void recharge(RechargeRequest req, MySubcriber<OrderInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createRecharge(req).map(new ObjectRespFunc<OrderInfo>());
		toSubscribe(o, s);
	}

	public static void getRechargeLog(RechargeLogRequest req, MySubcriber<List<RechargeLogInfo>> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createRechargeLog(req).map(new ArrayRespFunc<RechargeLogInfo>());
        toSubscribe(o, s);
    }

    public static void getGiftExchangeLog(RechargeLogRequest req, MySubcriber<List<GiftLogInfo>> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createGiftExchangeLog(req).map(new ArrayRespFunc<GiftLogInfo>());
        toSubscribe(o, s);
    }

    public static void resetLoginPwd(ResetLoginPwdRequest req, MySubcriber<String> s) {
        Encrypt.encrypt(req);
        Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
                .createResetLoginPwd(req).map(new ObjectRespFunc<String>());
        toSubscribe(o, s);
    }

    public static void getKefuInfo(BaseRequest req, MySubcriber<KefuInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createKefuInfo(req).map(new ObjectRespFunc<KefuInfo>());
		toSubscribe(o, s);
	}

	public static void getKefuQAList(BaseRequest req, MySubcriber<KefuQAInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createKefuQAList(req).map(new ObjectRespFunc<KefuInfo>());
		toSubscribe(o, s);
	}

	public static void getShareParams(BaseRequest req, MySubcriber<ShareParamsInfo> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createShareParams(req).map(new ObjectRespFunc<ShareParamsInfo>());
		toSubscribe(o, s);
	}

	public static void sendJoinedMsg(JoinRoomRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createJoinedMsg(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void IYIPay(DuobaoPayRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createIYIPay(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void duobaoPay(DuobaoPayRequest req, MySubcriber<String> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createDuobaoPay(req).map(new ObjectRespFunc<String>());
		toSubscribe(o, s);
	}

	public static void checkPay(PayCheckRequest req, MySubcriber<Integer> s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createPayCheck(req).map(new ObjectRespFunc<Integer>());
		toSubscribe(o, s);
	}

	public static void getProxyRule(BaseRequest req, MySubcriber s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createProxyRule(req).map(new ObjectRespFunc<ProxyRuleInfo>());
		toSubscribe(o, s);
	}

	public static void checkVersion(VersionRequest req, MySubcriber s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createCheckVersion(req).map(new ObjectRespFunc<VersionInfo>());
		toSubscribe(o, s);
	}

	public static void getEarningList(AccountRecordRequest req, MySubcriber s) {
		Encrypt.encrypt(req);
		Observable o = RetrofitHelper.getInstance().getRetrofit().create(RxApiService.class)
				.createEarningList(req).map(new ArrayRespFunc<EarningInfo>());
		toSubscribe(o, s);
	}

	private static void toSubscribe(Observable o, Subscriber s) {
		o.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(s);
	}

	/**
	 * 返回元素
	 */
	private static class ObjectRespFunc<T> implements Func1<ObjectResponse<T>, T> {
		@Override
		public T call(ObjectResponse<T> objResp) {
			if(objResp == null) {
				throw new ApiException(-1, PcddApp.applicationContext.getResources().getString(R.string.net_err_404));
			} else if(ApiInterface.SUCCEED != objResp.result_code) {
				throw new ApiException(objResp.result_code, objResp.result_desc);
			} else {
				return objResp.data;
			}
		}
	}

	/**
	 * 返回列表数据
	 */
	private static class ArrayRespFunc<T> implements Func1<ArrayResponse<T>, List<T>> {
		@Override
		public List<T> call(ArrayResponse<T> arrayResp) {
			if(arrayResp == null) {
				throw new ApiException(-1, PcddApp.applicationContext.getResources().getString(R.string.net_err_404));
			} else if(ApiInterface.SUCCEED != arrayResp.result_code) {
				throw new ApiException(arrayResp.result_code, arrayResp.result_desc);
			} else {
				return arrayResp.data;
			}
		}
	}
}
