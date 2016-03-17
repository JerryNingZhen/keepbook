package com.joey.keepbook.bean;

/**
 * Created by Joey on 2016/3/16.
 */
public class HomeInitData {
    private float todayIn;
    private float todayOut;
    private float todayResidue;
    private float thisMonthIn;
    private float thisMonthOut;
    private float thisMonthResidue;

    public HomeInitData(float todayIn, float todayOut, float todayResidue, float thisMonthIn, float thisMonthOut, float thisMonthResidue) {
        this.todayIn = todayIn;
        this.todayOut = todayOut;
        this.todayResidue = todayResidue;
        this.thisMonthIn = thisMonthIn;
        this.thisMonthOut = thisMonthOut;
        this.thisMonthResidue = thisMonthResidue;
    }

    public float getTodayIn() {
        return todayIn;
    }

    public float getTodayOut() {
        return todayOut;
    }

    public float getTodayResidue() {
        return todayResidue;
    }

    public float getThisMonthIn() {
        return thisMonthIn;
    }

    public float getThisMonthOut() {
        return thisMonthOut;
    }

    public float getThisMonthResidue() {
        return thisMonthResidue;
    }
}
