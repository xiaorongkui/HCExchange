package com.hacai.exchange.ui.widget.bean;

public class RollItem implements IRollItem {
    private String imagetUrl;
    private int resId;
    private String titleText;

    @Override
    public String getImagetUrl() {
        return imagetUrl;
    }

    @Override
    public String getTitleText() {
        return titleText;
    }

    @Override
    public int getResId() {
        return resId;
    }

    public RollItem(String imagetUrl, int resId, String titleText) {
        this.imagetUrl = imagetUrl;
        this.resId = resId;
        this.titleText = titleText;
    }

    public RollItem(String imagetUrl, String titleText) {
        this.imagetUrl = imagetUrl;
        this.titleText = titleText;
    }

    public RollItem(String imagetUrl) {
        this.imagetUrl = imagetUrl;
    }
}
