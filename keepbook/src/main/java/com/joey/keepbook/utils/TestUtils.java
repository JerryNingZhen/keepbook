package com.joey.keepbook.utils;

import android.content.Context;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.PageDao;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by joey on 2016/3/24.
 */
public class TestUtils {

    private static final String TAG = "调试 TestUtils";

    public static void insertPage(Context context) {
        PageDao.getInstance(context).deleteAll();
        PageDao.getInstance(context).insert(new Page(0, "我的账单"));
        PageDao.getInstance(context).insert(new Page(1, "老婆的账单"));
        PageDao.getInstance(context).insert(new Page(2, "公司账单"));
        PageDao.getInstance(context).insert(new Page(3, "私房钱"));
    }

    public static void insertBill(Context context, int count) {
        LogUtils.e("调试TestUtils", "insertBill。。。。。。。。。。");
        BillDao billDao = BillDao.getInstance(context);
        billDao.deleteAll();

        Long ONE_YEAR = 31536000000L;
        long oneYearAgo = System.currentTimeMillis() - ONE_YEAR;

        int pageCount = PageDao.getInstance(context).getCount();
        if (pageCount < 1) {
            pageCount = 1;
        }
        Random random = new Random();

        String[] classesIn = {"工资", "奖金"};
        String[] classesOut = {"车费", "餐费"};
        for (int i = 0; i < count; i++) {
            long date = (long) (oneYearAgo + ONE_YEAR * Math.random());
            float money = twoPoint(random.nextInt(10000) * 0.01f);
            String remark = "无备注 测试。。。" + i;
            String str;
            int classify = (random.nextBoolean()) ? AppConfig.BOOK_IN_FRAGMENT : AppConfig.BOOK_OUT_FRAGMENT;
            if (classify == AppConfig.BOOK_IN_FRAGMENT) {
                str = classesIn[random.nextInt(1)];
            } else {
                str = classesOut[random.nextInt(1)];
            }
            int page = random.nextInt(pageCount - 1);
            Bill bill = new Bill(date, money, remark, str, classify, page);
            LogUtils.e(TAG, "insertBill.........\n" + bill.toString());
            billDao.insert(bill);
        }
    }

    private static float twoPoint(float f) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }
}
