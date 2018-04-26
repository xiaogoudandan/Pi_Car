package com.william_zhang.pi_car;

import android.app.Application;
import android.content.Context;

/**
 * Created by william_zhang on 2018/2/23.
 */

public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context=getApplicationContext();
    }

    public static Context getApplication(){
        return MyApplication.context;
    }
}
