package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.base.BaseOpenHelper;

/**
 * Created by Joey on 2016/2/18.
 */
public class BillOpenHelper extends BaseOpenHelper {
    private static final String TAG = "调试BillOpenHelper";
    public BillOpenHelper(Context context) {
        super(context, AppConstant.getInstance().billTableName, AppConstant.getInstance().billColumns, AppConstant.getInstance().billVersion);
    }
}
