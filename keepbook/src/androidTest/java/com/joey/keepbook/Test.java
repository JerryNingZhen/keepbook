package com.joey.keepbook;

import android.content.Context;
import android.content.res.Resources;
import android.test.AndroidTestCase;
import android.text.format.Time;

import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.ChartInitData;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.engine.BillData;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.utils.LogUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Joey on 2016/3/5.
 */
public class Test extends AndroidTestCase {
    private static final String TAG = "调试测试类";
    private Context context;
    private Resources resources;
    private Long ONE_YEAR=31536000000L;
    private long today = System.currentTimeMillis();

    public void test() {
//        insert();
//        Time time=new Time();
//        time.set(System.currentTimeMillis());
//        LogUtils.e(TAG,"月="+time.month+"  日"+time.monthDay);
        ChartInitData chartInitData = BillData.getInstance(getContext()).getChartInitData();
        float[][] monthTotals = chartInitData.getMonthTotals();
        for (int i=0;i<monthTotals.length;i++){
            for (int j=0;j<monthTotals[0].length;j++){
                LogUtils.e(TAG, "x= "+i+"  y= "+j+"   "+monthTotals[i][j]);
            }
        }
        LogUtils.e(TAG, monthTotals[1].toString());
        LogUtils.e(TAG, chartInitData.getWeekTotals().toString());
    }

    private void insert() {
        context = getContext();
        long oneYearAgo = System.currentTimeMillis() - ONE_YEAR;
        MyActivityManager activityManager = MyActivityManager.getInstance();
        int bookIn = activityManager.BOOKINFRAGMENT;
        int bookOut = activityManager.BOOKOUTFRAGMENT;
        LogUtils.e(TAG, String.valueOf(System.currentTimeMillis()));
        BillDao billDao = BillDao.getInstance(context);
        ClassesDao classesDao = ClassesDao.getInstance(context);
        PageDao pageDao = PageDao.getInstance(context);
        pageDao.deleteAll();
        billDao.deleteAll();
        classesDao.deleteAll();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Page page = new Page(i + 1, "我的账单测试" + i);
            pageDao.insert(page);
        }

        for (int i = 0; i < 100; i++) {
            int classify = (random.nextBoolean()) ? bookIn : bookOut;
            String str = "测试" + i;
            int page = random.nextInt(9) + 1;
            Classes classes = new Classes(str, classify, page);
            classesDao.insert(classes);
        }

        for (int i = 0; i < 2000; i++) {
            long date = (long) (oneYearAgo +ONE_YEAR*Math.random());
            float money = random.nextInt(1000) * 0.1f;
            String remark = "无备注 测试。。。" + i;
            String str = "测试" + random.nextInt(99);
            int classify = (random.nextBoolean()) ? bookIn : bookOut;
            int page = random.nextInt(9) + 1;
            billDao.insert(new Bill(date, money, remark, str, classify, page));
        }
    }

    private void traversalBill(List<Bill> billList) {
        int n = 1;
        LogUtils.e(TAG, "开始遍历Classes");
        for (Bill b : billList) {
            LogUtils.e(TAG, "第" + n);
            LogUtils.e(TAG, b.toString());
            LogUtils.e(TAG, b.getClasses());
            n++;
        }
        LogUtils.e(TAG, "结束遍历Classes");
    }

    private void traversalClasses(List<Classes> classesList) {
        int n = 1;
        LogUtils.e(TAG, "开始遍历Classes");
        for (Classes c : classesList) {
            LogUtils.e(TAG, "第" + n);
            LogUtils.e(TAG, c.toString());
            LogUtils.e(TAG, c.getClasses());
        }
        LogUtils.e(TAG, "结束遍历Classes");
    }


}
