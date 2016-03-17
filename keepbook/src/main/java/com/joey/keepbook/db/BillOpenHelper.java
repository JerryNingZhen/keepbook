package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.data.Data;

/**
 * Created by Joey on 2016/2/18.
 */
public class BillOpenHelper extends BaseOpenHelper {
    private static final String TAG = "调试BillOpenHelper";
    public BillOpenHelper(Context context) {
        super(context, Data.getInstance().getDbConstant().billTableName, Data.getInstance().getDbConstant().billColumns, Data.getInstance().getDbConstant().billVersion);
    }
}
