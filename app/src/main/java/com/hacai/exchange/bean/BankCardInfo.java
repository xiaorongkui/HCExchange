package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2018/1/5.
 */

public class BankCardInfo extends BaseResultEntry {


    /**
     * obj : {"ableMoney":"50170.00","bankId":"ICBC","bankLimit":"本次支付上限5000元，日限额20万",
     * "bankName":"工商银行","cardId":"330719197901021826","cardNo":"6222081208002936743",
     * "logo":"/data/image/1.png","payCode":"1256","phone":"15088212599","realName":"徐建英",
     * "registerTime":"20160226212219","status":1,"userId":"55","userIdNo":"330719197901021826",
     * "userRealName":"徐建英"}
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
         * ableMoney : 50170.00
         * bankId : ICBC
         * bankLimit : 本次支付上限5000元，日限额20万
         * bankName : 工商银行
         * cardId : 330719197901021826
         * cardNo : 6222081208002936743
         * logo : /data/image/1.png
         * payCode : 1256
         * phone : 15088212599
         * realName : 徐建英
         * registerTime : 20160226212219
         * status : 1
         * userId : 55
         * userIdNo : 330719197901021826
         * userRealName : 徐建英
         */

        private String ableMoney;
        private String bankId;
        private String bankLimit;
        private String bankName;
        private String cardId;
        private String cardNo;
        private String img;
        private String payCode;
        private String phone;
        private String realName;
        private String registerTime;
        private int status;
        private String userId;
        private String userIdNo;
        private String userRealName;

        protected ObjBean(Parcel in) {
            ableMoney = in.readString();
            bankId = in.readString();
            bankLimit = in.readString();
            bankName = in.readString();
            cardId = in.readString();
            cardNo = in.readString();
            img = in.readString();
            payCode = in.readString();
            phone = in.readString();
            realName = in.readString();
            registerTime = in.readString();
            status = in.readInt();
            userId = in.readString();
            userIdNo = in.readString();
            userRealName = in.readString();
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

        public String getAbleMoney() {
            return ableMoney;
        }

        public void setAbleMoney(String ableMoney) {
            this.ableMoney = ableMoney;
        }

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public String getBankLimit() {
            return bankLimit;
        }

        public void setBankLimit(String bankLimit) {
            this.bankLimit = bankLimit;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getLogo() {
            return img;
        }

        public void setLogo(String logo) {
            this.img = logo;
        }

        public String getPayCode() {
            return payCode;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserIdNo() {
            return userIdNo;
        }

        public void setUserIdNo(String userIdNo) {
            this.userIdNo = userIdNo;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ableMoney);
            dest.writeString(bankId);
            dest.writeString(bankLimit);
            dest.writeString(bankName);
            dest.writeString(cardId);
            dest.writeString(cardNo);
            dest.writeString(img);
            dest.writeString(payCode);
            dest.writeString(phone);
            dest.writeString(realName);
            dest.writeString(registerTime);
            dest.writeInt(status);
            dest.writeString(userId);
            dest.writeString(userIdNo);
            dest.writeString(userRealName);
        }
    }
}
