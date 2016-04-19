package com.joey.keepbook;

import android.content.Context;
import android.content.res.Resources;
import android.test.AndroidTestCase;
import android.text.format.Time;

import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.utils.DateManger;
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
    private Long ONE_YEAR = 31536000000L;
    private long today = System.currentTimeMillis();

    public void test() {
        A a=new A();
        a.getA()[1]=10;
        LogUtils.e(String.valueOf(a.getA()[1]));

    }


    class A{
        int []a={1,2,3};
        public int[] getA() {
            return a;
        }
        public void setA(int[] a) {
            this.a = a;
        }
    }
}
