package com.joey.keepbook.cache;

import com.joey.keepbook.utils.LogUtils;

import java.math.BigDecimal;

/**
 * chart cache
 */
public class ChartCache {
    private float[] monthsIn;
    private float[] monthsOut;
    private float[] weeksIn;
    private float[] weeksOut;
    private final int weekCount = 60;
    private final int monthCount = 12;

    /**
     * 构造
     */
    public ChartCache() {
        weeksIn = new float[weekCount];
        weeksOut = new float[weekCount];
        monthsIn=new float[monthCount];
        monthsOut=new float[monthCount];
    }

    public float[] getWeeksIn() {
        if (weeksIn == null) {
            weeksIn = new float[weekCount];
        }
        return twoPoint(weeksIn);
    }

    public float[] getWeeksOut() {
        if (weeksOut == null) {
            weeksOut = new float[weekCount];
        }
        return twoPoint(weeksOut);
    }

    public float[] getMonthsIn() {
        for (int i=0;i<12;i++){//初始化12个月的数据
            float total = 0;
            for (int j=0;j<5;j++){//每个月 5周
                total=total+weeksIn[i*5+j];
            }
            monthsIn[i]=total;
        }
        monthsIn = twoPoint(monthsIn);
        return monthsIn;
    }

    public float[] getMonthsOut() {
        for (int i=0;i<12;i++){//初始化12个月的数据
            float total = 0;
            for (int j=0;j<5;j++){//每个月 5周
                total=total+weeksOut[i*5+j];
            }
            monthsOut[i]=total;
        }
        monthsOut = twoPoint(monthsOut);
        return monthsOut;
    }

    public void setWeeksIn(float[] weeksIn) {
        if (weeksIn.length != weekCount) {
            this.weeksIn = new float[weekCount];
        } else {
            this.weeksIn = weeksIn;
        }
    }

    public void setWeeksOut(float[] weeksOut) {
        if (weeksOut.length != weekCount) {
            this.weeksOut = new float[weekCount];
        } else {
            this.weeksOut = weeksOut;
        }
    }

    /**
     * 保留两位小数
     */
    private float[] twoPoint(float[] fs) {
        float[] f = new float[fs.length];
        for (int i = 0; i < fs.length; i++) {
            f[i] = new BigDecimal(fs[i]).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        return f;
    }
}