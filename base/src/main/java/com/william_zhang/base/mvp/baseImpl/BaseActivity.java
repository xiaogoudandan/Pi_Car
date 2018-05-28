package com.william_zhang.base.mvp.baseImpl;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.william_zhang.base.service.NetBroadcastReceiver;
import com.william_zhang.base.utils.ActivityManager;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by william_zhang on 2017/11/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected ZLoadingDialog dialog;
    protected Z_TYPE z_type = Z_TYPE.ELASTIC_BALL;
    protected P presenter;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = initPresenter();
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        NetBroadcastReceiver.getInstance().setmListener(new NetBroadcastReceiver.NetListener() {
            @Override
            public void netChange(int state) {
                newStateChange(state);
            }
        });
        registerReceiver(NetBroadcastReceiver.getInstance(), filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(NetBroadcastReceiver.getInstance());
    }

    /**
     * 子类中初始化presenter
     * @return
     */
    public abstract P initPresenter();


    @Override
    public void dismissLoadingDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void showLoadingDialog(String msg) {
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(z_type)
                .setLoadingColor(Color.parseColor("#7B68EE"))
                .setHintText(msg)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create()
                .show();
    }

    protected void newStateChange(int state) {

    }
}
