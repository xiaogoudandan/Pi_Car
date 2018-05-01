package com.william_zhang.pi_car.Manager;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.william_zhang.pi_car.constant.Key;

/**
 * Created by william_zhang on 2018/4/11.
 */

public class CarHtmlManager {
    private static final String TAG = "BRIDGE";
    BridgeWebView mWebView;

    public CarHtmlManager(BridgeWebView webView) {
        this.mWebView = webView;
    }

    public void setCBS(String mess) {
        mWebView.callHandler("functionInJs", mess, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("CarHtmlManager:", data);
            }
        });
    }

//    public void executeCode(String code) {
//        mWebView.callHandler("executeCode", code, new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//                Log.e(TAG, data);
//            }
//        });
//    }


    public void init() {
        mWebView.callHandler("dataInit", "fass", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e(TAG, data);
            }
        });
    }
}
