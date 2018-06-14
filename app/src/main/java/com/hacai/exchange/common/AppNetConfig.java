package com.hacai.exchange.common;

import com.hacai.exchange.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 * 旧测试环境地址：https://www.hacai360.com/app
 * 生产接口地址：https://api.zifae.com/zifae/api/
 * 生产h5资源地址：https://api.zifae.com/app-resources/
 * 新测试环境地址：http://140.207.53.166:8080/zifae_pre/
 */

public class AppNetConfig {
    public static final String urlPath = "web/app/";//测试环境环境
    //    public static final String urlPath = "app/";//正式环境

    public static final String baseUrl = "http://192.168.59.104:8081/";//测试环境
    //public static final String baseUrl = "http://192.168.124.31:8091/";
    public static final String baseUrl_product = "http://app.hacai360.com/";
    //    public static final String baseUrl = baseUrl_product;

    public static final String recharge_success = "http://app.hacai360.com/app/success";
    public static final String recharge_failed = "http://app.hacai360.com/app/fail";

    //.....所有的项目当中接口的请求url全部配置在这里.....//
    private static Map<String, Object> baseFiel = new HashMap<>();

    public static Map<String, Object> getBaseMaps() {
        if (!baseFiel.isEmpty()) baseFiel.clear();
        baseFiel.put("token", CommonUtil.getTokenId());
        return baseFiel;
    }
}
