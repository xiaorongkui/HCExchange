package com.hacai.exchange.network.service;

import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.bean.CodeInfo;
import com.hacai.exchange.bean.LoginReponse;
import com.hacai.exchange.bean.UpdateInfo;
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

public interface LoginService {
    /**
     * 用户登录的接口
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "login/doLogin")
    //    @POST("zifae/api/ZS0100003")
    Observable<LoginReponse> userLogin(@FieldMap Map<String, Object> maps);

    /**
     * 重置登录密码,验证码获取
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "sms/sendPCodeb")
    Observable<CodeInfo> getLoginPwdCode(@FieldMap Map<String, Object> maps);

    /**
     * 校验验证码马
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "user/checkPhoneCode")
    Observable<BaseResultEntry> checkForgetLoginCode(@FieldMap Map<String, Object> maps);

    /**
     * 重置登录密码
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "user/lostLoginPwd")
    Observable<BaseResultEntry> resetPwd(@FieldMap Map<String, Object> maps);

    /**
     * 注册验证码
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "sms/sendPhoneCode")
    Observable<CodeInfo> getRegisterPwdCode(@FieldMap Map<String, Object> maps);

    /**
     * 注册
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "reg/submitreg")
    Observable<BaseResultEntry> register(@FieldMap Map<String, Object> maps);

    /**
     * 退出登录
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "login/logout")
    Observable<BaseResultEntry> loginOut(@FieldMap Map<String, Object> maps);

    /**
     * 获取提现验证码
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "sms/sendCashCode")
    Observable<CodeInfo> getDrawWithCode(@FieldMap Map<String, Object> maps);

    /**
     * 修改登录密码
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/updatePwd")
    Observable<BaseResultEntry> modifyLoginPwd(@FieldMap Map<String, Object> maps);

    /**
     * 重置交易密码,验证码获取
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "sms/payPwdCode")
    Observable<CodeInfo> getExchangePwdCode(@FieldMap Map<String, Object> maps);

    /**
     * 重置交易密码
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "user/lostPayPwd")
    Observable<CodeInfo> setForgetExchangePwd(@FieldMap Map<String, Object> maps);

    /**
     * 下载apk文件
     * 响应体的数据用流的形式返回 @Streaming
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "index/version")
    Observable<UpdateInfo> checkUpdate(@FieldMap Map<String, Object> maps);
}
