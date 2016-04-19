package com.joey.keepbook.utils;

import android.text.format.Time;

/**
 * Created by Joey on 2016/2/22.
 */
public class DateManger {
    private static DateManger dateManger;
    private static Time time=new Time();


    //"%Y-%m-%d %H:%M:%S" pattern
    public static final String Y_M_D_H_M = "%Y-%m-%d %H:%M";

    private DateManger() {
        time.set(System.currentTimeMillis());
    }

    /**
     * 单例
     */
    public static DateManger getInstance() {
        if (dateManger == null) {
            dateManger = new DateManger();
        }
        return dateManger;
    }

    /**
     * 获取 月 0-11月
     */
    public int getMonth() {
        return time.month;
    }

    /**
     * 获取 日
     */
    public int getDay() {
        return time.monthDay;
    }

    /**
     * 设置当前 时间
     */
    public void setToday(long date) {//在服务中更新时间
        time.set(date);
    }



    //格式化时间
    public static String format(long date) {
        return format(null, date);
    }

    public static String format(String pattern, long date) {
        if (date <= 0) {
            return null;
        }
        if (pattern == null || pattern.equals("")) {
            pattern = Y_M_D_H_M;
        }
        Time t=new Time();
        t.set(date);
        return t.format(pattern);
    }

    //是否为今月
    public static boolean isThisMonth(long date) {
        if (date <= 0) {
            return false;
        }
        Time temp = new Time();
        temp.set(date);
        Time t=new Time();
        t.set(System.currentTimeMillis());
        return temp.year == t.year && temp.month == t.month;
    }

    //是否为今天
    public static boolean isToday(long date) {
        if (date <= 0) {
            return false;
        }
        Time temp = new Time();
        temp.set(date);
        Time t=new Time();
        t.set(System.currentTimeMillis());
        return temp.year == t.year && temp.month == t.month && temp.monthDay == t.monthDay;
    }


    //第几周 1-60周
    public static int getWeekNum(long date) {
        Time lTime = new Time();
        lTime.set(date);
        return lTime.monthDay / 7 + lTime.month * 5 + 1;
    }

    //获取 号
    public static int getThisDay(long date) {
        Time lTime = new Time();
        lTime.set(date);
        return lTime.monthDay;
    }

    //获取 月
    public static int getThisMonth(long date) {
        Time lTime = new Time();
        lTime.set(date);
        return lTime.month;
    }

    /**
     * 释放内存
     */
    public static void clear(){
        dateManger=null;
    }
}
