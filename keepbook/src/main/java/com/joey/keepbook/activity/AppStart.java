package com.joey.keepbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.R;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.cache.CacheManager;
import com.joey.keepbook.db.DbManager;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.listener.IReadyListener;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.utils.ToastUtils;

import java.util.Random;

/**
 * Created by joey on 2016/3/23.
 */
public class AppStart extends Activity {
    private static final String TAG = "调试";
    private static final int REDIRECT_TO_HOME = 1;
    private static final int READY = 2;
    private static final int TIME_OUT = 3;
    private static final int FIRST_LAUNCHED = 4;
    private static boolean isReady = false;

    private FrameLayout fl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REDIRECT_TO_HOME:

                    break;
                case READY:
                    if (isReady) {
                        LogUtils.e("AppStart 数据加载完毕，正在跳转至 主界面");
                        redirectTo();
                    }
                    break;
                case TIME_OUT:
                    LogUtils.e("AppStart 加载超时 直接跳转 主界面");
                    redirectTo();
                    break;
                case FIRST_LAUNCHED:
                    fl.setVisibility(View.INVISIBLE);
                    LogUtils.e("AppStart 第一次加载完毕---");
                    ToastUtils.makeTextShort(AppStart.this, "测试数据，写入完成");
                    init();
                    break;

                default:
                    break;
            }
        }
    };
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(TAG, "onCreate ");
        view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        fl = (FrameLayout) findViewById(R.id.fl_app_start);
        fl.setVisibility(View.INVISIBLE);
        //保存启动次数
        int count = PrefUtils.getInt(this, AppConfig.KEY_LAUNCH_COUNT, 0) + 1;
        PrefUtils.putInt(this, AppConfig.KEY_LAUNCH_COUNT, count);
        LogUtils.e("这是 应用 第" + count + "次启动");
        //第一次加载--
        if (count <= 1) {
            fl.setVisibility(View.VISIBLE);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        CacheManager.getInstance().writeTestData();//写入测试数据，非常耗时
                        handler.sendEmptyMessage(FIRST_LAUNCHED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            init();
        }

    }

    //跳转 至 home
    private void redirectTo() {
        if (!isDestroyed()) {
            //未销毁状态下跳转
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void init() {
        //db 数据库  绑定 缓存 cache
        DbManager.getInstance().syncToCache(CacheManager.getInstance());

        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(1000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        //启动 线程  加载缓存 最快2秒，最慢6秒
        new Thread() {
            @Override
            public void run() {
                super.run();
                CacheManager.getInstance().init(new IReadyListener() {
                    @Override
                    public void isReady() {
                        isReady = true;
                    }
                });
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int i = 0; i < 3; i++) {
                        sleep(2000);
                        handler.sendEmptyMessage(READY);
                    }
                    handler.sendEmptyMessage(TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
