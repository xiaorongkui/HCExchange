package com.hacai.exchange.network;

import com.google.gson.Gson;
import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.common.NetResponseCode;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.utils.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lenovo on 2018/1/10.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseCode = "";
        String msg = "";
        BaseResultEntry httpResult = null;
        try {
            String response = value.string();
            //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
            httpResult = gson.fromJson(response, BaseResultEntry.class);
            responseCode = httpResult.getResponseCode();
            msg = httpResult.getResponseMessage();
            if (responseCode.equals(NetResponseCode.HMC_SUCCESS)) {
                //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
                T fromJson = null;
                try {
                    fromJson = gson.fromJson(response, type);
                    LogUtil.i("正常解析="+fromJson);
                } catch (Exception e) {
                    LogUtil.i("数据转换异常"+e.getMessage());
                   return null;
                }
                return fromJson;
            } else {
                LogUtil.i("异常垂涎HandlerException");
                //抛一个自定义ResultException 传入失败时候的状态码，和信息
                throw new HandlerException.ResponeThrowable(msg, responseCode);
            }
        } finally {
            value.close();
        }

    }

}
