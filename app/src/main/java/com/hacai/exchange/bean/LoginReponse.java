package com.hacai.exchange.bean;

import java.io.Serializable;

/**
 * Created by dove on 2017/1/19.
 */
public class LoginReponse extends BaseResultEntry implements Serializable {


    /**
     * rcd : R0001
     * rmg : null
     * userId : 76216
     * username : 18818018912
     * token : bb514ef2926328aae7b72c3201fd8665
     * realStatus : 0
     */

    private int userId;
    private String username;
    private String token;
    private int realStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(int realStatus) {
        this.realStatus = realStatus;
    }
}
