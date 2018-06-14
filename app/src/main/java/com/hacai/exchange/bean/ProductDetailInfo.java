package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2017/12/28.
 */

public class ProductDetailInfo extends BaseResultEntry {

    /**
     * obj : {"fullDate":1516949575000,"giveRate":2,"canAccount":"100.0","putDate":1516863175000,
     * "tenderCount":0,"cipalDate":1516949575000,"id":353,"jixiTime":1516863175000,
     * "arriveDate":1516949575000,"buyTime":1516863175000,"nowDate":1516946928776,"baseRate":16,
     * "capitalGuarantee":"test","name":"哈财00A普通标","account":"100","borrowTime":"1",
     * "payType":"分期付息，到期本息","borrowContent":"测试","light":1,"lowAccount":"100"}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Parcelable {
        /**
         * fullDate : 1516949575000
         * giveRate : 2.0
         * canAccount : 100.0
         * putDate : 1516863175000
         * tenderCount : 0
         * cipalDate : 1516949575000
         * id : 353
         * jixiTime : 1516863175000
         * arriveDate : 1516949575000
         * buyTime : 1516863175000
         * nowDate : 1516946928776
         * baseRate : 16.0
         * capitalGuarantee : test
         * name : 哈财00A普通标
         * account : 100
         * borrowTime : 1
         * payType : 分期付息，到期本息
         * borrowContent : 测试
         * light : 1
         * lowAccount : 100
         */

        private long fullDate;
        private double giveRate;
        private String canAccount;
        private long putDate;
        private int tenderCount;
        private long cipalDate;
        private int id;
        private long jixiTime;
        private long arriveDate;
        private long buyTime;
        private long nowDate;
        private double baseRate;
        private String capitalGuarantee;
        private String name;
        private String account;
        private String borrowTime;
        private String payType;
        private String borrowContent;
        private int light;
        private String lowAccount;

        protected ObjBean(Parcel in) {
            fullDate = in.readLong();
            giveRate = in.readDouble();
            canAccount = in.readString();
            putDate = in.readLong();
            tenderCount = in.readInt();
            cipalDate = in.readLong();
            id = in.readInt();
            jixiTime = in.readLong();
            arriveDate = in.readLong();
            buyTime = in.readLong();
            nowDate = in.readLong();
            baseRate = in.readDouble();
            capitalGuarantee = in.readString();
            name = in.readString();
            account = in.readString();
            borrowTime = in.readString();
            payType = in.readString();
            borrowContent = in.readString();
            light = in.readInt();
            lowAccount = in.readString();
        }

        public static final Creator<ObjBean> CREATOR = new Creator<ObjBean>() {
            @Override
            public ObjBean createFromParcel(Parcel in) {
                return new ObjBean(in);
            }

            @Override
            public ObjBean[] newArray(int size) {
                return new ObjBean[size];
            }
        };

        public long getFullDate() {
            return fullDate;
        }

        public void setFullDate(long fullDate) {
            this.fullDate = fullDate;
        }

        public double getGiveRate() {
            return giveRate;
        }

        public void setGiveRate(double giveRate) {
            this.giveRate = giveRate;
        }

        public String getCanAccount() {
            return canAccount;
        }

        public void setCanAccount(String canAccount) {
            this.canAccount = canAccount;
        }

        public long getPutDate() {
            return putDate;
        }

        public void setPutDate(long putDate) {
            this.putDate = putDate;
        }

        public int getTenderCount() {
            return tenderCount;
        }

        public void setTenderCount(int tenderCount) {
            this.tenderCount = tenderCount;
        }

        public long getCipalDate() {
            return cipalDate;
        }

        public void setCipalDate(long cipalDate) {
            this.cipalDate = cipalDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getJixiTime() {
            return jixiTime;
        }

        public void setJixiTime(long jixiTime) {
            this.jixiTime = jixiTime;
        }

        public long getArriveDate() {
            return arriveDate;
        }

        public void setArriveDate(long arriveDate) {
            this.arriveDate = arriveDate;
        }

        public long getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(long buyTime) {
            this.buyTime = buyTime;
        }

        public long getNowDate() {
            return nowDate;
        }

        public void setNowDate(long nowDate) {
            this.nowDate = nowDate;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(double baseRate) {
            this.baseRate = baseRate;
        }

        public String getCapitalGuarantee() {
            return capitalGuarantee;
        }

        public void setCapitalGuarantee(String capitalGuarantee) {
            this.capitalGuarantee = capitalGuarantee;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBorrowTime() {
            return borrowTime;
        }

        public void setBorrowTime(String borrowTime) {
            this.borrowTime = borrowTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getBorrowContent() {
            return borrowContent;
        }

        public void setBorrowContent(String borrowContent) {
            this.borrowContent = borrowContent;
        }

        public int getLight() {
            return light;
        }

        public void setLight(int light) {
            this.light = light;
        }

        public String getLowAccount() {
            return lowAccount;
        }

        public void setLowAccount(String lowAccount) {
            this.lowAccount = lowAccount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(fullDate);
            dest.writeDouble(giveRate);
            dest.writeString(canAccount);
            dest.writeLong(putDate);
            dest.writeInt(tenderCount);
            dest.writeLong(cipalDate);
            dest.writeInt(id);
            dest.writeLong(jixiTime);
            dest.writeLong(arriveDate);
            dest.writeLong(buyTime);
            dest.writeLong(nowDate);
            dest.writeDouble(baseRate);
            dest.writeString(capitalGuarantee);
            dest.writeString(name);
            dest.writeString(account);
            dest.writeString(borrowTime);
            dest.writeString(payType);
            dest.writeString(borrowContent);
            dest.writeInt(light);
            dest.writeString(lowAccount);
        }
    }
}
