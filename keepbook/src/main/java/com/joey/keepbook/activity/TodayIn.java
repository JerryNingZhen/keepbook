package com.joey.keepbook.activity;

import android.os.Bundle;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.activity.base.BaseDetailActivity;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/5.
 */
public class TodayIn extends BaseDetailActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTvTitle().setText("今日收入");
    }

    @Override
    protected List<Bill> getBillList(List<Bill> billList) {
        List<Bill> tempList = new ArrayList<Bill>();
        for (Bill b : billList) {
            if (b.getClassify() == AppConfig.BOOK_IN_FRAGMENT && DateUtils.isToday(b.getDate())) {
                tempList.add(b);
            }
        }
        return tempList;
    }
}
