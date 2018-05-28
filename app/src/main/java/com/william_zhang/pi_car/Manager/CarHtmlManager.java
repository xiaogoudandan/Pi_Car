package com.william_zhang.pi_car.Manager;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.william_zhang.base.utils.ScreenUtil;
import com.william_zhang.pi_car.MyApplication;
import com.william_zhang.pi_car.constant.Key;

/**
 * Created by william_zhang on 2018/4/11.
 */

public class CarHtmlManager {
    public void startRun() {
        mWebView.callHandler("startRun", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("startRun:", data);
            }
        });
    }

    public void stopRun() {
        mWebView.callHandler("stopRun", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("stopRun:", data);
            }
        });
    }

    public interface JsLstener {
        void stop();
    }
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


    public void init(final JsLstener jsLstener) {
        mWebView.callHandler("dataInit", Float.toString(180f), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e(TAG, data);
            }
        });
        mWebView.registerHandler("stopRun", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsLstener.stop();
            }
        });
    }

    public void clear(){
        mWebView.callHandler("dataInit", Float.toString(180f), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e(TAG, data);
            }
        });
    }
}
