package com.hacai.exchange.bean;

/**
 * Created by lenovo on 2018/1/19.
 */

public class UpdateInfo extends BaseResultEntry {


    /**
     * obj : {"isForce":"0","updateDesc":"本次更新说明\r\n1、界面优化\r\n2、bug修改","url":"http://api
     * .hacai360.com/HCExchange.apk","version":"V1.0.0"}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * isForce : 0
         * updateDesc : 本次更新说明
         1、界面优化
         2、bug修改
         * url : http://api.hacai360.com/HCExchange.apk
         * version : V1.0.0
         */

        private String isForce;
        private String updateDesc;
        private String url;
        private String version;

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public String getUpdateDesc() {
            return updateDesc;
        }

        public void setUpdateDesc(String updateDesc) {
            this.updateDesc = updateDesc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
