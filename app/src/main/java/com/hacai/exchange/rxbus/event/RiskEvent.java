package com.hacai.exchange.rxbus.event;

/**
 * Created by lenovo on 2018/1/18.
 */

public class RiskEvent {
    boolean isSuccess;
    String level;

    public RiskEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
