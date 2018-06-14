package com.hacai.exchange.module.myAccount.model;

import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.MyAccountService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by dove on 2017/1/19.
 */
public class MyAccountTaskImpl implements MyAccountInterface.IMyAccountModel {
    private RxAppCompatActivity activity;

    public MyAccountTaskImpl(RxAppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void getAccountInformation(final Map<String, Object> maps, final HttpOnNextListener
            listener, boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getAccountInformation(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getInvestmentRecode(final Map<String, Object> maps, HttpOnNextListener listener,
                                    boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getInvestmentRecode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void recharge(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.recharge(maps);
            }
        };
        //        baseapi.setBaseUrl("http://192.168.59.103:8888/");
        //        baseapi.setGsonConverterFactory(ScalarsConverterFactory.create());
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


    @Override
    public void getBankInfo(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getBankInfo(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getBankChannelList(final Map<String, Object> maps, HttpOnNextListener listener,
                                   boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getBankChannelList(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void setExchangePwd(final Map<String, Object> maps, HttpOnNextListener listener,
                               boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.setExchangePwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void reviseExchangePwd(final Map<String, Object> maps, HttpOnNextListener listener,
                                  boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.reviseExchangePwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void forgetExchangePwd(final Map<String, Object> maps, HttpOnNextListener listener,
                                  boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.forgetExchangePwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


    @Override
    public void checkWithDraw(final Map<String, Object> maps, HttpOnNextListener listener,
                              boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.checkWithDraw(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void calculateWithDrawMoney(final Map<String, Object> maps, HttpOnNextListener
            listener, boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.calculateWithDrawMoney(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void submitWithDraw(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.submitWithDraw(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getCapitalRecode(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getCapitalRecode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getRiskLevel(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                return myAccountService.getRiskLevel(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


}
