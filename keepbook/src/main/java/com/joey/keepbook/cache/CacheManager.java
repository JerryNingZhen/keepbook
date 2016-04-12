package com.joey.keepbook.cache;

import android.content.Context;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.App;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.DbManager;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.listener.IReadyListener;
import com.joey.keepbook.utils.DateUtils;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.utils.TraversalUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * cache model
 */
public class CacheManager implements ICacheModel {
    public static final String HOME_CACHE_MAP = "home_cache_map";
    public static final String HOME_CACHE = "home_cache";
    public static final String CHART_CACHE = "chart_cache";
    public static final String OTHER_CACHE = "other_cache";

    private static CacheManager mManager;
    private Cache mCache;
    private IReadyListener mReadyListener;

    private CacheManager() {
        mCache = new Cache();
    }

    /**
     * 单例
     */
    public static CacheManager getInstance() {
        if (mManager == null) {
            mManager = new CacheManager();
        }
        return mManager;
    }

    /**
     * 释放内存
     */
    public void clear() {
        mCache = null;
        mManager = null;
        mReadyListener = null;
    }

    /**
     * 初始化 缓存 信息
     */
    public void init(IReadyListener readyListener) {
        this.mReadyListener = readyListener;
        initCache();//初始化缓存信息
    }

    //初始化缓存信息
    private void initCache() {
        int launchCount = PrefUtils.getInt(getContext(), AppConfig.KEY_LAUNCH_COUNT, 1);
        DbManager.getInstance().bindCache(this);//绑定cache
        if (launchCount == 1) {//第一次 启动应用
            firstLaunch();
        }
        loadCacheFromPref();//加载 pref 中的信息
    }

    //第一次启动，初始化数据 将数据保存至 pref
    private void firstLaunch() {
        LogUtils.e("第一次启动应用，进行初始化，执行 firstLaunch()");
        //保存 chart cache 至pref
//        ChartCache chartCache = (ChartCache) getDefaultCache(CHART_CACHE);
//        saveChartCacheToPref(chartCache);
//        //测试用
//        float[] f1 = new float[60];
//        float[] f2 = new float[60];
//        for (int i = 0; i < 60; i++) {
//            f1[i] = i + 5;
//            f2[i] = i + 20;
//        }
//        chartCache.setWeeksIn(f1);
//        chartCache.setWeeksOut(f2);
//        saveChartCacheToPref(chartCache);

        //保存 chart cache 至pref
//        HomeCache homeCache = (HomeCache) getDefaultCache(HOME_CACHE);
//        saveHomeCacheToPref(homeCache);
//        //生成其它默认信息
//        setDefaultOtherCache(0);
//        PageDao.getInstance(getContext()).insert(new Page(0, "我的账单"));

    }

    private Object getDefaultCache(String cacheName) {
        if (cacheName.equals(HOME_CACHE)) {
            HomeCache homeCache = new HomeCache();
            homeCache.setThisMonthIn(0);
            homeCache.setThisMonthOut(0);
            homeCache.setTodayIn(0);
            homeCache.setTodayOut(0);
            homeCache.setPage(0);
            homeCache.setTitle("我的账单");
            homeCache.setHelp("作者：joey");
            return homeCache;
        } else if (cacheName.equals(CHART_CACHE)) {
            ChartCache chartCache = new ChartCache();
            chartCache.setWeeksIn(new float[60]);
            chartCache.setWeeksOut(new float[60]);
            return chartCache;
        }
        return null;
    }

    private void setDefaultOtherCache(int page) {
        ClassesDao.getInstance(getContext()).insert(new Classes("早餐", AppConfig.BOOK_OUT_FRAGMENT, page));
        ClassesDao.getInstance(getContext()).insert(new Classes("工资", AppConfig.BOOK_IN_FRAGMENT, page));
        PrefUtils.putString(getContext(), AppConfig.KEY_OUT_CLASSES + page, "早餐");
        PrefUtils.putString(getContext(), AppConfig.KEY_IN_CLASSES + page, "工资");
    }

    //加载 pref 中的信息
    private void loadCacheFromPref() {
        loadHomeCacheFromPref();
        loadChartCacheFromPref();
        getReadyListener().isReady();//通知 界面 缓存加载完毕
    }

    //get ready listener
    private IReadyListener getReadyListener() {
        if (mReadyListener == null) {
            mReadyListener = new InnerReadyListener();
        }
        return mReadyListener;
    }


    /**
     * 根据 缓存 名字 获取缓存 cache
     */
    public Object getCache(String cacheName) {
        return mCache.getCache(cacheName);
    }


    //读取 home cache config from config
    private void loadHomeCacheFromPref() {
        List<Page> pageList = PageDao.getInstance(getContext()).query();
        Map<Integer, HomeCache> homeCacheMap = new HashMap<Integer, HomeCache>();//新建一个map 用于存储 home cache
        for (Page page : pageList) {
            int intPage = page.getPage();
            String strCache = PrefUtils.getString(getContext(), AppConfig.KEY_HOME_CACHE + intPage, "");
            HomeCache homeCache = new HomeCache();
            homeCache.setPage(intPage);

            //title thisMonthIn thisMonthOut todayIn todayOut help
            String[] split = strCache.split(AppConfig.SYMBOL);
            TraversalUtils.array("cache manager load home str cache from pref", split);//log e
            for (int i = 0; i < split.length; i++) {
                switch (i) {
                    case 0:
                        homeCache.setTitle(split[i]);
                        break;
                    case 1:
                        homeCache.setThisMonthIn(Float.parseFloat(split[i]));
                        break;
                    case 2:
                        homeCache.setThisMonthOut(Float.parseFloat(split[i]));
                        break;
                    case 3:
                        homeCache.setTodayIn(Float.parseFloat(split[i]));
                        break;
                    case 4:
                        homeCache.setTodayOut(Float.parseFloat(split[i]));
                        break;
                    case 5:
                        homeCache.setHelp(split[i]);
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
            }
            homeCacheMap.put(page.getPage(), homeCache);
        }
        mCache.putCache(HOME_CACHE_MAP, homeCacheMap);
    }

    //保存 home cache config
    private void saveHomeCacheToPref(HomeCache cache) {
        String symbol = AppConfig.SYMBOL;
        //保存为 string 用 symbol 分隔
        //title thisMonthIn thisMonthOut todayIn todayOut help
        String strCache = cache.getTitle() + symbol +
                cache.getStrThisMonthIn() + symbol + cache.getStrThisMonthOut() + symbol +
                cache.getStrTodayIn() + symbol + cache.getStrTodayOut() + symbol +
                cache.getHelp();
        int page = cache.getPage();
        String key = AppConfig.KEY_HOME_CACHE + page;
        PrefUtils.putString(getContext(), key, strCache);
    }

    //读取 chart cache config from pref
    private void loadChartCacheFromPref() {
        String strChartCache = PrefUtils.getString(getContext(), AppConfig.KEY_CHART_CACHE, "");
        String[] split = strChartCache.split(AppConfig.SYMBOL);
        ChartCache chartCache = new ChartCache();
        float[] weeksIn = chartCache.getWeeksIn();
        float[] weeksOut = chartCache.getWeeksOut();
        for (int i = 0; i < split.length; i++) {
            if (i < weeksIn.length) {
                weeksIn[i] = Float.parseFloat(split[i]);
            } else if (i < weeksIn.length + weeksOut.length) {
                weeksOut[i - weeksIn.length] = Float.parseFloat(split[i]);
            }
        }
        chartCache.setWeeksIn(weeksIn);
        chartCache.setWeeksOut(weeksOut);
        TraversalUtils.array("cache manager 加载 数据 weeksIn", weeksIn);//log e
        TraversalUtils.array("cache manager 加载 数据 weeksOut", weeksOut);//log e
        mCache.putCache(CHART_CACHE, chartCache);
    }

    //保存 chart cache config
    private void saveChartCacheToPref(ChartCache cache) {
        String symbol = AppConfig.SYMBOL;
        String strWeeks = "";
        float[] weeksIn = cache.getWeeksIn();
        float[] weeksOut = cache.getWeeksOut();
        for (int i = 0; i < weeksIn.length; i++) {
            strWeeks = strWeeks + weeksIn[i] + symbol;
        }
        for (int i = 0; i < weeksOut.length; i++) {
            if (i == weeksOut.length - 1) {
                strWeeks = strWeeks + weeksOut[i];
            } else {
                strWeeks = strWeeks + weeksOut[i] + symbol;
            }
        }
        PrefUtils.putString(getContext(), AppConfig.KEY_CHART_CACHE, strWeeks);
        LogUtils.e("保存 chart cache 到 pref   " + strWeeks);
    }

    public Context getContext() {
        return App.getContextObject();
    }


    @Override
    public void insert(String cacheName, Object event) {
        if (cacheName.equals(DB_BILL)) {
            // bill
            Map<Integer, HomeCache> homeCacheMAP = (Map<Integer, HomeCache>) mCache.getCache(HOME_CACHE_MAP);
            ChartCache chartCache = (ChartCache) mCache.getCache(CHART_CACHE);
            if (homeCacheMAP != null && chartCache != null) {
                Bill bill = (Bill) event;
                int page = bill.getPage();
                if (homeCacheMAP.containsKey(page)) {
                    HomeCache homeCache = homeCacheMAP.get(page);
                    float[] weeksOut = chartCache.getWeeksOut();
                    float[] weeksIn = chartCache.getWeeksIn();
                    int weekNum = DateUtils.getWeekNum(bill.getDate());
                    switch (bill.getClassify()) {
                        case AppConfig.BOOK_IN_FRAGMENT:
                            homeCache.setTodayIn(homeCache.getTodayIn() + bill.getMoney());
                            homeCache.setThisMonthIn(homeCache.getThisMonthIn() + bill.getMoney());
                            weeksIn[weekNum - 1] = weeksIn[weekNum - 1] + bill.getMoney();
                            chartCache.setWeeksIn(weeksIn);
                            break;
                        case AppConfig.BOOK_OUT_FRAGMENT:
                            homeCache.setTodayOut(homeCache.getTodayOut() + bill.getMoney());
                            homeCache.setThisMonthOut(homeCache.getThisMonthOut() + bill.getMoney());
                            weeksOut[weekNum - 1] = weeksOut[weekNum - 1] + bill.getMoney();
                            chartCache.setWeeksOut(weeksOut);
                            break;
                    }
                    saveHomeCacheToPref(homeCache);
                    saveChartCacheToPref(chartCache);
                }
            }
        }
        if (cacheName.equals(DB_PAGE)) {
            //增加一页page
            Page page = (Page) event;
            Map<Integer, HomeCache> homeCacheMAP = (Map<Integer, HomeCache>) mCache.getCache(HOME_CACHE_MAP);
            if (!homeCacheMAP.containsKey(page.getPage())) {
                //生成默认 home cache
                HomeCache homeCache = (HomeCache) getDefaultCache(HOME_CACHE);
                homeCache.setPage(page.getPage());
                homeCache.setTitle(page.getTitle());
                saveHomeCacheToPref(homeCache);
                //生成默认 其它数据
                setDefaultOtherCache(page.getPage());
                homeCacheMAP.put(page.getPage(), homeCache);
            }
        }


    }

    @Override
    public void delete(String cacheName, Object event) {

    }

    @Override
    public void deleteAll(String cacheName, Object event) {

    }

    @Override
    public void query(String cacheName, Object event) {

    }

    @Override
    public void replace(String cacheName, Object event) {
        LogUtils.e("cache manager replace()  调用者=" + cacheName);
        if (cacheName.equals(DB_PAGE)) {
            Page page = (Page) event;
            int p = page.getPage();
            Map<Integer, HomeCache> map = (Map<Integer, HomeCache>) mCache.getCache(HOME_CACHE_MAP);
            if (map.containsKey(p)) {
                HomeCache homeCache = map.get(p);
                homeCache.setTitle(page.getTitle());
                saveHomeCacheToPref(homeCache);//保存修改
                LogUtils.e("cache manager page replace() 生成的新的 title =" + homeCache.getTitle());
            }
        }
    }

    public void writeTestData() {
        int count = 2;
        //写入1000条测试数据
        BillDao billDao = BillDao.getInstance(getContext());
        PageDao pageDao = PageDao.getInstance(getContext());
        ClassesDao classesDao = ClassesDao.getInstance(getContext());

        String[] strClassesIn = {"工资", "奖金", "股票", "基金", "赌博", "彩票"};
        String[] strClassesOut = {"早餐", "午餐", "晚餐", "夜宵", "休闲", "购物"};

        final int bookIn = AppConfig.BOOK_IN_FRAGMENT;
        final int bookOut = AppConfig.BOOK_OUT_FRAGMENT;

        Random random = new Random();
        final long ONE_YEAR = 31536000000L;//一年的毫秒值
        final long ONE_YEAR_AGO = System.currentTimeMillis() - ONE_YEAR;//一年前
        final String[] remarks = {"心情非常开心", "很轻松", "不差钱", "小意思"};

        for (int i = 0; i < count; i++) {//第一条数据在缓存中自动初始化
            pageDao.insert(new Page(i, "我的账单" + i));
            for (int j = 0; j < 6; j++) {
                classesDao.insert(new Classes(strClassesIn[j], bookIn, i));
                classesDao.insert(new Classes(strClassesOut[j], bookOut, i));
            }
            //每页增加 n 条数据
            for (int j = 0; j < 1000; j++) {
                float money = random.nextInt(500);
                long date = (long) (ONE_YEAR_AGO + ONE_YEAR * Math.random());
                String remark = remarks[random.nextInt(remarks.length - 1)];
                int classify = 0;
                String classes = "";
                switch (random.nextInt(3)) {
                    case 1:
                        classify = bookOut;
                        classes = strClassesOut[random.nextInt(strClassesOut.length - 1)];
                        break;
                    case 2:
                        classify = bookIn;
                        classes = strClassesIn[random.nextInt(strClassesIn.length - 1)];
                        break;
                    default:
                        classify = bookIn;
                        classes = strClassesIn[random.nextInt(strClassesIn.length - 1)];
                        break;
                }
                Bill bill = new Bill(date, money, remark, classes, classify, i);
                billDao.insert(bill);
            }
        }
        //chart 缓存
        ChartCache chartCache = new ChartCache();
        float[] weekIn = new float[60];
        float[] weekOut =  new float[60];
        //home 缓存
        Map<Integer, HomeCache> map = new HashMap<Integer, HomeCache>();
        for (int i = 0; i < count; i++) {
            HomeCache homeCache = new HomeCache();
            List<Bill> billList = billDao.query(i);
            float monthIn=0;
            float monthOut=0;
            float todayIn=0;
            float todayOut=0;
            for (Bill bill : billList) {
                long date = bill.getDate();
                int weekNum = DateUtils.getWeekNum(date);
                switch (bill.getClassify()) {
                    case bookIn:
                        weekIn[weekNum-1]= weekIn[weekNum-1]+bill.getMoney();
                        if (DateUtils.isThisMonth(date)){
                            monthIn=monthIn+bill.getMoney();
                            if (DateUtils.isToday(date)){
                                todayIn=todayIn+bill.getMoney();
                            }
                        }
                        break;
                    case bookOut:
                        weekOut[weekNum-1]= weekOut[weekNum-1]+bill.getMoney();
                        if (DateUtils.isThisMonth(date)){
                            monthOut=monthOut+bill.getMoney();
                            if (DateUtils.isToday(date)){
                                todayOut=todayOut+bill.getMoney();
                            }
                        }
                        break;
                }
            }
            homeCache.setThisMonthIn(monthIn);
            homeCache.setThisMonthOut(monthOut);
            homeCache.setTodayIn(todayIn);
            homeCache.setTodayOut(todayOut);
            homeCache.setPage(i);
            homeCache.setTitle("我的账单" + i);
            homeCache.setHelp("作者：joey");
            saveHomeCacheToPref(homeCache);
            PrefUtils.putString(getContext(), AppConfig.KEY_OUT_CLASSES + i, "早餐");
            PrefUtils.putString(getContext(), AppConfig.KEY_IN_CLASSES + i, "工资");
            map.put(i,homeCache);
        }
        //根据写入的测试数据，，计算缓存数据
        chartCache.setWeeksIn(weekIn);
        chartCache.setWeeksOut(weekOut);
        saveChartCacheToPref(chartCache);
    }

    class InnerReadyListener implements IReadyListener {
        @Override
        public void isReady() {
        }
    }
}
