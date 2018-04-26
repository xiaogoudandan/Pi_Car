package com.william_zhang.base.mvp.baseImpl;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by william_zhang on 2017/11/17.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    protected V view;

    public BasePresenterImpl(V view) {
        this.view = view;
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {
         this.view=null;
         unDisposable();
    }
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;
    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
