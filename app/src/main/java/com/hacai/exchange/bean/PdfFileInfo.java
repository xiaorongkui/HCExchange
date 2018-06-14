package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2017/12/27.
 */

public class PdfFileInfo implements Parcelable{
    /**
     * fileName : 提测注意事项
     * filePath : temp/20170425/ghtmrhma6791493088658967.png
     */

    private String fileName;
    private String filePath;

    public PdfFileInfo(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    protected PdfFileInfo(Parcel in) {
        fileName = in.readString();
        filePath = in.readString();
    }

    public static final Creator<PdfFileInfo> CREATOR = new Creator<PdfFileInfo>() {
        @Override
        public PdfFileInfo createFromParcel(Parcel in) {
            return new PdfFileInfo(in);
        }

        @Override
        public PdfFileInfo[] newArray(int size) {
            return new PdfFileInfo[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(filePath);
    }
}
