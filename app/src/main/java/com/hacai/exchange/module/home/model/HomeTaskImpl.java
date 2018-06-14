package com.hacai.exchange.module.home.model;

import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.HomeService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by dove on 2017/1/19.
 */
public class HomeTaskImpl implements HomeInterface.IHomeModel {
    private RxAppCompatActivity activity;

    public HomeTaskImpl(RxAppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void showHomeData(final Map<String, Object> maps, final HttpOnNextListener listener,
                             boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.showHomeData(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getProductDetail(final Map<String, Object> maps, HttpOnNextListener listener,
                                 boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.getProductDetail(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getProductBidRecode(final Map<String, Object> maps, HttpOnNextListener listener,
                                    boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.getProductBidRecode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void checkBuyCondition(final Map<String, Object> maps, HttpOnNextListener listener,
                                  boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.checkBuyCondition(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void paymentProduct(final Map<String, Object> maps, HttpOnNextListener listener,
                               boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.paymentProduct(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getProductSummary(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                return homeService.getProductSummary(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


}
