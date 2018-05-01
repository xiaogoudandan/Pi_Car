package com.william_zhang.pi_car.config;

import android.app.Activity;

/**
 * Created by william_zhang on 2017/12/2.
 */

public class ActivityStruct {
    private Class<? extends Activity> className;
    private String mTitle;

    public Class<? extends Activity> getClassName() {
        return className;
    }

    public void setClassName(Class<? extends Activity> className) {
        this.className = className;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ActivityStruct(Class<? extends Activity> className, String mTitle) {
        this.className = className;
        this.mTitle = mTitle;
    }
}
