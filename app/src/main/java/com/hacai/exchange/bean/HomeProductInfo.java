package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lenovo on 2017/12/22.
 */

public class HomeProductInfo extends BaseResultEntry {


    /**
     * responseCode  : R0001
     * responseMessage  : null
     * indexBorrow : {"id":306,"name":"测试新手标","timeLimit":"7","apr":14.4,"status":7,
     * "showBorrowStatus":"已还完","schedule":null,"lowestAccount":null,"balance":"0"}
     * indexHotBorrows : [{"id":308,"name":"哈财A018","timeLimit":"30","apr":9.72,"status":1,
     * "showBorrowStatus":"募集中","schedule":null,"lowestAccount":null,"balance":"323900"},
     * {"id":308,"name":"哈财A018","timeLimit":"30","apr":9.72,"status":1,"showBorrowStatus":"募集中",
     * "schedule":null,"lowestAccount":null,"balance":"323900"},{"id":308,"name":"哈财A018",
     * "timeLimit":"30","apr":9.72,"status":1,"showBorrowStatus":"募集中","schedule":null,
     * "lowestAccount":null,"balance":"323900"}]
     * indexImageItemList : [{"rcd":"R0001","rmg":null,
     * "imageUrl":"/data/upfiles/images/201712/294d5948bc8e4361992b442195812993.jpg","type":1,
     * "typeTarget":"http://test/690.htm"},{"rcd":"R0001","rmg":null,
     * "imageUrl":"/data/upfiles/images/201712/22c179699f49420d866dd825d7bb3e30.png","type":1,
     * "typeTarget":"http://test/detail.do?id=21"},{"rcd":"R0001","rmg":null,
     * "imageUrl":"/data/upfiles/images/201712/437122e66ef246599a601d96bdaaaf51.jpg","type":1,
     * "typeTarget":"http://test/detail.do?id=13"}]
     * totalUserNum : null
     * totalTenderMoney : null
     * userFlg : 0
     */

    private IndexBorrowBean indexBorrow;
    private Object totalUserNum;
    private Object totalTenderMoney;
    private int userFlg;
    private List<IndexHotBorrowsBean> indexHotBorrows;
    private List<IndexImageItemListBean> indexImageItemList;

    public IndexBorrowBean getIndexBorrow() {
        return indexBorrow;
    }

    public void setIndexBorrow(IndexBorrowBean indexBorrow) {
        this.indexBorrow = indexBorrow;
    }

    public Object getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(Object totalUserNum) {
        this.totalUserNum = totalUserNum;
    }

    public Object getTotalTenderMoney() {
        return totalTenderMoney;
    }

    public void setTotalTenderMoney(Object totalTenderMoney) {
        this.totalTenderMoney = totalTenderMoney;
    }

    public int getUserFlg() {
        return userFlg;
    }

    public void setUserFlg(int userFlg) {
        this.userFlg = userFlg;
    }

    public List<IndexHotBorrowsBean> getIndexHotBorrows() {
        return indexHotBorrows;
    }

    public void setIndexHotBorrows(List<IndexHotBorrowsBean> indexHotBorrows) {
        this.indexHotBorrows = indexHotBorrows;
    }

    public List<IndexImageItemListBean> getIndexImageItemList() {
        return indexImageItemList;
    }

    public void setIndexImageItemList(List<IndexImageItemListBean> indexImageItemList) {
        this.indexImageItemList = indexImageItemList;
    }

    public static class IndexBorrowBean implements Parcelable {
        /**
         * id : 306
         * name : 测试新手标
         * timeLimit : 7
         * apr : 14.4
         * status : 7
         * showBorrowStatus : 已还完
         * schedule : null
         * lowestAccount : null
         * balance : 0
         */

        private String id;
        private String name;
        private String timeLimit;
        private double apr;
        private double givenRate;
        private double baseRate;
        private String status;
        private String showBorrowStatus;
        private String schedule;
        private String lowestAccount;
        private String balance;

        protected IndexBorrowBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            timeLimit = in.readString();
            apr = in.readDouble();
            givenRate = in.readDouble();
            baseRate = in.readDouble();
            status = in.readString();
            showBorrowStatus = in.readString();
            schedule = in.readString();
            lowestAccount = in.readString();
            balance = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(timeLimit);
            dest.writeDouble(apr);
            dest.writeDouble(givenRate);
            dest.writeDouble(baseRate);
            dest.writeString(status);
            dest.writeString(showBorrowStatus);
            dest.writeString(schedule);
            dest.writeString(lowestAccount);
            dest.writeString(balance);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<IndexBorrowBean> CREATOR = new Creator<IndexBorrowBean>() {
            @Override
            public IndexBorrowBean createFromParcel(Parcel in) {
                return new IndexBorrowBean(in);
            }

            @Override
            public IndexBorrowBean[] newArray(int size) {
                return new IndexBorrowBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public double getApr() {
            return apr;
        }

        public void setApr(double apr) {
            this.apr = apr;
        }

        public double getGivenRate() {
            return givenRate;
        }

        public void setGivenRate(double givenRate) {
            this.givenRate = givenRate;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(double baseRate) {
            this.baseRate = baseRate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShowBorrowStatus() {
            return showBorrowStatus;
        }

        public void setShowBorrowStatus(String showBorrowStatus) {
            this.showBorrowStatus = showBorrowStatus;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getLowestAccount() {
            return lowestAccount;
        }

        public void setLowestAccount(String lowestAccount) {
            this.lowestAccount = lowestAccount;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }

    public static class IndexHotBorrowsBean implements Parcelable {
        /**
         * id : 308
         * name : 哈财A018
         * timeLimit : 30
         * apr : 9.72
         * status : 1
         * showBorrowStatus : 募集中
         * schedule : null
         * lowestAccount : null
         * balance : 323900
         */

        private String id;
        private String name;
        private String timeLimit;
        private double apr;
        private double givenRate;
        private double baseRate;
        private String status;
        private String showBorrowStatus;
        private Object schedule;
        private Object lowestAccount;
        private String balance;

        protected IndexHotBorrowsBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            timeLimit = in.readString();
            apr = in.readDouble();
            givenRate = in.readDouble();
            baseRate = in.readDouble();
            status = in.readString();
            showBorrowStatus = in.readString();
            balance = in.readString();
        }

        public static final Creator<IndexHotBorrowsBean> CREATOR = new
                Creator<IndexHotBorrowsBean>() {
            @Override
            public IndexHotBorrowsBean createFromParcel(Parcel in) {
                return new IndexHotBorrowsBean(in);
            }

            @Override
            public IndexHotBorrowsBean[] newArray(int size) {
                return new IndexHotBorrowsBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public double getApr() {
            return apr;
        }

        public void setApr(double apr) {
            this.apr = apr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShowBorrowStatus() {
            return showBorrowStatus;
        }

        public void setShowBorrowStatus(String showBorrowStatus) {
            this.showBorrowStatus = showBorrowStatus;
        }

        public Object getSchedule() {
            return schedule;
        }

        public void setSchedule(Object schedule) {
            this.schedule = schedule;
        }

        public Object getLowestAccount() {
            return lowestAccount;
        }

        public void setLowestAccount(Object lowestAccount) {
            this.lowestAccount = lowestAccount;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public double getGivenRate() {
            return givenRate;
        }

        public void setGivenRate(double givenRate) {
            this.givenRate = givenRate;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(double baseRate) {
            this.baseRate = baseRate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(timeLimit);
            dest.writeDouble(apr);
            dest.writeDouble(baseRate);
            dest.writeDouble(givenRate);
            dest.writeString(status);
            dest.writeString(showBorrowStatus);
            dest.writeString(balance);
        }
    }

    public static class IndexImageItemListBean {
        /**
         * rcd : R0001
         * rmg : null
         * imageUrl : /data/upfiles/images/201712/294d5948bc8e4361992b442195812993.jpg
         * type : 1
         * typeTarget : http://test/690.htm
         */

        private String rcd;
        private Object rmg;
        private String imageUrl;
        private int type;
        private String typeTarget;

        public String getRcd() {
            return rcd;
        }

        public void setRcd(String rcd) {
            this.rcd = rcd;
        }

        public Object getRmg() {
            return rmg;
        }

        public void setRmg(Object rmg) {
            this.rmg = rmg;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeTarget() {
            return typeTarget;
        }

        public void setTypeTarget(String typeTarget) {
            this.typeTarget = typeTarget;
        }
    }
}
