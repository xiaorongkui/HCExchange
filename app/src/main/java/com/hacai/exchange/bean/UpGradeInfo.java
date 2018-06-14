package com.hacai.exchange.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rongkui.xiao on 2017/5/17.
 *
 * @description
 */

public class UpGradeInfo extends BaseResultEntry implements Parcelable{

    /**
     * compulsionFlag : 1
     * code : 1
     * flag : 0
     * memo : 2121212
     * packageVersion : 1.0.4
     * appCode : zifae
     * clock : {"currentTimeInMillis":1497950757571,"currentDate":"2017-06-20 17:25:57"}
     * version : 0
     * url : http://140.207.53.166:8080/jenkins/job/projects-app-android-v1.0.0/ws/app/build/outputs/apk
     * /ZSExchange_V1.0_baidu.apk
     * upgradeFlag : 1
     * serialVersionUID : 8968972584033203030
     * createdDt : 2017-07-01 14:14:52
     * packageSystem : ANDROID
     * name : zifae
     * updatedDt : 2017-07-01 14:14:56
     * id : 1
     */

    private String compulsionFlag;
    private String code;
    private String flag;
    private String memo;
    private String packageVersion;
    private String appCode;
    private ClockBean clock;
    private int version;
    private String url;
    private String upgradeFlag;
    private long serialVersionUID;
    private String createdDt;
    private String packageSystem;
    private String name;
//    private String updatedDt;
    private int id;

    public UpGradeInfo() {
    }

    protected UpGradeInfo(Parcel in) {
        compulsionFlag = in.readString();
        code = in.readString();
        flag = in.readString();
        memo = in.readString();
        packageVersion = in.readString();
        appCode = in.readString();
        version = in.readInt();
        url = in.readString();
        upgradeFlag = in.readString();
        serialVersionUID = in.readLong();
        createdDt = in.readString();
        packageSystem = in.readString();
        name = in.readString();
//        updatedDt = in.readString();
        id = in.readInt();
    }

    public static final Creator<UpGradeInfo> CREATOR = new Creator<UpGradeInfo>() {
        @Override
        public UpGradeInfo createFromParcel(Parcel in) {
            return new UpGradeInfo(in);
        }

        @Override
        public UpGradeInfo[] newArray(int size) {
            return new UpGradeInfo[size];
        }
    };

    public String getCompulsionFlag() {
        return compulsionFlag;
    }

    public void setCompulsionFlag(String compulsionFlag) {
        this.compulsionFlag = compulsionFlag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public ClockBean getClock() {
        return clock;
    }

    public void setClock(ClockBean clock) {
        this.clock = clock;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpgradeFlag() {
        return upgradeFlag;
    }

    public void setUpgradeFlag(String upgradeFlag) {
        this.upgradeFlag = upgradeFlag;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getPackageSystem() {
        return packageSystem;
    }

    public void setPackageSystem(String packageSystem) {
        this.packageSystem = packageSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getUpdatedDt() {
//        return updatedDt;
//    }

//    public void setUpdatedDt(String updatedDt) {
//        this.updatedDt = updatedDt;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(compulsionFlag);
        dest.writeString(code);
        dest.writeString(flag);
        dest.writeString(memo);
        dest.writeString(packageVersion);
        dest.writeString(appCode);
        dest.writeInt(version);
        dest.writeString(url);
        dest.writeString(upgradeFlag);
        dest.writeLong(serialVersionUID);
        dest.writeString(createdDt);
        dest.writeString(packageSystem);
        dest.writeString(name);
//        dest.writeString(updatedDt);
        dest.writeInt(id);
    }

    public static class ClockBean implements Parcelable {
        /**
         * currentTimeInMillis : 1497950757571
         * currentDate : 2017-06-20 17:25:57
         */

        private long currentTimeInMillis;
        private String currentDate;

        protected ClockBean(Parcel in) {
            currentTimeInMillis = in.readLong();
            currentDate = in.readString();
        }

        public static final Creator<ClockBean> CREATOR = new Creator<ClockBean>() {
            @Override
            public ClockBean createFromParcel(Parcel in) {
                return new ClockBean(in);
            }

            @Override
            public ClockBean[] newArray(int size) {
                return new ClockBean[size];
            }
        };

        public long getCurrentTimeInMillis() {
            return currentTimeInMillis;
        }

        public void setCurrentTimeInMillis(long currentTimeInMillis) {
            this.currentTimeInMillis = currentTimeInMillis;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(currentTimeInMillis);
            dest.writeString(currentDate);
        }
    }

    @Override
    public String toString() {
        return "UpGradeInfo{" + "compulsionFlag='" + compulsionFlag + '\'' + ", code='" + code + '\'' + ", flag='" +
                flag + '\'' + ", memo='" + memo + '\'' + ", packageVersion='" + packageVersion + '\'' + ", appCode='"
                + appCode + '\'' + ", clock=" + clock + ", version=" + version + ", url='" + url + '\'' + ", " +
                "upgradeFlag='" + upgradeFlag + '\'' + ", serialVersionUID=" + serialVersionUID + ", createdDt='" +
                createdDt + '\'' + ", packageSystem='" + packageSystem + '\'' + ", name='" + name + '\'' + ", id=" + id + '}';
    }
}
