package com.william_zhang.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * Created by william_zhang on 2017/11/17.
 */

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    public ActivityManager() {
    }

    public static synchronized ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
            if (activityStack == null)
                activityStack = new Stack<>();
        }
        return instance;
    }

    public synchronized void addActivity(Activity activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
            activityStack.add(activity);
            Log.i("TAG", "ActivityManager添加了：" + activity.getClass().getName());
        } else
            Log.i("TAG", "ActivityManager存在" + activity.getClass().getName());
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 移除最后一个Activity
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            Log.i("TAG", "ActivityManager关闭了：" + activity.getClass().getName());
        }
    }

    /**
     * 移除最后一个Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            Log.i("TAG", "ActivityManager移除了：" + activity.getClass().getName());
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                removeActivity(activityStack.get(i));
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用
     */
    public void addExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
