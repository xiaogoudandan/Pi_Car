package com.william_zhang.pi_car.mvp.presenter;

import com.william_zhang.base.mvp.baseImpl.BasePresenterImpl;
import com.william_zhang.base.utils.NetUtils;
import com.william_zhang.pi_car.MyApplication;
import com.william_zhang.pi_car.mvp.contact.CarContact;

/**
 * Created by william_zhang on 2018/2/23.
 */

public class CarPresenter extends BasePresenterImpl<CarContact.view> implements CarContact.presenter {
    private static final String IpAddress="192.168.12.1";
    public static final String vedioUrl="http://192.168.12.1:8080/?action=snapshot";
    public CarPresenter(CarContact.view view) {
        super(view);
    }

    @Override
    public void init() {
        if(NetUtils.isWifiConnected(MyApplication.getApplication())){
            String ip=NetUtils.getIpAddress(MyApplication.getApplication());
            if(!ip.equals("")&&ip.contains("192.168.12.")){//当前链接正确
                view.setVedio(vedioUrl);
            }
        }
    }
}
