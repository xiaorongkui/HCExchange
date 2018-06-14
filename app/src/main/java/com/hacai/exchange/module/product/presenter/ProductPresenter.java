package com.hacai.exchange.module.product.presenter;

import android.app.Activity;

import com.hacai.exchange.base.BasePresenter;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.module.product.model.ProductInterface;
import com.hacai.exchange.module.product.model.ProductTaskImpl;
import com.hacai.exchange.network.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * Created by dove on 2017/11/19.
 */
public class ProductPresenter extends BasePresenter<ProductInterface.IProductView> implements ProductInterface.IProductPresenter {

    private Activity activity;
    private final ProductTaskImpl productTask;

    public ProductPresenter(RxAppCompatActivity activity) {
        productTask = new ProductTaskImpl(activity);
        this.activity = activity;
    }

    @Override
    public void getProductList(Map<String, Object> maps, final int tag, boolean isShowProgress) {
        productTask.getProductList(maps, new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(((HandlerException.ResponeThrowable) e).message, ((HandlerException.ResponeThrowable)
                        e).getCode(), tag);
            }
        }, isShowProgress);
    }
}
