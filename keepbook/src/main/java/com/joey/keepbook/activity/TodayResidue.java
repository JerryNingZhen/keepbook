package com.joey.keepbook.activity;

import com.joey.keepbook.activity.base.BaseDetailActivity;
import com.joey.keepbook.bean.Bill;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/5.
 */
public class TodayResidue extends BaseDetailActivity {
    @Override
    protected void initData() {
        super.initData();
        title = "今日结存";
        int todayOut = activityManager.BOOKOUTFRAGMENT;//收入状态
        int todayIn = activityManager.BOOKINFRAGMENT;//收入状态

        /**
         * 筛选今日收入数据
         */
        List<Bill> tempList = new ArrayList<Bill>();
        for (Bill b : billList) {
            if (b.getClassify() == todayIn) {
                if (dateFormat.isSameDay(today, b.getDate())) {
                    tempList.add(b);
                }
            }else if (b.getClassify() == todayOut){
                if (dateFormat.isSameDay(today, b.getDate())) {
                    tempList.add(b);
                }
            }
        }
        billList = tempList;
    }
}
