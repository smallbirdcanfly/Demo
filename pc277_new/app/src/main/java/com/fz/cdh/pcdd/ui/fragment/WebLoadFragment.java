package com.fz.cdh.pcdd.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.util.T;

/**
 * Created by hang on 2015/11/19.
 * 加载网页
 */
public class WebLoadFragment extends BaseFragment {

	public final static String PARAMS_TITLE = "params_title";
    public final static String PARAMS_URL = "params_url";
    public final static String PARAMS_BACK = "params_back";
    public final static String PARAMS_LOAD_TYPE = "params_type";
    
    private ProgressBar progressBar;
    private WebView webView;
    private String url;

    public static synchronized WebLoadFragment getInstance(String url) {
        return getInstance("", url, true);
    }
    public static synchronized WebLoadFragment getInstance(String url,String loadType) {
        return getInstance("", url, true,loadType);
    }
    public static synchronized WebLoadFragment getInstance(String title, String url, boolean back,String loadType) {
        WebLoadFragment mInstance = new WebLoadFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TITLE, title);
        args.putString(PARAMS_URL, url);
        args.putBoolean(PARAMS_BACK, back);
        args.putString(PARAMS_LOAD_TYPE, loadType);
        //PARAMS_LOAD_TYPE

        mInstance.setArguments(args);
        return mInstance;
    }
    public static synchronized WebLoadFragment getInstance(String title, String url, boolean back) {
        WebLoadFragment mInstance = new WebLoadFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_TITLE, title);
        args.putString(PARAMS_URL, url);
        args.putBoolean(PARAMS_BACK, back);
        mInstance.setArguments(args);
        return mInstance;
    }
    
    @Override
	protected int getLayoutId() {
    	return R.layout.fragment_web_view;
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
        webView.setWebViewClient(new MyWebViewClient());
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
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.stopLoading();
        }
    }

    @Override
	public void onDestroy() {
    	webView.destroy();
		super.onDestroy();
	}

	public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if("update".equalsIgnoreCase(getArguments().getString(PARAMS_LOAD_TYPE))||"pay".equalsIgnoreCase(getArguments().getString(PARAMS_LOAD_TYPE))){
                Uri uri = Uri.parse(url); // url为你要链接的地址
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }else {
                return super.shouldOverrideUrlLoading(view, url);
            }
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
