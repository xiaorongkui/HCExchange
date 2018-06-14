package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2017/12/29.
 */

public class CheckPayConditionInfo extends BaseResultEntry {

    /**
     * obj : {"balance":"323900","baseRate":8.64,"giveRate":1.08,"login":0,"lowestAccount":"100",
     * "mostAccount":"200000","rate":9.72,"timeLimit":"30"}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }


    public static class ObjBean implements Parcelable{
        /**
         * balance : 323900
         * baseRate : 8.64
         * giveRate : 1.08
         * login : 0
         * lowestAccount : 100
         * mostAccount : 200000
         * rate : 9.72
         * timeLimit : 30
         */

        private String balance;
        private double baseRate;
        private double giveRate;
        private String login;
        private String lowestAccount;
        private String mostAccount;
        private double rate;
        private String timeLimit;
        private double ableMoney;
        private int isBound;
        private int isPaypwd;

        public int getIsBound() {
            return isBound;
        }

        public void setIsBound(int isBound) {
            this.isBound = isBound;
        }

        public int getIsPaypwd() {
            return isPaypwd;
        }

        public void setIsPaypwd(int isPaypwd) {
            this.isPaypwd = isPaypwd;
        }

        protected ObjBean(Parcel in) {
            balance = in.readString();
            baseRate = in.readDouble();
            giveRate = in.readDouble();
            login = in.readString();
            lowestAccount = in.readString();
            mostAccount = in.readString();
            rate = in.readDouble();
            timeLimit = in.readString();
            ableMoney=in.readDouble();
            isBound=in.readInt();
            isPaypwd=in.readInt();
        }

        public double getAbleMoney() {
            return ableMoney;
        }

        public void setAbleMoney(double ableMoney) {
            this.ableMoney = ableMoney;
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

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(double baseRate) {
            this.baseRate = baseRate;
        }

        public double getGiveRate() {
            return giveRate;
        }

        public void setGiveRate(double giveRate) {
            this.giveRate = giveRate;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getLowestAccount() {
            return lowestAccount;
        }

        public void setLowestAccount(String lowestAccount) {
            this.lowestAccount = lowestAccount;
        }

        public String getMostAccount() {
            return mostAccount;
        }

        public void setMostAccount(String mostAccount) {
            this.mostAccount = mostAccount;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(balance);
            dest.writeDouble(baseRate);
            dest.writeDouble(giveRate);
            dest.writeString(login);
            dest.writeString(lowestAccount);
            dest.writeString(mostAccount);
            dest.writeDouble(rate);
            dest.writeString(timeLimit);
            dest.writeDouble(ableMoney);
            dest.writeInt(isBound);
            dest.writeInt(isPaypwd);
        }
    }
}
