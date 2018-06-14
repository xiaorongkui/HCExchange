package com.hacai.exchange.rxbus.event;

/**
 * Created by Rongkui.xiao on 2017/8/9.
 *
 * @description
 */
//充值
public class RechargeOrDrawEvent {
    private boolean isSuccess;
    private String message;

    public RechargeOrDrawEvent(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
