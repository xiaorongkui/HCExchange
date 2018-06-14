package com.hacai.exchange.bean;

import java.util.List;

/**
 * Created by lenovo on 2018/1/12.
 */

public class CapitalRecodeInfo extends BaseResultEntry {


    /**
     * obj : {"datas":[{"createDate":1515681106000,"money":"5.00","prefix":"+",
     * "typeName":"线上充值"},{"createDate":1515683472000,"money":"500000.00","prefix":"+",
     * "typeName":"线上充值"},{"createDate":1515694380000,"money":"200.00","prefix":"冻结",
     * "typeName":"提现申请"},{"createDate":1515694504000,"money":"200.00","prefix":"冻结",
     * "typeName":"提现申请"},{"createDate":1515694583000,"money":"500.00","prefix":"冻结",
     * "typeName":"提现申请"},{"createDate":1515694865000,"money":"100.00","prefix":"冻结",
     * "typeName":"提现申请"},{"createDate":1515695308000,"money":"503.00","prefix":"冻结",
     * "typeName":"提现申请"},{"createDate":1515697572000,"money":"2000.00","prefix":"-",
     * "typeName":"投标成功"},{"createDate":1515757724000,"money":"200.00","prefix":"冻结",
     * "typeName":"提现申请"}],"pageBean":{"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":9}}
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
         * datas : [{"createDate":1515681106000,"money":"5.00","prefix":"+","typeName":"线上充值"},
         * {"createDate":1515683472000,"money":"500000.00","prefix":"+","typeName":"线上充值"},
         * {"createDate":1515694380000,"money":"200.00","prefix":"冻结","typeName":"提现申请"},
         * {"createDate":1515694504000,"money":"200.00","prefix":"冻结","typeName":"提现申请"},
         * {"createDate":1515694583000,"money":"500.00","prefix":"冻结","typeName":"提现申请"},
         * {"createDate":1515694865000,"money":"100.00","prefix":"冻结","typeName":"提现申请"},
         * {"createDate":1515695308000,"money":"503.00","prefix":"冻结","typeName":"提现申请"},
         * {"createDate":1515697572000,"money":"2000.00","prefix":"-","typeName":"投标成功"},
         * {"createDate":1515757724000,"money":"200.00","prefix":"冻结","typeName":"提现申请"}]
         * pageBean : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":9}
         */

        private PageBeanBean pageBean;
        private List<DatasBean> datas;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class PageBeanBean {
            /**
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 10
             * totalCount : 9
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

        public static class DatasBean {
            /**
             * createDate : 1515681106000
             * money : 5.00
             * prefix : +
             * typeName : 线上充值
             */

            private long createDate;
            private String money;
            private String prefix;
            private String typeName;

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }
        }
    }
}
