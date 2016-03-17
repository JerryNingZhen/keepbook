package com.joey.keepbook.bean;

/**
 * Created by Joey on 2016/3/16.
 */
public class ChartInitData {
    private float[][] monthTotals;
    private float[][][] weekTotals;

    public ChartInitData(float[][] monthTotals, float[][][] weekTotals) {
        this.monthTotals = monthTotals;
        this.weekTotals = weekTotals;
    }

    public float[][] getMonthTotals() {
        return monthTotals;
    }

    public float[][][] getWeekTotals() {
        return weekTotals;
    }
}
