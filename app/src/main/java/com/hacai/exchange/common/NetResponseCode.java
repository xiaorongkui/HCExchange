package com.hacai.exchange.common;

public class NetResponseCode {
    public final static String HMC_SUCCESS= "R0001";//成功
    ////////////////异常消息////////////////
    public final static String HMC_LOGIN ="DL0001";//请先登录
    public final static String HMC_NETWORK_ERROR ="-00000";//没有网络
    public final static String HMC_UNBIND_CARD_ERROR ="M00010";//未绑卡
    public static final String HMC_NO_PAYMENT_PWD = "S0004";
    public final static String HMC_NO_RISK ="-0300003" ;//您还未进行风险评估！todo

    public final static String HMC_KEY = "-2000";//appKey为必传项
    public final static String HMC_UNKNOWN = "-90002";//服务器异常
    public final static String HMC_CANT_BUYSELF = "-0300001";//不能购买自己的转让！
    public final static String HMC_RISK_LOWLEVEL = "-0300004";//您的风险评估等级未达到产品要求！
    public final static String HMC_SETPAY_PASSWORD ="-0300005" ;//请先设置支付密码！
    public final static String HMC_BINDCARD_1 ="-0300006" ;//请先绑定银行卡！
    public final static String HMC_BINDCARD_2 = "-0300007";//请先绑定银行卡！
    public final static String HMC_COMDIRM_BIND ="-0300008" ;//已绑定预指定银行，请先确认绑定！
    public final static String HMC_AVAILABLE_MONEY ="-0300009" ;//可用余额为零，请先充值！
    public final static String HMC_NEW_ONLY ="-0300011" ;//该产品仅限余新手客户！

}