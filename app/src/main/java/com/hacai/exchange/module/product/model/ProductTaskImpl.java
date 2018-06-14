package com.hacai.exchange.module.product.model;

import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.ProductService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by dove on 2017/1/19.
 */
public class ProductTaskImpl implements ProductInterface.IProductModel {
    private RxAppCompatActivity activity;

    public ProductTaskImpl(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getProductList(final Map<String, Object> maps, HttpOnNextListener listener, boolean
            isShowProgress) {
        BaseApi baseapi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                ProductService productService = retrofit.create(ProductService.class);
                return productService.getProductList(maps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(isShowProgress);

        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }
}
