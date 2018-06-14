package com.hacai.exchange.module.product.model;

import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.ui.ui.ViewInterface;

import java.util.Map;

/**
 * Created by Rongkui.xiao on 2017/4/27.
 *
 * @description
 */

public class ProductInterface {
    /**
     * 登录的View接口
     */
    public interface  IProductView extends ViewInterface {
    }

/*====================连接层===========================*/

    public interface IProductPresenter {
        void getProductList(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了
    }

/*====================业务层===========================*/

    /**
     * 登录的接口
     */
    interface IProductModel {
        /*========登录=========*/

        void getProductList(Map<String, Object> maps, final HttpOnNextListener listener, boolean isShowProgress);//登录请求

    }

}
