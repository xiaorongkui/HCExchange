package com.hacai.exchange.network.service;

import com.hacai.exchange.bean.ProductInfo;
import com.hacai.exchange.common.AppNetConfig;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Rongkui.xiao on 2017/3/17.
 *
 * @description
 */

public interface ProductService {
    /**
     * 首页数据的接口
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrow/borrowList")
    Observable<ProductInfo> getProductList(@FieldMap Map<String, Object> maps);

}
