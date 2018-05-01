package com.william_zhang.pi_car.mvp.presenter;

import com.william_zhang.base.mvp.baseImpl.BasePresenterImpl;
import com.william_zhang.base.retrofit.ExceptionHelper;
import com.william_zhang.pi_car.mvp.contact.SplashContact;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by william_zhang on 2018/5/1.
 */

public class SplashPresenter extends BasePresenterImpl<SplashContact.view> implements SplashContact.presenter {
    public SplashPresenter(SplashContact.view view) {
        super(view);
    }

    @Override
    public void init() {
        view.init();
    }

    @Override
    public void startTime(final int i) {
        Observable<Integer> observable= Observable.interval(0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return i-aLong.intValue();
                    }
                })
                .take(i+1)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.setTime(integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ExceptionHelper.handleException(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        view.forWord();
                    }
                });
    }
}
