package com.hacai.exchange.rxbus.event;

/**
 * Created by lenovo on 2018/1/9.
 */

public class PasswordEvent {
    public boolean isSuccess;
    public int tag;//1 表示设置交易密码

    public PasswordEvent(boolean isSuccess, int tag) {
        this.isSuccess = isSuccess;
        this.tag = tag;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
