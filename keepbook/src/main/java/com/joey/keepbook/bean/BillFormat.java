package com.joey.keepbook.bean;

import android.text.format.Time;

import com.joey.keepbook.utils.DateUtils;

/**
 * Created by Joey on 2016/3/16.
 */
public class BillFormat extends Bill {
    private Time time;
    private final int ERROR=-1;
    private int month;
    private int year;
    private int week;
    private int day;

    public Time getTime() {
        return time;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }

    public BillFormat(long date, float money, String remark, String classes, int classify, int page) {
        super(date, money, remark, classes, classify, page);
        time=new Time();

        time.set(date);
        this.year=time.year;
        this.month=time.month;
        this.day=time.monthDay;
        this.week= DateUtils.getWeekFromDay(day);
    }
    public String getDateToString(String pattern){
        return DateUtils.format(getDate());
    }

}
