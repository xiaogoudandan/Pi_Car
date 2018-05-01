package com.william_zhang.pi_car.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.william_zhang.pi_car.config.ActivityMapping;
import com.william_zhang.pi_car.config.ActivityStruct;

/**
 * Created by william_zhang on 2018/5/1.
 */

public class ForwardUtil {
    private static Class<? extends Activity> lastActivity;

    public static void openActivity(Context fromContext, String toActivityId, Intent intent){
        if (fromContext == null) {
            Log.e("ForwardUtils", " context is null");
            return;
        }
        ActivityStruct toActivityStruct = ActivityMapping.getInstance().getStruct(toActivityId);
        if (toActivityStruct == null) {
            Log.e("ForwardUtils", " toStruct is null");
            return;
        }

        Class<? extends Activity> toActivity = toActivityStruct.getClassName();
        if (lastActivity != null && lastActivity == toActivity) {

        }
        if (intent == null) {
            intent = new Intent();
        }
        if (fromContext instanceof Activity && toActivity != null) {
            intent.setClass(fromContext, toActivity);
            ((Activity) fromContext).startActivity(intent);
        }
    }
}
