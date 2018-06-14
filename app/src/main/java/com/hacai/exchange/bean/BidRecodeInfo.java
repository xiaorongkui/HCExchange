package com.hacai.exchange.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/29.
 */

public class BidRecodeInfo extends BaseResultEntry {


    /**
     * obj : {"amount":"76000","apr":"18.00","borrowTenderItemList":[{"ableMoney":"496102.00",
     * "account":"2000","autoTenderStatus":"0","clientType":1,"createDate":1515697572000,
     * "id":348,"money":"2000","status":"1","tenderId":61876,"username":"155****0360"},
     * {"ableMoney":"18009.90","account":"1000","autoTenderStatus":"0","clientType":1,
     * "createDate":1515694749000,"id":348,"money":"1000","status":"1","tenderId":61875,
     * "username":"173****6839"},{"ableMoney":"59000.00","account":"1000","autoTenderStatus":"0",
     * "clientType":0,"createDate":1515682909000,"id":348,"money":"1000","status":"1",
     * "tenderId":61874,"username":"132****2784"},{"ableMoney":"50000.00","account":"50000",
     * "autoTenderStatus":"0","clientType":1,"createDate":1515682555000,"id":348,"money":"50000",
     * "status":"1","tenderId":61873,"username":"151****4836"},{"ableMoney":"50000.00",
     * "account":"10000","autoTenderStatus":"0","clientType":0,"createDate":1515682288000,
     * "id":348,"money":"10000","status":"1","tenderId":61872,"username":"151****1527"},
     * {"ableMoney":"1000.00","account":"1000","autoTenderStatus":"0","clientType":0,
     * "createDate":1515681179000,"id":348,"money":"1000","status":"1","tenderId":61871,
     * "username":"134****5112"},{"account":"10000","autoTenderStatus":"0","clientType":1,
     * "createDate":1515666630000,"id":348,"money":"10000","status":"1","tenderId":61870},
     * {"account":"1000","autoTenderStatus":"0","clientType":1,"createDate":1515603485000,
     * "id":348,"money":"1000","status":"1","tenderId":61869}],"pageBean":{"pageCount":1,
     * "pageNumber":1,"pageSize":10,"totalCount":8},"tenderSum":6}
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
         * amount : 76000
         * apr : 18.00
         * borrowTenderItemList : [{"ableMoney":"496102.00","account":"2000",
         * "autoTenderStatus":"0","clientType":1,"createDate":1515697572000,"id":348,
         * "money":"2000","status":"1","tenderId":61876,"username":"155****0360"},
         * {"ableMoney":"18009.90","account":"1000","autoTenderStatus":"0","clientType":1,
         * "createDate":1515694749000,"id":348,"money":"1000","status":"1","tenderId":61875,
         * "username":"173****6839"},{"ableMoney":"59000.00","account":"1000",
         * "autoTenderStatus":"0","clientType":0,"createDate":1515682909000,"id":348,
         * "money":"1000","status":"1","tenderId":61874,"username":"132****2784"},
         * {"ableMoney":"50000.00","account":"50000","autoTenderStatus":"0","clientType":1,
         * "createDate":1515682555000,"id":348,"money":"50000","status":"1","tenderId":61873,
         * "username":"151****4836"},{"ableMoney":"50000.00","account":"10000",
         * "autoTenderStatus":"0","clientType":0,"createDate":1515682288000,"id":348,
         * "money":"10000","status":"1","tenderId":61872,"username":"151****1527"},
         * {"ableMoney":"1000.00","account":"1000","autoTenderStatus":"0","clientType":0,
         * "createDate":1515681179000,"id":348,"money":"1000","status":"1","tenderId":61871,
         * "username":"134****5112"},{"account":"10000","autoTenderStatus":"0","clientType":1,
         * "createDate":1515666630000,"id":348,"money":"10000","status":"1","tenderId":61870},
         * {"account":"1000","autoTenderStatus":"0","clientType":1,"createDate":1515603485000,
         * "id":348,"money":"1000","status":"1","tenderId":61869}]
         * pageBean : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":8}
         * tenderSum : 6
         */

        private String amount;
        private String apr;
        private PageBeanBean pageBean;
        private int tenderSum;
        private List<BorrowTenderItemListBean> borrowTenderItemList;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApr() {
            return apr;
        }

        public void setApr(String apr) {
            this.apr = apr;
        }

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public int getTenderSum() {
            return tenderSum;
        }

        public void setTenderSum(int tenderSum) {
            this.tenderSum = tenderSum;
        }

        public List<BorrowTenderItemListBean> getBorrowTenderItemList() {
            return borrowTenderItemList;
        }

        public void setBorrowTenderItemList(List<BorrowTenderItemListBean> borrowTenderItemList) {
            this.borrowTenderItemList = borrowTenderItemList;
        }

        public static class PageBeanBean {
            /**
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 10
             * totalCount : 8
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

        public static class BorrowTenderItemListBean {
            /**
             * ableMoney : 496102.00
             * account : 2000
             * autoTenderStatus : 0
             * clientType : 1
             * createDate : 1515697572000
             * id : 348
             * money : 2000
             * status : 1
             * tenderId : 61876
             * username : 155****0360
             */

            private String ableMoney;
            private String account;
            private String autoTenderStatus;
            private int clientType;
            private long createDate;
            private int id;
            private String money;
            private String status;
            private int tenderId;
            private String username;

            public String getAbleMoney() {
                return ableMoney;
            }

            public void setAbleMoney(String ableMoney) {
                this.ableMoney = ableMoney;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAutoTenderStatus() {
                return autoTenderStatus;
            }

            public void setAutoTenderStatus(String autoTenderStatus) {
                this.autoTenderStatus = autoTenderStatus;
            }

            public int getClientType() {
                return clientType;
            }

            public void setClientType(int clientType) {
                this.clientType = clientType;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getTenderId() {
                return tenderId;
            }

            public void setTenderId(int tenderId) {
                this.tenderId = tenderId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
