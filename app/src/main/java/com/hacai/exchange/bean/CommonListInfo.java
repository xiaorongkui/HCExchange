package com.hacai.exchange.bean;

/**
 * Created by lenovo on 2017/12/14.
 */

public class CommonListInfo {
    public String title;
    public String content;

    public CommonListInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
