package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.data.Data;

/**
 * Created by Joey on 2016/2/18.
 */
public class ClassesOpenHelper extends BaseOpenHelper {

    public ClassesOpenHelper(Context context) {
        super(context, Data.getInstance().getDbConstant().classesTableName, Data.getInstance().getDbConstant().classesColumns, Data.getInstance().getDbConstant().classesVersion);
    }
}
