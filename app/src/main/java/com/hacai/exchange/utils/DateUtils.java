package com.hacai.exchange.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by max on 16/3/3.
 */
public class DateUtils {

    /**
     * 判断日期是否相等
     *
     * @param first 单位毫秒
     * @param now   单位毫秒
     * @return
     */
    public static boolean dateIsSame(long first, long now) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_first = new Date(first);
        String dFirst = sdf.format(date_first);
        Date date_now = new Date(now);
        String dNow = sdf.format(date_now);

        return dFirst.equals(dNow);
    }

    /**
     * @param senconds 单位秒
     * @return
     */
    public static String secondToDate(int senconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date((long) senconds * 1000);
        return sdf.format(date);
    }

    /**
     * @param minis 单位毫秒
     * @return
     */
    public static String millisToDateTime_1(long minis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(minis);
        return sdf.format(date);
    }
    /**
     * @param minis 单位毫秒
     * @return
     */
    public static String millisToDateTime_3(long minis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(minis);
        return sdf.format(date);
    }
    /**
     * @param minis 单位毫秒
     * @return
     */
    public static String millisToDateTime_2(long minis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(minis);
        return sdf.format(date);
    }

    /**
     * @param senconds 单位秒
     * @return
     */
    public static String secondToTime(int senconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date((long) senconds * 1000);
        return sdf.format(date);
    }

    /**
     * @param millis 单位秒
     * @return
     */
    public static String millisToTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(millis * 1000);
        return sdf.format(date);
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String getFileNameCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }


    public static String formatDateOnlyYesterday(long time) {


        long one_day_millis = 1000 * 60 * 60 * 24;

        long date = System.currentTimeMillis() - time * 1000;
        if (date <= one_day_millis) {
            SimpleDateFormat today = new SimpleDateFormat("HH:mm");
            return today.format(time * 1000);
        }

        if (date > one_day_millis && date <= one_day_millis * 2) {
            SimpleDateFormat today = new SimpleDateFormat("HH:mm");
            return "昨天 " + today.format(time * 1000);
        }
        if (date >= one_day_millis * 2 && date < one_day_millis * 365) {
            SimpleDateFormat today = new SimpleDateFormat("MM-dd HH:mm");
            return today.format(time * 1000);
        }

        if (date >= one_day_millis * 365) {
            SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return today.format(time * 1000);
        }
        return "";

    }
    public static void main(String[] args){
        System.out.print(millisToDateTime_2(1479906218000l));
        System.out.print("\n"+millisToDateTime_1(1479906218000l));
    }

}
