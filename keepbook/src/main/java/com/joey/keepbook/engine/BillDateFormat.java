package com.joey.keepbook.engine;


import android.text.format.Time;

import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.data.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Joey on 2016/2/19.
 */
public class BillDateFormat {
    private static BillDateFormat mFormat;
    private static Time time;
    private static Pattern pattern;
    private static long today;
    private static MyActivityManager activityManager;
    private BillDateFormat() {
    }
    public static BillDateFormat getInstance() {
        if (mFormat == null) {
            mFormat = new BillDateFormat();
        }
        if (time == null) {
            time = new Time();
        }
        if (today < 1) {
            today = System.currentTimeMillis();
        }
        if (activityManager == null) {
            activityManager = MyActivityManager.getInstance();
        }
        return mFormat;
    }

    public Pattern getPattern() {
        if (pattern == null) {
            pattern = new Pattern();
        }
        return pattern;
    }

    public boolean isSameDay(long today, long date) {
        setTime(today);
        Time t = new Time();
        t.set(date);
        if (time.year == t.year && time.month == t.month && time.monthDay == t.monthDay) {
            t = null;
            return true;
        }
        return false;
    }

    public boolean isSameMonth(long today, long date) {
        setTime(today);
        Time t = new Time();
        t.set(date);
        if (time.year == t.year && time.month == t.month) {
            t = null;
            return true;
        }
        return false;
    }

    public String format(long date, String pattern) {
        setTime(date);
        return time.format(pattern);
    }

    public class Pattern {
        public Pattern() {
        }

        //"%Y-%m-%d %H:%M:%S"
        public final String SHOWTIME = "%H:%M";
        public final String SHOWDATE = "%Y-%m-%d";
        public final String SHOWDATEANDTIME = "%Y-%m-%d %H:%M";
    }

    public Time getTime(long date) {
        setTime(date);
        return time;
    }

    public void setTime(long date) {
        time.set(date);
    }

    public List<Bill> getToday(List<Bill> billList, int classify) {
        List<Bill> tempList = new ArrayList<Bill>();
        for (Bill b : billList) {
            if (b.getClassify() == classify) {
                if (isSameDay(today, b.getDate())) {
                    tempList.add(b);
                }
            }
        }
        return tempList;
    }



}
