package com.fz.cdh.pcdd.network;

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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by hang on 2017/2/5.
 */

public interface RxApiService {

    /** 文件上传 */
    @POST("file/upload")
    @Multipart
    Observable<ObjectResponse<String>> createUpload(@Part MultipartBody.Part file, @Part("business_type") RequestBody type);

    /** 首頁数据 */
    @POST("home/page")
    Observable<ObjectResponse<HomeDataInfo>> createHomeData(@Body HomeDataRequest req);

    /** 广告列表 */
    @POST("banner/list")
    Observable<ArrayResponse<BannerInfo>> createBannerList(@Body BannerListRequest req);

    /** 注册 */
    @POST("user/register")
    Observable<ObjectResponse<UserInfo>> createRegister(@Body RegisterRequest req);

    /** 登录 */
    @POST("user/login")
    Observable<ObjectResponse<UserInfo>> createLogin(@Body LoginRequest req);

    /** 第三方登录 */
    @POST("user3rd/login")
    Observable<ObjectResponse<UserInfo>> createLoginOther(@Body LoginOtherRequest req);

    /** 用户信息 */
    @POST("user/details")
    Observable<ObjectResponse<UserInfo>> createUserInfo(@Body BaseRequest req);

    /** 修改用户信息 */
    @POST("user/update")
    Observable<ObjectResponse<String>> createModifyUserInfo(@Body ModifyUserInfoRequest req);

    /** 消息列表 */
    @POST("notice/list")
    Observable<ArrayResponse<NoticeInfo>> createNoticeList(@Body NoticeListRequest req);

    /** 游戏房间级别列表 */
    @POST("gameArea/list")
    Observable<ArrayResponse<RoomLevelInfo>> createRoomLevelList(@Body RoomLevelListRequest req);

    /** 房间列表 */
    @POST("room/list")
    Observable<ArrayResponse<RoomInfo>> createRoomList(@Body RoomListRequest req);

    /** 加入房间 */
    @POST("room/user/add")
    Observable<ObjectResponse<String>> createJoinRoom(@Body JoinRoomRequest req);

    /** 退出房间 */
    @POST("room/user/del")
    Observable<ObjectResponse<String>> createExitRoom(@Body ExitRoomRequest req);

    /** 获取游戏比例 */
    @POST("room/bili/list")
    Observable<ObjectResponse<GameTypeInfo>> createGameTypeData(@Body GameTypeDataRequest req);
    /** 获取当前可撤单订单 */
    @POST("getLatestChoiceList")
    Observable<ArrayResponse<LatestChoiceInfo>> getLatestChoiceData(@Body GetLatestChoiceDataRequest req);
    /** 撤单订单 */
    @POST("deleteUserChoiceLog")
    Observable<ObjectResponse<CancelOrderResult>> cancleOrder(@Body CancelOrderRequest req);
    /** 房间下注记录以及倒计时接口 */
    @POST("room/open/info")
    Observable<ObjectResponse<BetDetailInfo>> createBetDetail(@Body RoomBetDetailRequest req);

    /** 下注 */
    @POST("room/point/add")
    Observable<ObjectResponse<String>> createBetting(@Body BettingRequest req);

    /** 帐变记录 */
    @POST("point/change/list")
    Observable<ArrayResponse<AccountRecordInfo>> createAccountRecord(@Body AccountRecordRequest req);

    /** 我的收益列表  */
    @POST("fenxiao/list")
    Observable<ArrayResponse<EarningInfo>> createEarningList(@Body AccountRecordRequest req);

    /** 游戏记录 */
    @POST("game/choice/list")
    Observable<ArrayResponse<GameRecordInfo>> createGameRecord(@Body GameRecordRequest req);

    /** 充值记录 */
    @POST("game/charge/list")
    Observable<ArrayResponse<RechargeRecordInfo>> createRechargeRecord(@Body RechargeRecordRequest req);

    /** 提现记录 */
    @POST("withdrawals/list")
    Observable<ObjectResponse<WithdrawRecordResponse>> createWithdrawRecord(@Body WithdrawRecordRequest req);

    /** 回水记录 */
    @POST("huishui/list")
    Observable<ArrayResponse<BackwaterInfo>> createBackWaterList(@Body BackWaterListRequest req);

    /** 修改用户信息 */
    @POST("user/update")
    Observable<ObjectResponse<String>> createEditUserInfo(@Body EditUserInfoRequest req);

    /** 短信验证码 */
    @POST("sendSMSVerification")
    Observable<ObjectResponse<VCodeInfo>> createVCode(@Body VCodeRequest req);

    /** 绑定手机 */
    @POST("user/band")
    Observable<ObjectResponse<String>> createBindMobile(@Body BindMobileRequest req);

    /** 绑定银行卡 */
    @POST("withdrawals/bank/update")
    Observable<ObjectResponse<String>> createBindBank(@Body BindBankRequest req);

    /** 提现信息 */
    @POST("tixian/params")
    Observable<ObjectResponse<WithdrawInfo>> createWithdrawInfo(@Body BaseRequest req);

    /** 提现 */
    @POST("withdrawals/add")
    Observable<ObjectResponse<String>> createWithdraw(@Body WithdrawRequest req);

    /** 礼物列表 */
    @POST("gift/list")
    Observable<ArrayResponse<GiftInfo>> createGiftList(@Body GiftListRequest req);

    /** 兑换礼物 */
    @POST("gift/exchange")
    Observable<ObjectResponse<String>> createExchangeGift(@Body ExchangeGiftRequest req);

    /** 礼物兑换记录 */
    @POST("exchange/list")
    Observable<ArrayResponse<GiftLogInfo>> createGiftExchangeLog(@Body RechargeLogRequest req);

    /** 转账账号列表 */
    @POST("account/list")
    Observable<ArrayResponse<RechargeAccountInfo>> createAccountList(@Body AccountListRequest req);

    /** 添加转账记录 */
    @POST("account/user/add")
    Observable<ObjectResponse<String>> createRechargeOffline(@Body RechargeOfflineRequest req);

    /** 充值 */
    @POST("user/recharge")
    Observable<ObjectResponse<OrderInfo>> createRecharge(@Body RechargeRequest req);

    /** 充值记录 */
    @POST("account/user/list")
    Observable<ArrayResponse<RechargeLogInfo>> createRechargeLog(@Body RechargeLogRequest req);

    /** 找回密码 */
    @POST("user/password/find")
    Observable<ObjectResponse<String>> createResetLoginPwd(@Body ResetLoginPwdRequest req);

    /** 客服信息 */
    @POST("kefu/list")
    Observable<ObjectResponse<KefuInfo>> createKefuInfo(@Body BaseRequest req);

    /** 客服问题列表 */
    @POST("quest/list")
    Observable<ObjectResponse<KefuInfo>> createKefuQAList(@Body BaseRequest req);

    /** 获取分享参数 */
    @POST("share/list")
    Observable<ObjectResponse<ShareParamsInfo>> createShareParams(@Body BaseRequest req);

    /** 加入房间消息 */
    @POST("room/user/sendMsg")
    Observable<ObjectResponse<String>> createJoinedMsg(@Body JoinRoomRequest req);

    /** 爱益支付生成付款页面 */
    @POST("aiyi/pay/url")
    Observable<ObjectResponse<String>> createIYIPay(@Body DuobaoPayRequest req);

    /** 多宝生成二维码 */
    @POST("duobao/pay/url")
    Observable<ObjectResponse<String>> createDuobaoPay(@Body DuobaoPayRequest req);

    /** 是否充值 */
    @POST("user/pay/check")
    Observable<ObjectResponse<Integer>> createPayCheck(@Body PayCheckRequest req);

    /** 分享及规则 */
    @POST("share/bili/list")
    Observable<ObjectResponse<ProxyRuleInfo>> createProxyRule(@Body BaseRequest req);

    /** 版本检查 */
    @POST("version")
    Observable<ObjectResponse<VersionInfo>> createCheckVersion(@Body VersionRequest req);
}
