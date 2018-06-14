package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2018/1/11.
 */

public class CheckWithDrawInfo extends BaseResultEntry {


    /**
     * obj : {"bankId":"ABC1","bankName":"中国农业银行","cardNo":"6212261807000183217",
     * "cashMoney":"90000.00","phone":"18818018912","realname":"杨涵华"}
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
         * bankId : ABC1
         * bankName : 中国农业银行
         * cardNo : 6212261807000183217
         * cashMoney : 90000.00
         * phone : 18818018912
         * realname : 杨涵华
         */

        private String bankId;
        private String bankName;
        private String cardNo;
        private String cashMoney;
        private String phone;
        private String realname;

        protected ObjBean(Parcel in) {
            bankId = in.readString();
            bankName = in.readString();
            cardNo = in.readString();
            cashMoney = in.readString();
            phone = in.readString();
            realname = in.readString();
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

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCashMoney() {
            return cashMoney;
        }

        public void setCashMoney(String cashMoney) {
            this.cashMoney = cashMoney;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bankId);
            dest.writeString(bankName);
            dest.writeString(cardNo);
            dest.writeString(cashMoney);
            dest.writeString(phone);
            dest.writeString(realname);
        }
    }
}
