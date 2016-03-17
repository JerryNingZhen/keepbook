package com.joey.keepbook.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.format.Time;

import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.data.Data;
import com.joey.keepbook.db.dao.BillDao;

import java.util.List;

/**
 * Created by Joey on 2016/3/15.
 */
public  class ActivityDataUtils extends Activity {
    /**
     * classify     4位  0表示收入1表示支出
     *
     */
    public static final int d=0;

    public static final int TODAY_IN_CHANGE=1;
    public static final int TODAY_OUT_CHANGE=2;
    public static final int CLASSES_CHANGE=3;
    public static final int DATA_FINISH_NOTIFY=11;
    public static int dayCount=7;//七天数据
    public static float[]Ins=new float[dayCount];
    public static float[]Outs=new float[dayCount];
    public static float[]residues=new float[dayCount];
    public static float todayIn;
    public static float todayOut;
    public static float todayResidue;
    public static float thisMonthIn;
    public static float thisMonthOut;
    public static float thisMonthResidue;
    //


    public static void notifyDataSetChanged(final Context context,final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                BillDao instance = BillDao.getInstance(context);


                handler.sendEmptyMessage(DATA_FINISH_NOTIFY);
            }
        };
    }
//    public DataUtils(List<Bill> billList) {
//        today = System.currentTimeMillis();
//        int classify_in = viewConstant.bookInFragment;
//        int classify_out = viewConstant.bookOutFragment;
//        Time t1 = new Time();
//        Time t2 = new Time();
//        t1.set(today);
//        for (Bill b : billList) {
//            t2.set(b.getDate());
//            //同一个月
//            if (t2.year == t1.year && t1.month == t2.month) {
//                if (b.getClassify() == classify_in) {
//                    if (t2.monthDay == t1.monthDay) {
//                        todayIn = todayIn + b.getMoney();
//                    } else {
//                        thisMonthIn = thisMonthIn + b.getMoney();
//                    }
//                } else if (b.getClassify() == classify_out) {
//                    if (t2.monthDay == t1.monthDay) {
//                        todayOut = todayOut + b.getMoney();
//                    } else {
//                        thisMonthOut = thisMonthOut + b.getMoney();
//                    }
//                }
//            }
//        }
//        thisMonthIn = thisMonthIn + todayIn;
//        thisMonthOut = thisMonthOut + todayOut;
//        thisMonthResidue = thisMonthIn - thisMonthOut;
//        todayResidue = todayIn - todayOut;
//    }
}
