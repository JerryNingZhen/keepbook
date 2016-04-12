package com.joey.keepbook.db.event;

/**
 * DB 事件
 */
public interface IDbEvent {
    String insertEvent = "INSERT_EVENT";
    String deleteEvent = "DELETE_EVENT";
    String deleteAllEvent = "DELETE_ALL_EVENT";
    String queryEvent = "QUERY_EVENT";
    String replace= "REPLACE_EVENT";

    String getEvent();
    Object getObject();
}
