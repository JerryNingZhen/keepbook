package com.joey.keepbook.activity;

import com.joey.keepbook.activity.base.BaseDetailActivity;
import com.joey.keepbook.bean.Bill;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/5.
 */
public class TodayIn extends BaseDetailActivity {
    @Override
    protected void initData() {
        super.initData();
        title="今日收入";
        classify= activityManager.BOOKINFRAGMENT;//收入状态

        /**
         * 筛选今日收入数据
         */
        List<Bill>tempList=new ArrayList<Bill>();
        for (Bill b:billList){
            if (b.getClassify()==classify){
                if (dateFormat.isSameDay(today,b.getDate())){
                    tempList.add(b);
                }
            }
        }
        billList=tempList;
    }
}
