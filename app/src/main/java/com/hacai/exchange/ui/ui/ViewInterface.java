package com.hacai.exchange.ui.ui;

/**
 * 加载界面的接口，处理各种显示状态
 *
 * @param
 */
public interface ViewInterface<T> {

    /**
     * @param errorMsg 网络请求失败的返回结果
     * @param code     网络请求失败的返回码
     * @param tag      网络请求标记，作为返回
     */
    void onError(String errorMsg, String code, int tag);//界面加载出错

    /**
     * @param response 网络请求返回结果
     * @param tag      网络请求标记，作为返回
     */
    void onSuccess(T response, int tag);//界面加载成功

}
