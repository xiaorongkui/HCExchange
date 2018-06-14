package com.hacai.exchange.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/1/2.
 */

public class AccountInfo extends BaseResultEntry implements Serializable{


    /**
     * obj : {"ableMoney":0,"headPic":"","isBind":0,"repaymentMoney":0,
     * "repaymentTime":1514873592993,"totalMoney":0,"userName":"15549370360","waitCapital":0,
     * "waitInterest":0}
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
         * ableMoney : 0.0
         * headPic :
         * isBind : 0
         * repaymentMoney : 0
         * repaymentTime : 1514873592993
         * totalMoney : 0.0
         * userName : 15549370360
         * waitCapital : 0.0
         * waitInterest : 0.0
         */

        private double ableMoney;
        private String headPic;
        private int isBind;
        private int isPaypwd;//0代表未设置交易密码，1代表已设置
        private double repaymentMoney;
        private long repaymentTime;
        private double totalMoney;
        private String userName;
        private double waitCapital;
        private double waitInterest;

        public int getIsPaypwd() {
            return isPaypwd;
        }

        public void setIsPaypwd(int isPaypwd) {
            this.isPaypwd = isPaypwd;
        }

        public double getAbleMoney() {
            return ableMoney;
        }

        public void setAbleMoney(double ableMoney) {
            this.ableMoney = ableMoney;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getIsBind() {
            return isBind;
        }

        public void setIsBind(int isBind) {
            this.isBind = isBind;
        }

        public double getRepaymentMoney() {
            return repaymentMoney;
        }

        public void setRepaymentMoney(double repaymentMoney) {
            this.repaymentMoney = repaymentMoney;
        }

        public long getRepaymentTime() {
            return repaymentTime;
        }

        public void setRepaymentTime(long repaymentTime) {
            this.repaymentTime = repaymentTime;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public double getWaitCapital() {
            return waitCapital;
        }

        public void setWaitCapital(double waitCapital) {
            this.waitCapital = waitCapital;
        }

        public double getWaitInterest() {
            return waitInterest;
        }

        public void setWaitInterest(double waitInterest) {
            this.waitInterest = waitInterest;
        }
    }
}
