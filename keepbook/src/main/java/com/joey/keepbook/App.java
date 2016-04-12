package com.joey.keepbook;

import android.app.Application;
import android.content.Context;

import com.joey.keepbook.utils.PrefUtils;

/**
 * Created by joey on 2016/3/31.
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    /**
     * 全局使用 Context
     */
    public static Context getContextObject(){
        return context;
    }
}
