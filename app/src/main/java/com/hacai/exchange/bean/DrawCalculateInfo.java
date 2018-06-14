package com.hacai.exchange.bean;

/**
 * Created by lenovo on 2018/1/11.
 */

public class DrawCalculateInfo extends BaseResultEntry {


    /**
     * obj : {"msg":"您当月已提现次数为0次,还在免费次数中,未收取任何手续费","realMoney":"100.00"}
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
         * msg : 您当月已提现次数为0次,还在免费次数中,未收取任何手续费
         * realMoney : 100.00
         */

        private String msg;
        private String realMoney;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getRealMoney() {
            return realMoney;
        }

        public void setRealMoney(String realMoney) {
            this.realMoney = realMoney;
        }
    }
}
