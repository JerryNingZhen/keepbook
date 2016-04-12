package com.joey.keepbook.cache;

import com.joey.keepbook.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存
 */
public class Cache {
    private Map<String, Object> mCache;
    private Map<Integer, Object> mItemCache;

    public Cache() {
        mCache = new HashMap<String, Object>();
        mItemCache = new HashMap<Integer, Object>();
    }

    /**
     * 增加 一个缓存 缓存
     */
    public void putCache(String cacheName, Object object) {
        mCache.put(cacheName, object);
    }

    /**
     * 获取缓存信息
     */
    public Object getCache(String cacheName) {
        if (mCache.containsKey(cacheName)) {
            return mCache.get(cacheName);
        } else {
            LogUtils.e("getCache() 找不到 " + cacheName + " 缓存信息");
            return null;
        }
    }
}
