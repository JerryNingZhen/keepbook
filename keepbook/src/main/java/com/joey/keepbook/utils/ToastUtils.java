package com.joey.keepbook.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Joey on 2016/3/5.
 */
public class ToastUtils {
    /**
     * 提示框-短时间
     * @param context
     * @param text
     */
    public static void makeTextShort(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示框-长时间
     * @param context
     * @param text
     */
    public static void makeTextLong(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
