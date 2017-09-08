package com.fz.cdh.pcdd.network;

/**
 * Created by hang on 2016/12/1.
 */
public interface HttpResultCallback<T> {
    public void onStart();
    public void onCompleted();
    public void onError(Throwable e);
    public void onNext(T t);
}
