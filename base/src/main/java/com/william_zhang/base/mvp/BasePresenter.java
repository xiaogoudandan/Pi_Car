package com.william_zhang.base.mvp;

import io.reactivex.disposables.Disposable;

/**
 * Created by william_zhang on 2017/11/17.
 * mvp中的P 基共性接口
 */

public interface BasePresenter {
    //初始化操作
    void start();

    //activity关闭操作
    void detach();

    //网络请求 防止内存泄漏
    void addDisposable(Disposable disposable);

    //注销所有请求
    void unDisposable();

}
