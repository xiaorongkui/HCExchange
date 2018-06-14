package com.hacai.exchange.module.login.presenter;

import android.app.Activity;

import com.hacai.exchange.base.BasePresenter;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.model.LoginTaskImpl;
import com.hacai.exchange.network.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * Created by dove on 2017/11/19.
 */
public class LoginPresenter extends BasePresenter<LoginInterface.ILoginView> implements
        LoginInterface.ILoginPresenter {

    private LoginTaskImpl loginTask;
    private Activity activity;

    public LoginPresenter(RxAppCompatActivity activity) {
        loginTask = new LoginTaskImpl(activity);
        this.activity = activity;
    }

    @Override
    public void login(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.login(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
                loginTask.saveLoginReponse(o);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).getMessage(), (
                        (HandlerException.ResponeThrowable) e).getCode(), tag);
                mView.clearPassword();
            }
        }, isShowProgress);
    }

    @Override
    public void getLoginPwdCode(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.getLoginPwdCode(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void checkForgetLoginCode(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        loginTask.checkForgetLoginCode(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void getRegisterPwdCode(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        loginTask.getRegisterPwdCode(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void resetPwd(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.resetPwd(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void register(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.register(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void loginOut(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.loginOut(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void getDrawWithCode(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.getDrawWithCode(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void modifyLoginPwd(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.modifyLoginPwd(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void getExchangePwdCode(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        loginTask.getExchangePwdCode(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void setForgetExchangePwd(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        loginTask.setForgetExchangePwd(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

    @Override
    public void checkUpdate(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        loginTask.checkUpdate(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException
                        .ResponeThrowable) e).getCode(), tag);
            }
        }, isShowProgress);
    }

}
