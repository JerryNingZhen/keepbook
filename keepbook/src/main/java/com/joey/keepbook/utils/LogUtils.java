package com.joey.keepbook.utils;

import android.util.Log;

/**
 * Created by Joey on 2016/3/5.
 */
public class LogUtils {
    private static boolean isDebug = true;
    /**
     * log e
     */
    public static void e(String TAG, String text) {
        if (isDebug)
            Log.e(TAG, text);
    }
    /**
     * log e
     */
    public static void e(String text) {
        e("调试",text);
    }
}
