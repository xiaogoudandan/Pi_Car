package com.william_zhang.pi_car.bean;

/**
 * Created by william_zhang on 2018/4/1.
 */

/**
 * socket传递的json对象
 */
public class CarMassage {
    private String massage;
    private String error;

    public CarMassage(CarMassageBuilder carMassageBuilder) {
        this.massage = carMassageBuilder.massage;
        this.error = carMassageBuilder.error;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


    public static class CarMassageBuilder {
        private String massage;
        private String error;

        public CarMassageBuilder setMessage(String message) {
            this.massage = message;
            return this;
        }

        public CarMassageBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public CarMassage build() {
            return new CarMassage(this);
        }

    }
}
