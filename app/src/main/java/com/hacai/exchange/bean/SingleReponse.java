package com.hacai.exchange.bean;

/**
 * Created by Rongkui.xiao on 2017/4/12.
 *
 * @description
 */

public class SingleReponse extends BaseResultEntry{
    private Object result;
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public SingleReponse() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
