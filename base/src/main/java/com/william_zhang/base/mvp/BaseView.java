package com.william_zhang.base.mvp;

/**
 * Created by william_zhang on 2017/11/17.
 * mvp中V的基接口
 */

public interface BaseView {
    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
