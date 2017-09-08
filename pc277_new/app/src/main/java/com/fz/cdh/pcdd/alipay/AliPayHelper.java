package com.fz.cdh.pcdd.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.fz.cdh.pcdd.BuildConfig;
import com.fz.cdh.pcdd.network.ApiInterface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AliPayHelper {

	// 商户PID
	public static final String PARTNER = "2088521624989491";
	// 商户收款账号
	public static final String SELLER = "1599999967@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMRKm6bQrvgp5j2u3+tAHSCsfF0ZtSvZ4+5r1V1rry7hLGVdOXBryTwXyIeuUiKXKy9oSqVSWq6wTqdu4tfzk74yEK1YN4KkEnwCY4R5AGBy4In5B5K4lFjE5OAacsfjk6VZQokwihaFGwZgPwrSyhxIWIPhjjjlvrofkpPty/jzAgMBAAECgYEAxD/fnQmZVa32bwdPApIoTQmGkUJpmz0OoMoZGXzXOiSQ0YtCKT7qg/U3XUgRAFppJ826i81psqu9B7NagxQZSdbE2e/0bl8zM220XIefOWdLD28jxH8mgwx7SUehI96kiRplxiP9Gex1gLn+alc/NONJPk7MPEiu2sdqx7+4JvECQQDweMqjNDHtBBliQ4GcDb6/ZdjjPCjTXGMCW2nQv3tQy9AH3LDJZ9DEKWzilG9HWUJw6ejvv4FOfhxR8XQwN6NbAkEA0Pd52uHesKJ8hIjR83sFmgjvlMSo6oA4Ous9feaHrP/CaYExQOIWlPzw4ZuKcekBzvgLVHkvKmfzVrOGgkxsSQJAQ5V/Rh0eRwHW7IIShDxstrg2G69FvhmCDiCI/c1DKDMP9ZW17fUKfIcz/f4/xCq2/KsQ94i4G8pfxTv6Lq8EbwJAGow2OO6VIg+ijhvrwGkSrx0PgvDxHMmpWyLjrwkIl5vukQTOwxV2FPuFhsQB9LLLfgXZOqQir9qA+bTZE9itsQJAFRr89uRZEQqDMQkv+yzEH82Rv88EPZPLzNvqxCISyvMsmLkBeoiYddBfT6jvj4bhpwkqyISZRRoUgn8pGBOD6g==";	// 支付宝公钥

	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDESpum0K74KeY9rt/rQB0grHxdGbUr2ePua9Vda68u4SxlXTlwa8k8F8iHrlIilysvaEqlUlqusE6nbuLX85O+MhCtWDeCpBJ8AmOEeQBgcuCJ+QeSuJRYxOTgGnLH45OlWUKJMIoWhRsGYD8K0socSFiD4Y445b66H5KT7cv48wIDAQAB";
	// 支付回调地址
	public String NOTIFY_URL = ApiInterface.HOST + "pcdd-api-client-interface/ali/recv";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;

	private Context context;

	private PayCompleteCallback callback;

	public AliPayHelper(Context context) {
		this.context = context;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					if (callback != null)
						callback.paySuccess();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						if (callback != null)
							callback.payFailure();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						if (callback != null)
							callback.payFailure();
					}
				}
				break;
			}

			case SDK_CHECK_FLAG: {

				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String tradeNo, String subject, String body, double price) {
		if(BuildConfig.DEBUG)
			price = 0.01;

		if(price <= 0)
			price = 0.01;
		
		// 订单
		String orderInfo = getOrderInfo(tradeNo, subject, body, price+"");

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) context);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check() {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask((Activity) context);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 */
	public String getSDKVersion() {
		PayTask payTask = new PayTask((Activity) context);
		return payTask.getVersion();
	}

	/**
	 * create the order info. 创建订单信息
	 */
	public String getOrderInfo(String TradeNo, String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + TradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url="
				+ "\""
				+ NOTIFY_URL
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"http://www.baidu.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 16);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public void setPayCallback(PayCompleteCallback callback) {
		this.callback = callback;
	}

	public interface PayCompleteCallback {
		public void paySuccess();
		public void payFailure();
	}
}
