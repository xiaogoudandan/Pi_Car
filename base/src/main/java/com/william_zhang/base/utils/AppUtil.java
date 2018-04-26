package com.william_zhang.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;

import com.william_zhang.base.R;

/**
 * Created by william_zhang on 2018/4/11.
 */

public class AppUtil {
    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getDarkColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    public static Drawable setIconColor(Drawable drawable, int color) {
        Drawable drawable1 = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(drawable1, ColorStateList.valueOf(color));
        return drawable1;
    }
}
