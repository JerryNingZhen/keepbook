package com.joey.keepbook.utils;
import android.content.Context;

import com.joey.keepbook.AppConfig;

/**
 * Created by Joey on 2016/3/13.
 */
public class PrefUtils {
    private static String NAME = AppConfig.APP_CONFIG;
    public static int getInt(Context context, String key, int defValue) {
        return context.getSharedPreferences(NAME, context.MODE_PRIVATE).getInt(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(NAME, context.MODE_PRIVATE).getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        context.getSharedPreferences(NAME, context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public static void putString(Context context, String key, String value) {
        context.getSharedPreferences(NAME, context.MODE_PRIVATE).edit().putString(key, value).commit();
    }
}
