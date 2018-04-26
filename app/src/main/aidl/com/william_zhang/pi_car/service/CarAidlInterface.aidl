// CarAidlInterface.aidl
package com.william_zhang.pi_car.service;

// Declare any non-default types here with import statements

interface CarAidlInterface {
    boolean sendMessage(String message);
    void initSocket(String ip,int port);
    void releaseSocket();
    void initDefaultSocket();
}
