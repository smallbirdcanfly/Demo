package com.fz.cdh.pcdd.network;

import com.fz.cdh.pcdd.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hang on 2016/9/1.
 */
public class RetrofitHelper {

    private static RetrofitHelper instance;

    private Retrofit retrofit;

    private RetrofitHelper() {
        OkHttpClient client = new OkHttpClient();
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
            loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .addInterceptor(loggin)
//                    .certificatePinner(new CertificatePinner.Builder().build())
//                    .sslSocketFactory(getSSLSocketFactory())
                    .build();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static RetrofitHelper getInstance() {
        if(instance == null)
            instance = new RetrofitHelper();
        return instance;
    }
    public static RetrofitHelper getInstance(boolean ischange) {
        if(instance == null||ischange)
            instance = new RetrofitHelper();
        return instance;
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        TrustManager[] manager = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, manager, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
