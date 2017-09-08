package com.fz.cdh.pcdd.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.util.T;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by hang on 2015/11/19.
 * 加载网页
 */
public class WebLotteryFragment extends BaseFragment {

	public final static String PARAMS_TITLE = "params_title";
    public final static String PARAMS_URL = "params_url";
    public final static String PARAMS_BACK = "params_back";
    
    private ProgressBar progressBar;
    private BridgeWebView webView;
    private String url;
    public static synchronized WebLotteryFragment getInstance(String url,String userId) {
        return getInstance("", url, true,userId);
    }
    public static synchronized WebLotteryFragment getInstance(String title, String url, boolean back,String userId) {
        WebLotteryFragment mInstance = new WebLotteryFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TITLE, title);
        args.putString("userId", userId);
        args.putString(PARAMS_URL, url);
        args.putBoolean(PARAMS_BACK, back);
        mInstance.setArguments(args);
        return mInstance;
    }
    
    @Override
	protected int getLayoutId() {
    	return R.layout.fragment_web_jsbridge;
	}

	@Override
	protected void init(View rootView) {
		progressBar = getView(R.id.pgLoadUrl);
		progressBar.setMax(100);
		
        webView = getView(R.id.webView);
//        webView.getSettings().setUserAgentString(UA);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSClient(getActivity()), "pcdandan");
        // 保证可滑动
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);// 支持缩放
        webView.getSettings().setLoadsImagesAutomatically(true);
        // 启用数据库
        webView.getSettings().setDatabaseEnabled(true);
        // 启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        // 设置定位的数据库路径
        String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setGeolocationDatabasePath(dir);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.requestFocus();
       webView.setDefaultHandler(new DefaultHandler());
        webView.setWebViewClient(new MyWebViewClient(webView));
        // 各种内容的渲染需要使用webviewChromClient去实现
        webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);
			}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
        	url = getArguments().getString(PARAMS_URL);

            webView.loadUrl(url);
            //注册本地调用js的方法
            webView.registerHandler("JSCallNative", new BridgeHandler() {

                @Override
                public void handler(String jsBackInfo, CallBackFunction callBackFunction) {
                    try{
                    JSONObject jo = new JSONObject(jsBackInfo);
                       String closeType =jo.optString("close");
                        String levelType =jo.optString("level");
                        String laterType=jo.optString("later");

                        if(!TextUtils.isEmpty(closeType)){
                            getActivity().finish();
                        }
                        if(!TextUtils.isEmpty(levelType)){
                            //回传userId
                           String userId= getArguments().getString("userId");
//                            1、使用JSONObject
                           JSONObject json =new JSONObject();
                            json.put("userId",userId);
                            callBackFunction.onCallBack(json.toString());
                           // String json = "{\"userId\":"+userId+"}";
                           // callBackFunction.onCallBack(json);
                        }
                        if(!TextUtils.isEmpty(laterType)){
                            getActivity().finish();
                        }
                     }catch(Exception e){
                    e.printStackTrace();
                        Toast.makeText(webView.getContext(),"js返回的信息"+jsBackInfo+"解析异常",Toast.LENGTH_SHORT).show();
                    }
                    System.out.print("JSCallNative"+"js返回的信息"+jsBackInfo);
                 //   Toast.makeText(webView.getContext(),"js返回的信息"+jsBackInfo,Toast.LENGTH_SHORT).show();
                  //  callBackFunction.onCallBack("JAVA本地返回给js的数据");

                }
            });
            //设置js可以调用的本地方法，并传递一定的数据
            webView.callHandler("nativeCallJS", "info", new CallBackFunction() {
                @Override
                public void onCallBack(String s) {
                    //调用本地方法获得数据后，js返回的信息
                    System.out.print("nativeCallJS"+"js回调回来的信息--"+s);
                   // Toast.makeText(webView.getContext(),"js回调回来的信息--"+s,Toast.LENGTH_SHORT).show();
                }
            });
        }
       // webView.send("");

    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.stopLoading();
        }
    }

    @Override
	public void onDestroy(){
    	webView.destroy();
		super.onDestroy();
	}

	public class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            progressBar.setVisibility(View.GONE);
            if(getActivity() != null)
                T.showShort("加载失败，请稍候再试");
        }
    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            activity.finish();
        }
    }

    class JSClient {

        private Context mContext;

        private static final int RESULT_RREFRUSH = 1;

        public JSClient(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public String getIMEI() {
            TelephonyManager tm = (TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        }

        @JavascriptInterface
        public void mobaoPaySuccess() {
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    }
}
