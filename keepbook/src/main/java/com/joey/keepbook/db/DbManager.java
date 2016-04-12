package com.joey.keepbook.db;

import android.content.Context;

import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.cache.ICacheModel;
import com.joey.keepbook.db.event.IDbEvent;
import com.joey.keepbook.db.event.IDbListener;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.utils.LogUtils;

/**
 * db model
 * 关联数据库，监听数据库数据变化，并分发给 缓存管理者 CacheModel
 */
public class DbManager {
    private static DbManager mManager;
    private IDbListener mDbListener;
    private ICacheModel cache;

    /**
     * 单例
     */
    public static DbManager getInstance() {
        if (mManager == null) {
            mManager = new DbManager();
        }
        return mManager;
    }

    /**
     * 同步 cache manager
     */
    public DbManager syncToCache(ICacheModel cacheModel) {
        this.cache = cacheModel;
        return this;
    }

    //构造
    private DbManager() {
        mDbListener = getDbListener();
    }

    //获取 cache
    private ICacheModel getCache() {
        if (cache == null) {
            cache = new InnerCacheModel();
        }
        return cache;
    }

    //获取 db changed listener
    private IDbListener getDbListener() {
        if (mDbListener == null) {
            mDbListener = new InnerDbListener();
        }
        return mDbListener;
    }

    public void bindCache(ICacheModel cache) {
        this.cache = cache;
    }


    /**
     * 获取 bill dao
     */
    public BillDao getBillDao(Context context) {
        BillDao dao = BillDao.getInstance(context);
        dao.setDbChangedListener(mDbListener);
        return dao;
    }

    /**
     * 获取 classes dao
     */
    public ClassesDao getClassesDao(Context context) {
        ClassesDao dao = ClassesDao.getInstance(context);
        dao.setDbChangedListener(mDbListener);
        return dao;
    }

    /**
     * 获取 Page dao
     */
    public PageDao getPageDao(Context context) {
        PageDao dao = PageDao.getInstance(context);
        dao.setDbChangedListener(mDbListener);
        return dao;
    }

    /**
     * DB 修改监听器
     * 对数据进行分类，并分发
     */
    class InnerDbListener implements IDbListener {
        @Override
        public void handleDbChanged(IDbEvent event) {
            onDbChanged(event);
        }

        private void onDbChanged(IDbEvent event) {
            String strEvent = event.getEvent();
            Object obj = event.getObject();
            ICacheModel cache = getCache();
            /**
             * 设置 cache 类型
             * 1.bill cache
             * 2.classes cache
             * 3.page cache
             */
            String cacheType = "";
            if (obj instanceof Bill) {
                cacheType = ICacheModel.DB_BILL;
            } else if (obj instanceof Classes) {
                cacheType = ICacheModel.DB_CLASSES;
            } else if (obj instanceof Page) {
                cacheType = ICacheModel.DB_PAGE;
            }
            /**
             * 设置 事件类型
             * 增
             * 删
             * 改
             * 查
             */
            if (strEvent.equals(IDbEvent.insertEvent)) {
                cache.insert(cacheType, obj);
            } else if (strEvent.equals(IDbEvent.deleteEvent)) {
                cache.delete(cacheType, obj);
            } else if (strEvent.equals(IDbEvent.deleteAllEvent)) {
                cache.deleteAll(cacheType, obj);
            } else if (strEvent.equals(IDbEvent.queryEvent)) {
                cache.query(cacheType, obj);
            } else if (strEvent.equals(IDbEvent.replace)) {
                cache.replace(cacheType, obj);
            }

        }
    }

    class InnerCacheModel implements ICacheModel {
        @Override
        public void insert(String cacheName, Object event) {
            LogUtils.e("db manager 调用 内部默认 cache manager 数据库 " + cacheName + " 增加 一条数据");
        }

        @Override
        public void delete(String cacheName, Object event) {
            LogUtils.e("db manager 调用 内部默认 cache manager 数据库 " + cacheName + " 删除 一条数据");
        }

        @Override
        public void deleteAll(String cacheName, Object event) {
            LogUtils.e("db manager 调用 内部默认 cache manager 数据库 " + cacheName + " 删除 全部数据");
        }

        @Override
        public void query(String cacheName, Object event) {
            LogUtils.e("db manager 调用 内部默认 cache manager 数据库 " + cacheName + " 查询 了数据");
        }

        @Override
        public void replace(String cacheName, Object event) {
            LogUtils.e("db manager 调用 内部默认 cache manager 数据库 " + cacheName + " 修改 了数据");
        }
    }
}