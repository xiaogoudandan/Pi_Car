package com.william_zhang.pi_car.mvp.contact;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;

/**
 * Created by william_zhang on 2018/5/1.
 */

public interface  SplashContact {
    interface view extends BaseView{
        void init();

        void setTime(int time);

        void forWord();
    }

    interface presenter extends BasePresenter{

        void init();

        void startTime(int i);
    }
}
