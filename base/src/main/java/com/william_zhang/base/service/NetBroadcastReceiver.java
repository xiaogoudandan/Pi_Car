package com.william_zhang.base.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.william_zhang.base.utils.NetUtils;


/**
 * Created by william_zhang on 2017/11/28.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {
    private static NetBroadcastReceiver mNetBroadcastReceiver;
    private static boolean wifiEnable;
    public static int netType;
    public static NetBroadcastReceiver getInstance() {
        if (mNetBroadcastReceiver == null) {
            synchronized (NetBroadcastReceiver.class) {
                if (mNetBroadcastReceiver == null) {
                    mNetBroadcastReceiver = new NetBroadcastReceiver();
                }
            }
        }
        return mNetBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //检测wifi状态的打开还是关闭，和链接没用关系
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.d("wifi", "state" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiEnable = false;
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiEnable = true;
                    break;
            }
        }

        /**
         * 判断有无网络链接
         */
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (NetUtils.isNetworkConnected(context)) {
                netType=NetUtils.getConnectedType(context);
                if (mListener != null) {
                    mListener.netChange(netType);
                }
            } else {
                netType=-1;
                if (mListener != null) {
                    mListener.netChange(netType);
                }
            }
        }
    }

    private NetListener mListener;

    public interface NetListener {
        void netChange(int state);
    }

    public void setmListener(NetListener mListener) {
        this.mListener = mListener;
    }
}
