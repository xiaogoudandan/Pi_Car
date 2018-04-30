package com.william_zhang.pi_car;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.william_zhang.base.utils.ScreenUtil;

/**
 * Created by william_zhang on 2018/4/9.
 */

public class BottomView extends android.support.v7.widget.AppCompatButton {
    private int state = 0;

    public BottomView(Context context) {
        super(context);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    RelativeLayout root;//保存父类节点
    RelativeLayout main;
    RelativeLayout.LayoutParams params;
    boolean isBottom = true;//默认是隐藏的
    float beginY;
    float top;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ViewParent viewParent = getParent();
                if (viewParent instanceof RelativeLayout) {
                    root = (RelativeLayout) viewParent;
                    params = (RelativeLayout.LayoutParams) root.getLayoutParams();
                    top = params.topMargin;
                    Log.e("RootMargin begin:", Integer.toString(params.topMargin));
                    main = (RelativeLayout) root.getParent();
                }
                if (root != null) {
                    int[] mear = new int[2];
                    root.getLocationOnScreen(mear);
                    //top = getTop();
                    int y = mear[1];//获取父类相对整个屏幕的距离y
                    int screenH = ScreenUtil.getScreenHeight(getContext());
                    if (y + getHeight() < screenH + 5 && y + getHeight() > screenH - 5) {//当前位置在底部隐藏
                        isBottom = true;
                    } else {
                        isBottom = false;
                    }
                }
                beginY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                int[] mear = new int[2];
//                root.getLocationOnScreen(mear);
//                int y = mear[1];//获取父类相对整个屏幕的距离y
                float currentY = event.getRawY();//当前Y
                Log.e("RootMargin move Y:", Float.toString(currentY));
                if (isBottom && currentY - beginY < 0) {//在底部的时候向上拉动
                    if (root != null && top + currentY - beginY > main.getHeight() - ScreenUtil.dip2px(getContext(), 200)) {
                        //root.scrollTo(0, y + currentY - beginY);//移动到
                        params.topMargin = (int) (top + currentY - beginY);
                        Log.e("RootMargin move:", Integer.toString(params.topMargin));
                        root.setLayoutParams(params);
                    }
                }
                if (!isBottom && currentY - beginY > 0) {//如果不在底部且向下拉动
                    if (root != null && top + currentY - beginY < main.getHeight() + getHeight()) {
                        params.topMargin = (int) (top + currentY - beginY);
                        Log.e("RootMargin move:", Integer.toString(params.topMargin));
                        root.setLayoutParams(params);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (root == null) {
                    break;
                }
                if (isBottom) {
                    params.topMargin = main.getHeight() - ScreenUtil.dip2px(getContext(), 200);
                    Log.e("RootMargin end:", Integer.toString(params.topMargin));
                    root.setLayoutParams(params);
                    state = 1;
                } else {
                    params.topMargin = main.getHeight() - getHeight();
                    Log.e("RootMargin end:", Integer.toString(params.topMargin));
                    root.setLayoutParams(params);
                    state = 2;
                }
                break;
        }
        return true;
    }


    public void open() {
        if (state != 1) {
            ViewParent viewParent = getParent();
            root = (RelativeLayout) viewParent;
            main = (RelativeLayout) root.getParent();
            params = (RelativeLayout.LayoutParams) root.getLayoutParams();
            params.topMargin = main.getHeight() - ScreenUtil.dip2px(getContext(), 200);
            root.setLayoutParams(params);
            state = 1;
        }
    }

    public void close() {
        if (state != 2) {
            ViewParent viewParent = getParent();
            root = (RelativeLayout) viewParent;
            main = (RelativeLayout) root.getParent();
            params = (RelativeLayout.LayoutParams) root.getLayoutParams();
            params.topMargin = main.getHeight() - getHeight();
            root.setLayoutParams(params);
            state = 2;
        }
    }
}
