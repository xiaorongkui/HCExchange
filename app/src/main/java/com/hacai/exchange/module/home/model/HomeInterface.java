package com.hacai.exchange.module.home.model;

import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.ui.ui.ViewInterface;

import java.util.Map;

/**
 * Created by Rongkui.xiao on 2017/4/27.
 *
 * @description
 */

public class HomeInterface {

    public interface IHomeView extends ViewInterface<Object> {
    }

/*====================连接层===========================*/

    public interface IHomePresenter {
        void showHomeData(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void getProductDetail(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void getProductBidRecode(Map<String, Object> maps, int tag, boolean isShowProgress);
        //桥梁就是登录了

        void checkBuyCondition(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void paymentProduct(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void getProductSummary(Map<String, Object> maps, int tag, boolean isShowProgress);//项目概述
    }

/*====================业务层===========================*/


    interface IHomeModel {

        void showHomeData(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);//登录请求

        void getProductDetail(Map<String, Object> maps, final HttpOnNextListener listener,
                              boolean isShowProgress);

        void getProductBidRecode(Map<String, Object> maps, final HttpOnNextListener listener,
                                 boolean isShowProgress);

        void checkBuyCondition(Map<String, Object> maps, final HttpOnNextListener listener,
                               boolean isShowProgress);

        void paymentProduct(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void getProductSummary(Map<String, Object> maps, final HttpOnNextListener listener,
                               boolean isShowProgress);

    }

}
