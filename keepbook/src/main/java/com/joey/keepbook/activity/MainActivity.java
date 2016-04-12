package com.joey.keepbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.joey.keepbook.AppConfig;
import com.joey.keepbook.R;
import com.joey.keepbook.cache.CacheManager;
import com.joey.keepbook.cache.HomeCache;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.BottomMenuView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by joey on 2016/3/20.
 */
public class MainActivity extends FragmentActivity {
    private static final String TAG = "调试HomeActivity";
    private List<Fragment> fragments;
    private int mintPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomMenuView bmvDetail = (BottomMenuView) findViewById(R.id.bmv_bottom_menu_detail);
        BottomMenuView bmvAccount = (BottomMenuView) findViewById(R.id.bmv_bottom_menu_account);
        BottomMenuView bmvStat = (BottomMenuView) findViewById(R.id.bmv_bottom_menu_stat);
        BottomMenuView bmvFund = (BottomMenuView) findViewById(R.id.bmv_bottom_menu_fund);
        BottomMenuView bmvBBS = (BottomMenuView) findViewById(R.id.bmv_bottom_menu_bbs);

        //点击 明细 报表 账户 理财 社区
        bmvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllBillActivity.class));
            }
        });

        //点击 账户
        bmvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnCompleting.class));
            }
        });

        //点击 报表
        bmvStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnCompleting.class));
            }
        });

        //点击 理财
        bmvFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnCompleting.class));
            }
        });

        //点击 社区
        bmvBBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnCompleting.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //初始化 fragments
        fragments = new ArrayList<Fragment>();
        Map<Integer, HomeCache> homeCacheMap = (Map<Integer, HomeCache>) CacheManager.getInstance().getCache(CacheManager.HOME_CACHE_MAP);

        fragments.add(new HomeLeftFragment());//第一页
        int count=0;//表示 home fragment 数目
        for (int i=0;count<homeCacheMap.size()&&i<100;i++){
          if (homeCacheMap.containsKey(i)){
              fragments.add(new HomeFragment().setCache(homeCacheMap.get(i)));
              count++;
          }
        }

        fragments.add(new HomeRightFragment());//最后一页

        //当前页 只存储 home fragment
        mintPage = PrefUtils.getInt(MainActivity.this, AppConfig.KEY_CURRENT_PAGE, 0);

        //view page 适配
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentAdapter adapter = new MyFragmentAdapter(fm);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(mintPage + 1);//设置当前页面

        //返回按钮监听
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
            }
        });

        //页面切换监听 保存当前页面位置
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e("在 main 中滑动后 position=" + position);
                if (position > 0 && position < fragments.size() - 1) {
                    PrefUtils.putInt(MainActivity.this, AppConfig.KEY_PAGE, position - 1);//保存当前页面位置 -1 -  XX
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyFragmentAdapter extends FragmentStatePagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
