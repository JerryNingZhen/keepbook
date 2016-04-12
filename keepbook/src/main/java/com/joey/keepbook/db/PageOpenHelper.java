package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.base.BaseOpenHelper;

/**
 * Created by Joey on 2016/2/18.
 */
public class PageOpenHelper extends BaseOpenHelper {
    private static final String TAG = "调试PageOpenHelper";
    public PageOpenHelper(Context context) {
        super(context, AppConstant.getInstance().pageTableName,
                AppConstant.getInstance().pageColumns,AppConstant.getInstance().pageVersion);
    }
}
