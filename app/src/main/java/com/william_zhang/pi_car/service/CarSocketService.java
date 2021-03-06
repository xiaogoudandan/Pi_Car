package com.william_zhang.pi_car.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.william_zhang.base.utils.RxBus;
import com.william_zhang.base.utils.ShareParanceUtil;
import com.william_zhang.pi_car.bean.SocketBean;
import com.william_zhang.pi_car.constant.Key;
import com.william_zhang.pi_car.utils.SharedPreferencesUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by william_zhang on 2018/2/23.
 */

public class CarSocketService extends Service {
    private static final String TAG = "CarSocketService";
    private static final long HEART_RATE = 10 * 1000;
    private static String HOST = "192.168.12.1";
    private static int PORT = 2022;
    private Handler mHandler = new Handler();
    private long sendtime = 0;
    private InitSocketThead mInitSocketThead;
    private ReadSocket mReadSocket;
    private ExecutorService executorService;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendtime > HEART_RATE) {
                boolean isSuccess = sendMess("");

                if (!isSuccess) {
                    mHandler.removeCallbacks(null);
                    releaseSocket(mSocket);
                    new InitSocketThead().start();
                }
            }
            mHandler.postDelayed(runnable, HEART_RATE);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return carAidl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        executorService = Executors.newSingleThreadScheduledExecutor();
        //需要初始化socket 在线程中
        //mInitSocketThead = new InitSocketThead();
        //mReadSocket = new ReadSocket();
        //mInitSocketThead.start();
    }

    private String mess;
    private CarAidlInterface.Stub carAidl = new CarAidlInterface.Stub() {
        @Override
        public boolean sendMessage(String message) throws RemoteException {
            mess = message;
            executorService.execute(sendRunnable);
            return true;
        }

        @Override
        public void initSocket(String ip, int port) throws RemoteException {
            HOST = ip;
            PORT = port;
            mInitSocketThead = new InitSocketThead();
            mReadSocket = new ReadSocket();
            mInitSocketThead.start();
        }

        @Override
        public void releaseSocket() throws RemoteException {
            releaseThread();
            releaseCarSocket();
        }

        @Override
        public void initDefaultSocket() throws RemoteException {
            String host = ShareParanceUtil.getString(getApplicationContext(), Key.CAR_SHAREpARANCE, Key.CAR_HOST);
            int post = ShareParanceUtil.getInt(getApplicationContext(), Key.CAR_SHAREpARANCE, Key.CAR_POST);
            if (!host.equals(""))
                HOST = host;
            if (post != 0)
                PORT = post;
            mInitSocketThead = new InitSocketThead();
            mReadSocket = new ReadSocket();
            mInitSocketThead.start();
        }
    };

    Runnable sendRunnable = new Runnable() {
        @Override
        public void run() {
            sendMess(mess);
        }
    };

    /**
     * 发送message
     *
     * @param message
     * @return
     */
    private boolean sendMess(String message) {
        if (so == null)
            return false;
        try {
            Socket socket = so;
            if (!socket.isClosed() && !socket.isOutputShutdown()) {//可出书
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter out;
                String m = message;
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        outputStream)), true);
                out.print(message);
                out.flush();
                Log.e(TAG, "send: " + message);
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private WeakReference<Socket> mSocket;
    private Socket so;

    class InitSocketThead extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
            initRead();
        }

        private void initRead() {
            mReadSocket.start();
        }

        private void initSocket() {
            //开始初始化socket
            try {
                so = new Socket(HOST, PORT);
                Log.e(TAG, "conning...");
                //mSocket = new WeakReference<>(so);//虚引用
                //rxBusPost(SocketBean.ISCONNECT, "yes", "");
            } catch (IOException e) {
                //rxBusPost(SocketBean.ISCONNECT, "no", "");
                Log.e(TAG, "connecting error");
                e.printStackTrace();
            }
        }
    }

    private boolean isRunning = true;

    class ReadSocket extends Thread {
        @Override
        public void run() {
            super.run();
            if (so != null) {
                try {
                    InputStream is = so.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    Log.e(TAG, "reading...");
                    isRunning = true;
                    while (!so.isClosed() && !so.isInputShutdown()
                            && ((length = is.read(buffer)) != -1) && isRunning) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            //收到服务器过来的消息，就通过Broadcast发送出去
                            //rxBusPost(SocketBean.FROMSOCKET, message, "");
                            Log.d(TAG, "Read from server" + message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void rxBusPost(int type, String result, String error) {
        SocketBean socketBean = new SocketBean();
        socketBean.setType(type);
        socketBean.setResult(result);
        socketBean.setError(error);
        RxBus.getInstance().post(socketBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                releaseCarSocket();
                releaseThread();
            }
        }, 0);
    }

    private void releaseThread() {
        Log.d(TAG, "releaseThread");
        if (mReadSocket != null)
            isRunning = false;
    }

    private void releaseSocket(WeakReference<Socket> socketWeakReference) {
        try {
            if (socketWeakReference != null) {
                Socket socket = socketWeakReference.get();
                if (!socket.isClosed()) {
                    socket.close();
                }
                socket = null;
                socketWeakReference = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseCarSocket() {
        try {
            if (so != null) {
                if (!so.isClosed()) {
                    so.close();
                }
                so = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
