package com.hacai.exchange.network.service;

import com.hacai.exchange.bean.AccountInfo;
import com.hacai.exchange.bean.BankCardInfo;
import com.hacai.exchange.bean.BankChannelInfo;
import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.bean.CapitalRecodeInfo;
import com.hacai.exchange.bean.CheckWithDrawInfo;
import com.hacai.exchange.bean.DrawCalculateInfo;
import com.hacai.exchange.bean.InvestmentRecodeInfo;
import com.hacai.exchange.bean.RechargeInfo;
import com.hacai.exchange.bean.RiskInfo;
import com.hacai.exchange.common.AppNetConfig;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Rongkui.xiao on 2017/3/17.
 *
 * @description 我的账户数据接口
 */

public interface MyAccountService {
    /**
     * 获取账户信息
     *
     * @return RxJava 对象
     */
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/index")
    Observable<AccountInfo> getAccountInformation(@FieldMap Map<String, Object> maps);

    //投资记录
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "mytender/mytz")
    Observable<InvestmentRecodeInfo> getInvestmentRecode(@FieldMap Map<String, Object> maps);

    //充值、绑卡
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "payment/payDo")
    Observable<RechargeInfo> recharge(@FieldMap Map<String, Object> maps);


    //获取绑卡信息
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "payment/rechargeTo")
    Observable<BankCardInfo> getBankInfo(@FieldMap Map<String, Object> maps);

    //获取绑卡渠道列表
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "payment/banckList")
    Observable<BankChannelInfo> getBankChannelList(@FieldMap Map<String, Object> maps);

    //设置交易密码
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/setSafePwd")
    Observable<BankChannelInfo> setExchangePwd(@FieldMap Map<String, Object> maps);

    //修改交易密码
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/safePwdUpdate")
    Observable<BankChannelInfo> reviseExchangePwd(@FieldMap Map<String, Object> maps);

    //忘记交易密码
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/safePwdUpdate")
    Observable<BankChannelInfo> forgetExchangePwd(@FieldMap Map<String, Object> maps);

    //6.10	跳转提现页面
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "cash/cashTo")
    Observable<CheckWithDrawInfo> checkWithDraw(@FieldMap Map<String, Object> maps);

    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "cash/cashChange")
    Observable<DrawCalculateInfo> calculateWithDrawMoney(@FieldMap Map<String, Object> maps);

    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "cash/cashSave")
    Observable<BaseResultEntry> submitWithDraw(@FieldMap Map<String, Object> maps);

    //获取所有的协议接口
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/risk")
    Observable<String> getRiskhtml(@FieldMap Map<String, Object> maps); //获取所有的协议接口

    //风险评估
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/registerRisk")
    Observable<BaseResultEntry> sendRiskLevel(@FieldMap Map<String, Object> maps);

    //关于我们
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/aboutUs")
    Observable<String> getAboutUshtml(@FieldMap Map<String, Object> maps);

    //帮助中心
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/help")
    Observable<String> getHelphtml(@FieldMap Map<String, Object> maps);

    //注册协议
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/register")
    Observable<String> getRegisterAgreement(@FieldMap Map<String, Object> maps);

    //绑卡协议
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/card")
    Observable<String> getBindCardAgreement(@FieldMap Map<String, Object> maps);

    //借款协议
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "agreement/loan")
    Observable<String> getLoanAgreement(@FieldMap Map<String, Object> maps);

    //资金记录
    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "mytender/myAccountDetail")
    Observable<CapitalRecodeInfo> getCapitalRecode(@FieldMap Map<String, Object> maps);//资金记录

    @FormUrlEncoded
    @POST(AppNetConfig.urlPath + "usercenter/isRisk")
    Observable<RiskInfo> getRiskLevel(@FieldMap Map<String, Object> maps);
}
