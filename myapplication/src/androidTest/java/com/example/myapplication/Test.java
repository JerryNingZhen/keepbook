package com.example.myapplication;


import android.support.design.widget.CoordinatorLayout;
import android.test.AndroidTestCase;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by joey on 2016/3/21.
 */
public class Test extends AndroidTestCase {
    private static final String TAG = "调试";

    public void test(){
        String text="asdfa2::asdfa1:af3";
        String[] split = TextUtils.split(text, ":");

    }
}
