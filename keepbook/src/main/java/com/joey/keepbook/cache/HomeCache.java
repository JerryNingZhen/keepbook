package com.joey.keepbook.cache;

import java.math.BigDecimal;
/**
 * home fragment cache
 */
public class HomeCache {
    //ui value
    private int page;
    private String title;
    private float thisMonthIn;
    private float thisMonthOut;
    private float todayIn;
    private float todayOut;
    private int month;
    private int day;
    private String help;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public HomeCache() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getThisMonthIn() {
        return thisMonthIn;
    }


    public void setThisMonthIn(float thisMonthIn) {
        thisMonthIn = twoPoint(thisMonthIn);
        this.thisMonthIn = thisMonthIn;
    }

    public float getThisMonthOut() {
        return thisMonthOut;
    }

    public void setThisMonthOut(float thisMonthOut) {
        thisMonthOut = twoPoint(thisMonthOut);
        this.thisMonthOut = thisMonthOut;
    }

    public float getThisMonthResidue() {
        return twoPoint(thisMonthIn - thisMonthOut);
    }


    public float getTodayIn() {
        return todayIn;
    }

    public void setTodayIn(float todayIn) {

        todayIn = twoPoint(todayIn);
        this.todayIn = todayIn;
    }

    public float getTodayOut() {
        return todayOut;
    }

    public void setTodayOut(float todayOut) {
        todayOut = twoPoint(todayOut);
        this.todayOut = todayOut;
    }

    public float getTodayResidue() {
        return twoPoint(todayIn - todayOut);
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * return string
     */
    public String getStrThisMonthIn() {
        if (thisMonthIn == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(thisMonthIn);
        }
    }

    /**
     * return string
     */
    public String getStrThisMonthOut() {
        if (thisMonthOut == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(thisMonthOut);
        }
    }

    /**
     * return string
     */
    public String getStrThisMonthResidue() {
        if (getThisMonthResidue() == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(getThisMonthResidue());
        }
    }

    /**
     * return string
     */
    public String getStrTodayIn() {
        if (todayIn == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(todayIn);
        }
    }

    /**
     * return string
     */
    public String getStrTodayOut() {
        if (todayOut == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(todayOut);
        }
    }

    /**
     * return string
     */
    public String getStrTodayResidue() {
        if (getTodayResidue() == 0.0f) {
            return "0.00";
        } else {
            return String.valueOf(getTodayResidue());
        }
    }

    /**
     * 保留两位小数
     */
    private float twoPoint(float f) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }
}