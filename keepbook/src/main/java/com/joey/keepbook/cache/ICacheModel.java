package com.joey.keepbook.cache;

/**
 * 缓存
 */
public interface ICacheModel {
    String DB_BILL ="bill";
    String DB_CLASSES ="classes";
    String DB_PAGE ="page";
    void insert(String  cacheName,Object event);
    void delete(String cacheName,Object event);
    void deleteAll(String cacheName,Object event);
    void query(String cacheName,Object event);
    void replace(String cacheName, Object event);
}
