package com.william_zhang.pi_car.mvp.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.william_zhang.base.mvp.baseImpl.BaseActivity;
import com.william_zhang.pi_car.Manager.MJPGManager;
import com.william_zhang.pi_car.R;
import com.william_zhang.pi_car.bean.CarMassage;
import com.william_zhang.pi_car.mvp.contact.CarContact;
import com.william_zhang.pi_car.mvp.presenter.CarPresenter;
import com.william_zhang.pi_car.service.CarAidlInterface;
import com.william_zhang.pi_car.service.CarSocketService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by william_zhang on 2018/2/23.
 */

public class CarActivity extends BaseActivity<CarContact.presenter> implements CarContact.view {
    private static final String TOP = "FF0001";
    private static final String BOTTOM = "FF0002";
    private static final String LEFT = "FF0003";
    private static final String RIGHT = "FF0004";
    private static final String STOP = "FF0000";
    @BindView(R.id.jzvideo)
    JZVideoPlayerStandard jzvideo;
    @BindView(R.id.btn_top)
    Button btnTop;
    @BindView(R.id.app_init_btn)
    Button appInitBtn;
    @BindView(R.id.app_release_btn)
    Button appReleaseBtn;
    @BindView(R.id.app_ip_et)
    EditText appIpEt;
    @BindView(R.id.app_port_et)
    EditText appPortEt;

    @OnClick(R.id.app_init_btn)
    void initSocket() {
        try {
            carAidl.initSocket(appIpEt.getText().toString(),
                    Integer.valueOf(appPortEt.getText().toString()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.app_release_btn)
    void releaseSocket() {
        try {
            carAidl.releaseSocket();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_top)
    void top() {
        carFunction(TOP);
    }

    @BindView(R.id.btn_left)
    Button btnLeft;

    @OnClick(R.id.btn_left)
    void left() {
        carFunction(LEFT);
    }

    @BindView(R.id.btn_right)
    Button btnRight;

    @OnClick(R.id.btn_right)
    void right() {
        carFunction(RIGHT);
    }

    @OnClick(R.id.btn_stop)
    void stop() {
        carFunction(STOP);
    }

    @OnClick(R.id.app_jump_blockly)
    void jumpToBlockly() {
        startActivity(new Intent(CarActivity.this, BlocklyActivity.class));
    }

    @BindView(R.id.btn_bottom)
    Button btnBottom;

    @OnClick(R.id.btn_bottom)
    void bottom() {
        carFunction(BOTTOM);
    }

    @BindView(R.id.SurfaceView)
    SurfaceView video;
    private MJPGManager mjpgManager;
    private CarAidlInterface carAidl;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            carAidl = CarAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            carAidl = null;
        }
    };

    public void carFunction(String f) {
        try {
            String mess = new Gson().toJson(new CarMassage.CarMassageBuilder().setMessage(f).setError("0").build());
            carAidl.sendMessage(mess);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        bindService(new Intent(CarActivity.this, CarSocketService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public CarContact.presenter initPresenter() {
        return new CarPresenter(this);
    }

    @Override
    public void newStateChange(int state) {

    }

    @Override
    public void setCar() {

    }

    @Override
    public void setVedio(String url) {
        //MediaPlayerHelper.getInstance().setSurfaceView(video).play(url);
        //jzvideo.setUp(url, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
        //mjpgManager = new MJPGManager(video);
        //mjpgManager.start(url);
    }
}
