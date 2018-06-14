package com.hacai.exchange.bean;

/**
 * Created by Rongkui.xiao on 2017/3/27.
 *
 * @description 网络请求的统一返回格式
 */

public class BaseResultEntry {

    /**
     * responseCode : R0001
     * responseMessage : 短信发送成功
     */

    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
