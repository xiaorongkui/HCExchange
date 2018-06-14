package com.hacai.exchange.bean;

import java.util.List;

/**
 * Created by lenovo on 2018/1/3.
 */

public class InvestmentRecodeInfo extends BaseResultEntry {


    /**
     * obj : {"pageBean":{"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":1},
     * "userTenderList":[{"apr":"18.00","borrowAccount":"100000","borrowName":"哈财新手标",
     * "borrowStatus":1,"borrowStatusShow":"等待复审","createDate":1515666630000,"interest":"35.00",
     * "tenderAccount":"10000","tenderid":61870}],"waitPrincipal":10000,"waitProfit":35}
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
         * pageBean : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":1}
         * userTenderList : [{"apr":"18.00","borrowAccount":"100000","borrowName":"哈财新手标",
         * "borrowStatus":1,"borrowStatusShow":"等待复审","createDate":1515666630000,
         * "interest":"35.00","tenderAccount":"10000","tenderid":61870}]
         * waitPrincipal : 10000.0
         * waitProfit : 35.0
         */

        private PageBeanBean pageBean;
        private double waitPrincipal;
        private double waitProfit;
        private List<UserTenderListBean> userTenderList;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public double getWaitPrincipal() {
            return waitPrincipal;
        }

        public void setWaitPrincipal(double waitPrincipal) {
            this.waitPrincipal = waitPrincipal;
        }

        public double getWaitProfit() {
            return waitProfit;
        }

        public void setWaitProfit(double waitProfit) {
            this.waitProfit = waitProfit;
        }

        public List<UserTenderListBean> getUserTenderList() {
            return userTenderList;
        }

        public void setUserTenderList(List<UserTenderListBean> userTenderList) {
            this.userTenderList = userTenderList;
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

        public static class UserTenderListBean {
            /**
             * apr : 18.00
             * borrowAccount : 100000
             * borrowName : 哈财新手标
             * borrowStatus : 1
             * borrowStatusShow : 等待复审
             * createDate : 1515666630000
             * interest : 35.00
             * tenderAccount : 10000
             * tenderid : 61870
             */

            private String apr;
            private String borrowAccount;
            private String borrowName;
            private int borrowStatus;
            private String borrowStatusShow;
            private long createDate;
            private String interest;
            private String tenderAccount;
            private int tenderid;

            public String getApr() {
                return apr;
            }

            public void setApr(String apr) {
                this.apr = apr;
            }

            public String getBorrowAccount() {
                return borrowAccount;
            }

            public void setBorrowAccount(String borrowAccount) {
                this.borrowAccount = borrowAccount;
            }

            public String getBorrowName() {
                return borrowName;
            }

            public void setBorrowName(String borrowName) {
                this.borrowName = borrowName;
            }

            public int getBorrowStatus() {
                return borrowStatus;
            }

            public void setBorrowStatus(int borrowStatus) {
                this.borrowStatus = borrowStatus;
            }

            public String getBorrowStatusShow() {
                return borrowStatusShow;
            }

            public void setBorrowStatusShow(String borrowStatusShow) {
                this.borrowStatusShow = borrowStatusShow;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getTenderAccount() {
                return tenderAccount;
            }

            public void setTenderAccount(String tenderAccount) {
                this.tenderAccount = tenderAccount;
            }

            public int getTenderid() {
                return tenderid;
            }

            public void setTenderid(int tenderid) {
                this.tenderid = tenderid;
            }
        }
    }
}
