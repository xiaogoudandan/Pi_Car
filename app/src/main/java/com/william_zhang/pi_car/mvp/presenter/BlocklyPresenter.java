package com.william_zhang.pi_car.mvp.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.william_zhang.base.mvp.baseImpl.BasePresenterImpl;
import com.william_zhang.base.retrofit.ExceptionHelper;
import com.william_zhang.base.utils.RxBus;
import com.william_zhang.pi_car.Manager.CarHtmlManager;
import com.william_zhang.pi_car.bean.SocketBean;
import com.william_zhang.pi_car.mvp.contact.BlocklyContact;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by william_zhang on 2018/3/25.
 */

public class BlocklyPresenter extends BasePresenterImpl<BlocklyContact.view> implements BlocklyContact.presenter {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    view.connectSocket();
                    break;
            }
        }
    };

    public BlocklyPresenter(BlocklyContact.view view) {
        super(view);
    }

    @Override
    public void init() {
        view.init();
        RxBus.getInstance().toObservable(SocketBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SocketBean>() {
                    @Override
                    public void accept(SocketBean socketBean) throws Exception {
                        switch (socketBean.getType()) {
                            case SocketBean.FROMSOCKET:
                                //read数据
                                Log.d("RxBus data:", socketBean.getResult());
                                view.setCSBHtml(socketBean.getResult());
                                break;
                            case SocketBean.ISCONNECT:

                                //连接状态
                                if (socketBean.getResult().equals("yes")) {
                                    //dialog提示成功连接
                                    Log.d("RxBus：", "connect success");
                                    view.connectSuccess();
                                } else if (socketBean.getResult().equals("no")) {
                                    //dialog提示失败
                                    Log.d("RxBus：", "connect fail");
                                    view.connectFail();
                                }
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ExceptionHelper.handleException(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                });
        view.showLoadingDialog("小车");
        handler.sendEmptyMessageDelayed(1, 3500);
    }
}
