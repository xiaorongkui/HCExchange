package com.hacai.exchange.rxbus.event;

/**
 * Created by Rongkui.xiao on 2017/5/15.
 *
 * @description
 */

public class LoginEvent {
    private boolean isLogin;
    private boolean isInterceptor;//屏蔽后面的事件

    public LoginEvent(boolean isLogin, boolean isInterceptor) {
        this.isLogin = isLogin;
        this.isInterceptor = isInterceptor;
    }

    public boolean isInterceptor() {
        return isInterceptor;
    }

    public void setInterceptor(boolean interceptor) {
        isInterceptor = interceptor;
    }

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
