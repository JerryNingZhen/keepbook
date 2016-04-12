package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.base.BaseOpenHelper;

/**
 * Created by Joey on 2016/2/18.
 */
public class ClassesOpenHelper extends BaseOpenHelper {

    public ClassesOpenHelper(Context context) {
        super(context, AppConstant.getInstance().classesTableName, AppConstant.getInstance().classesColumns,AppConstant.getInstance().classesVersion);
    }
}
