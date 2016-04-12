package com.joey.keepbook.utils;

import android.text.format.Time;

/**
 * Created by Joey on 2016/2/22.
 */
public class DateUtils {
    private static final int ERROR = -1;
    private static Time time=new Time();

    //"%Y-%m-%d %H:%M:%S" pattern
    public static final String H_M = "%H:%M";
    public static final String Y_M_D = "%Y-%m-%d";
    public static final String Y_M_D_H_M = "%Y-%m-%d %H:%M";

    //格式化时间
    public static String format(long date){
       return format(null,date);
    }
    public static String format(String pattern,long date){
        if (date<=0){
            return null;
        }
        if (pattern==null||pattern.equals("")){
            pattern= Y_M_D_H_M;
        }
        time.set(date);
        return time.format(pattern);
    }

    //是否为今月
    public static boolean isThisMonth(long date){
        if (date<=0){
            return false;
        }
        Time temp=new Time();
        temp.set(date);
        time.set(System.currentTimeMillis());
        return temp.year==time.year&&temp.month==time.month;
    }

    //是否为今天
    public static boolean isToday(long date){
        if (date<=0){
            return false;
        }
        Time temp=new Time();
        temp.set(date);
        time.set(System.currentTimeMillis());
        return temp.year==time.year&&temp.month==time.month&&temp.monthDay==time.monthDay;
    }

    //获取周
    public static int getWeekFromDay(int day){
        if (day<=0||day>31){
            return ERROR;
        }else if (day<=7){
            return 1;
        }else if (day<=14){
            return 2;
        }else if(day<=21){
            return 3;
        }else if (day<=28){
            return 4;
        }else {
            return 5;
        }
    }
    //获取周
    public static int getWeekFromDay(long date){
        if (date <=0){
            return ERROR;
        }
        time.set(date);
        return getWeekFromDay(time.monthDay);
    }

    //获取当前月
    public static int getMonth(){
        time.set(System.currentTimeMillis());
        return time.month;
    }

    //第几周 1-60周
    public static int getWeekNum(long date){
        Time time=new Time();
        time.set(date);
        return time.monthDay / 7 + time.month* 5+1;
    }
}
