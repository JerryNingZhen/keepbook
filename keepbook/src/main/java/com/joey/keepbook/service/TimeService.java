package com.joey.keepbook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.joey.keepbook.AppManager;
import com.joey.keepbook.utils.DateManger;

/**
 * 后台 每小时更新时间
 */
public class TimeService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (AppManager.isRunning) {
                    try {
                        sleep(3600000);//休息一小时
                        DateManger.getInstance().setToday(System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                onDestroy();
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
