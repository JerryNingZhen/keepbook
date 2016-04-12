package com.joey.keepbook.activity;

import android.os.Bundle;

import com.joey.keepbook.activity.base.BaseDetailActivity;
import com.joey.keepbook.bean.Bill;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/5.
 */
public class AllBillActivity extends BaseDetailActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTvTitle().setText("账单明细");
    }

    @Override
    protected List<Bill> getBillList(List<Bill> billList) {
        return billList;
    }
}
