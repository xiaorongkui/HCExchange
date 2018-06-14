package com.hacai.exchange.bean;

/**
 * Created by lenovo on 2017/12/25.
 */

public class TextShowInfo {
    public String str;
    public int textColorId;
    public int textSizeId;

    public TextShowInfo(String str, int textColorId, int textSizeId) {
        this.str = str;
        this.textColorId = textColorId;
        this.textSizeId = textSizeId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getTextColorId() {
        return textColorId;
    }

    public void setTextColorId(int textColorId) {
        this.textColorId = textColorId;
    }

    public int getTextSizeId() {
        return textSizeId;
    }

    public void setTextSizeId(int textSizeId) {
        this.textSizeId = textSizeId;
    }
}
