package com.william_zhang.pi_car.mvp.contact;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;

/**
 * Created by william_zhang on 2018/2/23.
 */

public interface CarContact {
    interface view extends BaseView {

        void setCar();
        void setVedio(String url);
    }

    interface presenter extends BasePresenter {

        void init();
    }
}
