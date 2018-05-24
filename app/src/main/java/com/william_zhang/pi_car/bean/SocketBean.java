package com.william_zhang.pi_car.bean;

/**
 * Created by william_zhang on 2018/4/11.
 */

public class SocketBean {
    //接收socket数据
    public static final int FROMSOCKET = 1;
    //连接状态
    public static final int ISCONNECT = 2;
    public static final int BREAKCONNECT = 5;
    //发送数据状态
    //代码
    public static final int CODE = 3;
    //控制
    public static final int CONTROLL = 4;
    private int type;
    private String result;
    private String error;

    public SocketBean(SocketBuilder socketBuilder) {
        this.type = socketBuilder.type;
        this.result = socketBuilder.result;
        this.error = socketBuilder.error;
    }

    public SocketBean() {
        result = "";
        error = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        if (result != null)
            this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        if (error != null)
            this.error = error;
    }


    public static class SocketBuilder {
        private int type;
        private String result;
        private String error;

        public SocketBuilder setType(int type) {
            this.type = type;
            return this;
        }

        public SocketBuilder setResult(String result) {
            this.result = result;
            return this;
        }

        public SocketBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public SocketBean build() {
            return new SocketBean(this);
        }
    }

}
