package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2018/1/17.
 */

public class LoanAgreementInfo implements Parcelable {
    private String lender;
    private String number1;
    private String borrower;
    private String number2;
    private String proName;
    private String loanMoney;
    private String loanTime;
    private String interestTime;
    private String loanRate;

    public LoanAgreementInfo() {
    }

    protected LoanAgreementInfo(Parcel in) {
        lender = in.readString();
        number1 = in.readString();
        borrower = in.readString();
        number2 = in.readString();
        proName = in.readString();
        loanMoney = in.readString();
        loanTime = in.readString();
        interestTime = in.readString();
        loanRate = in.readString();
    }

    public static final Creator<LoanAgreementInfo> CREATOR = new Creator<LoanAgreementInfo>() {
        @Override
        public LoanAgreementInfo createFromParcel(Parcel in) {
            return new LoanAgreementInfo(in);
        }

        @Override
        public LoanAgreementInfo[] newArray(int size) {
            return new LoanAgreementInfo[size];
        }
    };

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(String interestTime) {
        this.interestTime = interestTime;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lender);
        dest.writeString(number1);
        dest.writeString(borrower);
        dest.writeString(number2);
        dest.writeString(proName);
        dest.writeString(loanMoney);
        dest.writeString(loanTime);
        dest.writeString(interestTime);
        dest.writeString(loanRate);
    }
}
