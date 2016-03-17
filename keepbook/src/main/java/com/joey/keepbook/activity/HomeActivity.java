package com.joey.keepbook.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.joey.keepbook.R;
import com.joey.keepbook.base.BaseActivity;
import com.joey.keepbook.data.Data;
import com.joey.keepbook.fragment.HomeFragment;
import com.joey.keepbook.fragment.HomeLeftFragment;
import com.joey.keepbook.fragment.HomeRightFragment;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.BottomMenuView;
import com.joey.keepbook.view.FractionTranslateLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/3.
 */
public class HomeActivity extends Activity {
    private static final String TAG = "调试HomeActivity";
    //管理器
    private FragmentManager fm;
    private GestureDetector mDetector;
    //状态
    private int mIntPage;
    private int mIntPageCount = -1;
    //fragment
    private HomeFragment mFragment;
    private HomeRightFragment mRightFragment;
    private List<HomeFragment> mHomeFragmentList;

    //key
    private String mStrKeyPage;
    private HomeLeftFragment mLeftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initData() {
        PageDao dao = PageDao.getInstance(this);
        MyActivityManager activityManager =MyActivityManager.getInstance();
        //key
        mStrKeyPage = activityManager.KEY_PAGE;

        //状态
        mIntPage = 1;
        mIntPageCount = (dao.getCount()<1)?1:dao.getCount();
        PrefUtils.putInt(this, mStrKeyPage, mIntPage);
        mHomeFragmentList = new ArrayList<HomeFragment>();
    }
    /**
     * 初始化UI
     */
    private void initUI() {
        setContentView(R.layout.activity_home);
        BottomMenuView bottomMenuViewDetail= (BottomMenuView) findViewById(R.id.bmv_bottom_menu_detail);
        BottomMenuView bottomMenuViewAccount= (BottomMenuView) findViewById(R.id.bmv_bottom_menu_account);
        BottomMenuView bottomMenuViewBbs= (BottomMenuView) findViewById(R.id.bmv_bottom_menu_bbs);
        BottomMenuView bottomMenuViewFund= (BottomMenuView) findViewById(R.id.bmv_bottom_menu_fund);
        BottomMenuView bottomMenuViewStat= (BottomMenuView) findViewById(R.id.bmv_bottom_menu_stat);
        bottomMenuViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AllBillActivity.class));
            }
        });



        fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFragment = new HomeFragment();
        mHomeFragmentList.add(mFragment);
        transaction.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
                R.animator.slide_left_in, R.animator.slide_right_out);
        transaction.replace(R.id.translate_home, mFragment);
        transaction.commit();

        //滑动屏幕
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            //监听手势滑动
            /**
             * @param e1        滑动的起点
             * @param e2        滑动的终点
             * @param velocityX 水平速度
             * @param velocityY 垂直速度
             * @return
             */
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                /**
                 * rawX表示整个屏幕
                 * getX表示控件范围内
                 */
                //判断y坐标幅度是否过大
                if (Math.abs((e2.getRawY() - e1.getRawY())) < 200) {
                    if ((e2.getRawX() - e1.getRawX()) > 60) {
                        //向右滑动
                        showLeftPage();
                        return true;
                    } else if ((e2.getRawX() - e1.getRawX()) < -60) {
                        //向左滑动
                        showRightPage();
                        return true;
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


    }

    private void showLeftPage() {
        if (mIntPage > 1) {
            mIntPage--;
            savePageToPref();
            FragmentTransaction transaction = fm.beginTransaction();
            if (mHomeFragmentList.size() >= mIntPage) {
                mFragment = mHomeFragmentList.get(mIntPage - 1);
            } else {
                mFragment = new HomeFragment();
                mHomeFragmentList.add(mFragment);
            }
            transaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_right_out,
                    R.animator.slide_right_in, R.animator.slide_left_out);
            transaction.replace(R.id.translate_home, mFragment);
            transaction.commit();
        }else if (mIntPage==1){
            mIntPage--;
            savePageToPref();
            FragmentTransaction transaction = fm.beginTransaction();
            if (mLeftFragment == null) {
                mLeftFragment = new HomeLeftFragment();
            }
            transaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_right_out,
                    R.animator.slide_right_in, R.animator.slide_left_out);
            transaction.replace(R.id.translate_home, mLeftFragment);
            transaction.commit();
        }
        LogUtils.e(TAG, "showLeftPage()");
    }

    private void showRightPage() {
        if (mIntPage < mIntPageCount + 1) {
            mIntPage++;
            savePageToPref();
            if (mIntPage <= mIntPageCount) {
                FragmentTransaction transaction = fm.beginTransaction();
                if (mHomeFragmentList.size() >= mIntPage) {
                    mFragment = mHomeFragmentList.get(mIntPage - 1);
                } else {
                    mFragment = new HomeFragment();
                    mHomeFragmentList.add(mFragment);
                }
                transaction.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
                        R.animator.slide_left_in, R.animator.slide_right_out);
                transaction.replace(R.id.translate_home, mFragment);
                transaction.commit();
            } else if (mIntPage == mIntPageCount + 1) {
                FragmentTransaction transaction = fm.beginTransaction();
                if (mRightFragment == null) {
                    mRightFragment = new HomeRightFragment();
                }
                transaction.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
                        R.animator.slide_left_in, R.animator.slide_right_out);
                transaction.replace(R.id.translate_home, mRightFragment);
                transaction.commit();
            }
        }
        LogUtils.e(TAG, "showRightPage()");
    }

    private void savePageToPref() {
        PrefUtils.putInt(this, mStrKeyPage, mIntPage);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
