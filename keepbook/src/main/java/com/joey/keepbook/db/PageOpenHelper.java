package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.data.Data;

/**
 * Created by Joey on 2016/2/18.
 */
public class PageOpenHelper extends BaseOpenHelper {
    private static final String TAG = "调试PageOpenHelper";
    public PageOpenHelper(Context context) {
        super(context, Data.getInstance().getDbConstant().pageTableName,
                Data.getInstance().getDbConstant().pageColumns, Data.getInstance().getDbConstant().pageVersion);
    }
}
