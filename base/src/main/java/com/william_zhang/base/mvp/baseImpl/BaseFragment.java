package com.william_zhang.base.mvp.baseImpl;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by william_zhang on 2017/11/17.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected ZLoadingDialog dialog;
    protected Z_TYPE z_type = Z_TYPE.CIRCLE_CLOCK;
    protected P presenter;
    private boolean isViewCreate = false;
    private boolean isViewVisible = false;
    public Context context;
    private boolean isFirst = true;

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visiableToUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewVisible) {
            visiableToUser();
        }
    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visiableToUser() {
        if (isFirst) {
            fitstLoad();
            isFirst = false;
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected void fitstLoad() {
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
        }
        isViewCreate = false;
        super.onDestroyView();
    }

    public abstract P initPresenter();

    @Override
    public void dismissLoadingDialog() {
        dialog.dismiss();
    }

    @Override
    public void showLoadingDialog(String msg) {
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(z_type)
                .setLoadingColor(Color.parseColor("#7B68EE"))
                .setHintText(msg)
                .show();
    }
}
