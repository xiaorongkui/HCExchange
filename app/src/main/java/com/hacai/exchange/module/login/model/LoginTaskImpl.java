package com.hacai.exchange.module.login.model;

import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.bean.LoginReponse;
import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.LoginService;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by dove on 2017/1/19.
 */
public class LoginTaskImpl implements LoginInterface.ILoginModel {
    public static final String TAG = LoginTaskImpl.class.getSimpleName();
    private RxAppCompatActivity activity;

    public LoginTaskImpl(RxAppCompatActivity activity) {
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
            //            保存登录信息
            CommonUtil.saveLoginInfo(loginReponse);
        }
    }

    @Override
    public void login(final Map<String, Object> maps, final HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable<? extends BaseResultEntry> getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.userLogin(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getLoginPwdCode(final Map<String, Object> maps, final HttpOnNextListener
            listener, boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.getLoginPwdCode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void checkForgetLoginCode(final Map<String, Object> maps, HttpOnNextListener listener,
                                     boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.checkForgetLoginCode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }


    @Override
    public void resetPwd(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.resetPwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getRegisterPwdCode(final Map<String, Object> maps, HttpOnNextListener listener,
                                   boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.getRegisterPwdCode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    //注册
    @Override
    public void register(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.register(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void loginOut(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.loginOut(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getDrawWithCode(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.getDrawWithCode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void modifyLoginPwd(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.modifyLoginPwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void getExchangePwdCode(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.getExchangePwdCode(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void setForgetExchangePwd(final Map<String, Object> maps, HttpOnNextListener listener,
                                     boolean isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.setForgetExchangePwd(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    public void checkUpdate(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                LoginService loginService = retrofit.create(LoginService.class);
                return loginService.checkUpdate(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

}
