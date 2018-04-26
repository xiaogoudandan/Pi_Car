package com.william_zhang.pi_car.mvp.contact;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.william_zhang.pi_car.Manager.CarHtmlManager;

/**
 * Created by william_zhang on 2018/3/25.
 */

public interface BlocklyContact {
    public interface presenter extends BasePresenter {
        void init();
    }

    interface view extends BaseView {
        void setCSBHtml(String csb);
        void connectSocket();
        void connectFail();
        void connectSuccess();
        void init();
    }
}
