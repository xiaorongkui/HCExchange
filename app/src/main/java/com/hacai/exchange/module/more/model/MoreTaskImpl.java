package com.hacai.exchange.module.more.model;

import com.hacai.exchange.bean.LoginReponse;
import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.LoginService;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by dove on 2017/1/19.
 */
public class MoreTaskImpl implements MoreInterface.IMoreModel {
    public static final String TAG = MoreTaskImpl.class.getSimpleName();
    private RxAppCompatActivity activity;

    public MoreTaskImpl(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void saveLoginReponse(Object o) {
        if (o != null) {
            LoginReponse loginReponse = null;
            try {
                loginReponse = (LoginReponse) o;
                LogUtil.i(loginReponse.toString());
            } catch (Exception e) {
                LogUtil.i("HomeTaskImpl loginReponse exception");
                e.printStackTrace();
            }
           //保存登录信息
//            CommonUtil.setUserInfo(loginReponse);
        }
    }

    @Override
    public void login(final Map<String, Object> maps, final HttpOnNextListener listener,boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.userLogin(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


    @Override
    public void resetPwd(final Map<String, Object> maps, HttpOnNextListener listener) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.resetPwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(true);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    //注册
    @Override
    public void register(final Map<String, Object> maps, HttpOnNextListener listener) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.register(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(true);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

}
