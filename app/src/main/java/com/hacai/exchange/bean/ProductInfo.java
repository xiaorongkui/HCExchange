package com.hacai.exchange.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/27.
 */

public class ProductInfo extends BaseResultEntry {


    /**
     * obj : {"borrowItemList":[{"account":"100","apr":18,"balance":"100.0","baseApr":16,
     * "businessType":0,"giveApr":2,"id":353,"lowestAccount":"100","name":"哈财00A普通标",
     * "repaymentType":"1","repaymentTypeMsg":"一次性还本付息","schedule":"0","showBorrowStatus":"已过期",
     * "showBorrowType":"","status":1,"timeLimit":"1","type":"14"}],"pageBean":{"pageCount":1,
     * "pageNumber":1,"pageSize":10,"totalCount":1}}
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
         * borrowItemList : [{"account":"100","apr":18,"balance":"100.0","baseApr":16,
         * "businessType":0,"giveApr":2,"id":353,"lowestAccount":"100","name":"哈财00A普通标",
         * "repaymentType":"1","repaymentTypeMsg":"一次性还本付息","schedule":"0",
         * "showBorrowStatus":"已过期","showBorrowType":"","status":1,"timeLimit":"1","type":"14"}]
         * pageBean : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":1}
         */

        private PageBeanBean pageBean;
        private List<BorrowItemListBean> borrowItemList;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public List<BorrowItemListBean> getBorrowItemList() {
            return borrowItemList;
        }

        public void setBorrowItemList(List<BorrowItemListBean> borrowItemList) {
            this.borrowItemList = borrowItemList;
        }

        public static class PageBeanBean {
            /**
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 10
             * totalCount : 1
             */

            private int pageCount;
            private int pageNumber;
            private int pageSize;
            private int totalCount;

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }

        public static class BorrowItemListBean {
            /**
             * account : 100
             * apr : 18
             * balance : 100.0
             * baseApr : 16.0
             * businessType : 0
             * giveApr : 2.0
             * id : 353
             * lowestAccount : 100
             * name : 哈财00A普通标
             * repaymentType : 1
             * repaymentTypeMsg : 一次性还本付息
             * schedule : 0
             * showBorrowStatus : 已过期
             * showBorrowType :
             * status : 1
             * timeLimit : 1
             * type : 14
             */

            private String account;
            private int apr;
            private String balance;
            private double baseApr;
            private int businessType;
            private double giveApr;
            private int id;
            private String lowestAccount;
            private String name;
            private String repaymentType;
            private String repaymentTypeMsg;
            private String schedule;
            private String showBorrowStatus;
            private String showBorrowType;
            private String status;
            private String timeLimit;
            private String type;

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public int getApr() {
                return apr;
            }

            public void setApr(int apr) {
                this.apr = apr;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public double getBaseApr() {
                return baseApr;
            }

            public void setBaseApr(double baseApr) {
                this.baseApr = baseApr;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public double getGiveApr() {
                return giveApr;
            }

            public void setGiveApr(double giveApr) {
                this.giveApr = giveApr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLowestAccount() {
                return lowestAccount;
            }

            public void setLowestAccount(String lowestAccount) {
                this.lowestAccount = lowestAccount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRepaymentType() {
                return repaymentType;
            }

            public void setRepaymentType(String repaymentType) {
                this.repaymentType = repaymentType;
            }

            public String getRepaymentTypeMsg() {
                return repaymentTypeMsg;
            }

            public void setRepaymentTypeMsg(String repaymentTypeMsg) {
                this.repaymentTypeMsg = repaymentTypeMsg;
            }

            public String getSchedule() {
                return schedule;
            }

            public void setSchedule(String schedule) {
                this.schedule = schedule;
            }

            public String getShowBorrowStatus() {
                return showBorrowStatus;
            }

            public void setShowBorrowStatus(String showBorrowStatus) {
                this.showBorrowStatus = showBorrowStatus;
            }

            public String getShowBorrowType() {
                return showBorrowType;
            }

            public void setShowBorrowType(String showBorrowType) {
                this.showBorrowType = showBorrowType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTimeLimit() {
                return timeLimit;
            }

            public void setTimeLimit(String timeLimit) {
                this.timeLimit = timeLimit;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
