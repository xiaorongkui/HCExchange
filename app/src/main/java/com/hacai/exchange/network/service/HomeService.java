package com.hacai.exchange.network.service;

import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.bean.BidRecodeInfo;
import com.hacai.exchange.bean.HomeProductInfo;
import com.hacai.exchange.bean.ProductDetailInfo;
import com.hacai.exchange.bean.CheckPayConditionInfo;
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

public interface HomeService {
    /**
     * 首页数据的接口
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "index/show")
    Observable<HomeProductInfo> showHomeData(@FieldMap Map<String, Object> maps);

    /**
     * 产品详情数据的接口
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrow/borrowDetail")
    Observable<ProductDetailInfo> getProductDetail(@FieldMap Map<String, Object> maps);


    /**
     * 投标记录
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrow/tender/detail")
    Observable<BidRecodeInfo> getProductBidRecode(@FieldMap Map<String, Object> maps);

    /**
     * 检查产品的购买条件的接口
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrowTender/popup")
    Observable<CheckPayConditionInfo> checkBuyCondition(@FieldMap Map<String, Object> maps);

    /**
     * 产品的购买接口
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrowTender/tenderDo")
    Observable<BaseResultEntry> paymentProduct(@FieldMap Map<String, Object> maps);

    //项目概述
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "borrow/borrowDesc")
    Observable<String> getProductSummary(@FieldMap Map<String, Object> maps);

}
