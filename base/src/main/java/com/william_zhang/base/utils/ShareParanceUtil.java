package com.william_zhang.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by william_zhang on 2018/4/11.
 */

public class ShareParanceUtil {
    public static String getString(Context context, String sp_name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, 0);
        return sharedPreferences.getString(key, "");
    }

    public static void setString(Context context, String sp_name, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setInt(Context context, String sp_name, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String sp_name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, 0);
        return sharedPreferences.getInt(key, 0);
    }
}
