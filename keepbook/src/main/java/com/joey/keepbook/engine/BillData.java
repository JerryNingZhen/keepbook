package com.joey.keepbook.engine;

import android.content.Context;
import android.text.format.Time;

import com.joey.keepbook.base.BaseActivity;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.ChartInitData;
import com.joey.keepbook.bean.HomeInitData;
import com.joey.keepbook.data.Data;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.utils.DateUtils;
import com.joey.keepbook.utils.LogUtils;

import java.util.List;

/**
 * Created by Joey on 2016/3/16.
 */
public class BillData {
    public static final int BOOK_IN = 0;
    public static final int BOOK_OUT = 1;
    private static final String TAG = "调试BillData";
    public static Context context;
    private static BillData billData;

    /**
     * 单例
     */
    private BillData(Context context) {
        this.context = context;

    }

    public static BillData getInstance(Context context) {
        if (billData == null) {
            billData = new BillData(context);
        }
        return billData;
    }

    /**
     * 释放内存
     */
    public void close() {
        billData = null;
    }

    /**
     * 更新chart所需数据
     * monthTotals
     * weekTotals
     */
    public ChartInitData getChartInitData(List<Bill> billList) {
        if (billList == null) {
            return null;
        }
        MyActivityManager activityManager = MyActivityManager.getInstance();
        //支出和收入状态
        int book_out = activityManager.BOOKOUTFRAGMENT;
        int book_in = activityManager.BOOKINFRAGMENT;
        //chart数据
        //每周合计 月[0-11][] 收入[0-11][0-4] 和支出[0-11][5-9]
        float[][] monthTotals = new float[2][12];
        float[][][] weekTotals = new float[2][12][5];
        Time time = new Time();
        for (Bill bill : billList) {
            time.set(bill.getDate());
            int week = DateUtils.getWeekFromDay(time.monthDay);
            int month = time.month + 1;
            LogUtils.e(TAG, "getChartInitData() week=" + week + "month" + month);
            if (bill.getClassify() == book_in) {
                weekTotals[BOOK_IN][month - 1][week - 1] = weekTotals[BOOK_IN][month - 1][week - 1] + bill.getMoney();
                monthTotals[BOOK_IN][month - 1] = monthTotals[BOOK_IN][month - 1] + bill.getMoney();
            } else if (bill.getClassify() == book_out) {
                weekTotals[BOOK_OUT][month - 1][week - 1] = weekTotals[BOOK_OUT][month - 1][week - 1] + bill.getMoney();
                monthTotals[BOOK_OUT][month - 1] = monthTotals[BOOK_OUT][month - 1] + bill.getMoney();
            }
        }
        return new ChartInitData(monthTotals, weekTotals);
    }

    public ChartInitData getChartInitData() {
        List<Bill> bills = BillDao.getInstance(context).queryAll();
        return getChartInitData(bills);
    }

    /**
     * 获取ui初始化数据
     * HomeActivity数据
     */
    public HomeInitData getHomeInitData(List<Bill> billList) {
        float todayIn = 0;
        float todayOut = 0;
        float todayResidue = 0;
        float thisMonthIn = 0;
        float thisMonthOut = 0;
        float thisMonthResidue = 0;
        MyActivityManager activityManager = MyActivityManager.getInstance();
        int classify_in = activityManager.BOOKINFRAGMENT;
        int classify_out = activityManager.BOOKOUTFRAGMENT;
        Time t1 = new Time();
        Time t2 = new Time();
        t1.set(System.currentTimeMillis());
        for (Bill b : billList) {
            t2.set(b.getDate());
            //同一个月
            if (t2.year == t1.year && t1.month == t2.month) {
                if (b.getClassify() == classify_in) {
                    if (t2.monthDay == t1.monthDay) {
                        todayIn = todayIn + b.getMoney();
                    } else {
                        thisMonthIn = thisMonthIn + b.getMoney();
                    }
                } else if (b.getClassify() == classify_out) {
                    if (t2.monthDay == t1.monthDay) {
                        todayOut = todayOut + b.getMoney();
                    } else {
                        thisMonthOut = thisMonthOut + b.getMoney();
                    }
                }
            }
        }
        thisMonthIn = thisMonthIn + todayIn;
        thisMonthOut = thisMonthOut + todayOut;
        thisMonthResidue = thisMonthIn - thisMonthOut;
        todayResidue = todayIn - todayOut;
        return new HomeInitData(todayIn, todayOut, todayResidue, thisMonthIn, thisMonthOut, thisMonthResidue);
    }

}
