package com.hacai.exchange.module.myAccount.presenter;

import android.app.Activity;

import com.hacai.exchange.base.BasePresenter;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.model.MyAccountTaskImpl;
import com.hacai.exchange.network.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * Created by dove on 2017/11/19.
 */
public class MyAccountPresenter extends BasePresenter<MyAccountInterface.IMyAccountView>
        implements MyAccountInterface.IMyAccountPresenter {

    private MyAccountTaskImpl myAccountTask;
    private Activity activity;

    public MyAccountPresenter(RxAppCompatActivity activity) {
        myAccountTask = new MyAccountTaskImpl(activity);
        this.activity = activity;
    }

    @Override
    public void getAccountInformation(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        myAccountTask.getAccountInformation(maps, new HttpOnNextListener() {
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
    public void getInvestmentRecode(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        myAccountTask.getInvestmentRecode(maps, new HttpOnNextListener() {
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
    public void recharge(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.recharge(maps, new HttpOnNextListener() {
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
    public void getBankInfo(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.getBankInfo(maps, new HttpOnNextListener() {
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
    public void getBankChannelList(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        myAccountTask.getBankChannelList(maps, new HttpOnNextListener() {
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
    public void setExchangePwd(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.setExchangePwd(maps, new HttpOnNextListener() {
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
    public void reviseExchangePwd(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.reviseExchangePwd(maps, new HttpOnNextListener() {
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
    public void forgetExchangePwd(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.forgetExchangePwd(maps, new HttpOnNextListener() {
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
    public void checkWithDraw(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.checkWithDraw(maps, new HttpOnNextListener() {
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
    public void calculateWithDrawMoney(Map<String, Object> maps, final int tag, boolean
            isShowProgress) {
        myAccountTask.calculateWithDrawMoney(maps, new HttpOnNextListener() {
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
    public void submitWithDraw(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.submitWithDraw(maps, new HttpOnNextListener() {
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
    public void getCapitalRecode(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.getCapitalRecode(maps, new HttpOnNextListener() {
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
    public void getRiskLevel(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        myAccountTask.getRiskLevel(maps, new HttpOnNextListener() {
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
