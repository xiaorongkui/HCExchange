package com.hacai.exchange.bean;

import java.util.List;

/**
 * Created by lenovo on 2018/1/4.
 */

public class BankChannelInfo extends BaseResultEntry {


    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * createDate : 1351822546000
         * description : 3
         本次支付上限5000元，日限额20万
         * grade : 1
         * id : 1256
         * isEnabled : true
         * keyValue : ICBC
         * logo : /data/image/1.png
         * modifyDate : 1488857718000
         * name : 工商银行
         * orderList : 1
         * parentId : 25
         * path : 25,1256
         * sign : account_bank300
         */

        private long createDate;
        private String description;
        private int grade;
        private int id;
        private boolean isEnabled;
        private String keyValue;
        private String img;
        private long modifyDate;
        private String name;
        private int orderList;
        private int parentId;
        private String path;
        private String sign;

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsEnabled() {
            return isEnabled;
        }

        public void setIsEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }

        public String getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public String getLogo() {
            return img;
        }

        public void setLogo(String logo) {
            this.img = logo;
        }

        public long getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(long modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderList() {
            return orderList;
        }

        public void setOrderList(int orderList) {
            this.orderList = orderList;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
