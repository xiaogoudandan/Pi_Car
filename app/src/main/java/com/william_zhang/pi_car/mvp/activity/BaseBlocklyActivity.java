package com.william_zhang.pi_car.mvp.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.william_zhang.base.service.NetBroadcastReceiver;
import com.william_zhang.base.utils.ActivityManager;
import com.william_zhang.pi_car.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by william_zhang on 2018/3/25.
 */

public abstract class BaseBlocklyActivity<P extends BasePresenter> extends AbstractBlocklyActivity implements BaseView {
    protected ZLoadingDialog dialog;
    protected Z_TYPE z_type = Z_TYPE.DOUBLE_CIRCLE;
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

    protected void newStateChange(int state) {
        Log.e("BaseBlocklyActivity", "newStateChange:" + Integer.toString(state));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(NetBroadcastReceiver.getInstance());
    }

    /**
     * 子类中初始化presenter
     *
     * @return
     */
    public abstract P initPresenter();


    @Override
    public void dismissLoadingDialog() {
        dialog.dismiss();
    }

    @Override
    public void showLoadingDialog(String msg) {
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(z_type)
                .setLoadingColor(Color.parseColor("#4CAF50"))
                .setHintText(msg)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create()
                .show();
    }


    @Override
    protected int getActionBarMenuResId() {
        return R.menu.split_actionbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
