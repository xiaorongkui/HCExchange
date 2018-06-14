package com.hacai.exchange.module.more.presenter;

import android.app.Activity;

import com.hacai.exchange.base.BasePresenter;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.module.login.model.LoginTaskImpl;
import com.hacai.exchange.module.more.model.MoreInterface;
import com.hacai.exchange.network.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * Created by dove on 2017/11/19.
 */
public class MorePresenter extends BasePresenter<MoreInterface.IMoreView> implements MoreInterface
        .IMorePresenter {

    private LoginTaskImpl loginTask;
    private Activity activity;

    public MorePresenter(RxAppCompatActivity activity) {
        loginTask = new LoginTaskImpl(activity);
        this.activity = activity;
    }

    @Override
    public void login(Map<String, Object> maps, final int tag,boolean isShowProgress) {
        loginTask.login(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
                loginTask.saveLoginReponse(o);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException.ResponeThrowable)
                        e).getCode(), tag);
                mView.clearPassword();
            }
        },isShowProgress);
    }
}
