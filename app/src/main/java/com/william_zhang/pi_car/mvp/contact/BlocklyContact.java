package com.william_zhang.pi_car.mvp.contact;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.william_zhang.pi_car.Manager.CarHtmlManager;
import com.william_zhang.pi_car.service.CarAidlInterface;

/**
 * Created by william_zhang on 2018/3/25.
 */

public interface BlocklyContact {
    public interface presenter extends BasePresenter {
        void init();

        void setAIDL(CarAidlInterface carAidl);

        void sendCode(String generatedCode);

        void breakConnect();

        void startConnect();
    }

    interface view extends BaseView {
        void setCSBHtml(String csb);
        void connectSocket();
        void connectFail();
        void connectSuccess();
        void init();

        void showDialog(String s);

        void clearData();

        void breakConnect();
    }
}
