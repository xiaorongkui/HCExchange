package com.hacai.exchange.module.home.presenter;

import android.app.Activity;

import com.hacai.exchange.base.BasePresenter;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.module.home.model.HomeInterface;
import com.hacai.exchange.module.home.model.HomeTaskImpl;
import com.hacai.exchange.network.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * Created by dove on 2017/11/19.
 */
public class HomePresenter extends BasePresenter<HomeInterface.IHomeView> implements
        HomeInterface.IHomePresenter {

    private HomeTaskImpl homeTask;
    private Activity activity;

    public HomePresenter(RxAppCompatActivity activity) {
        homeTask = new HomeTaskImpl(activity);
        this.activity = activity;
    }

    @Override
    public void showHomeData(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.showHomeData(maps, new HttpOnNextListener() {
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
    public void getProductDetail(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.getProductDetail(maps, new HttpOnNextListener() {
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
    public void getProductBidRecode(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.getProductBidRecode(maps, new HttpOnNextListener() {
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
    public void checkBuyCondition(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.checkBuyCondition(maps, new HttpOnNextListener() {
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
    public void paymentProduct(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.paymentProduct(maps, new HttpOnNextListener() {
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
    public void getProductSummary(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        homeTask.getProductSummary(maps, new HttpOnNextListener() {
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
