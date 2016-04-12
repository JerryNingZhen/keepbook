package com.joey.keepbook.db.event;

/**
 * DB 监听
 */
public interface IDbListener {
    void handleDbChanged(IDbEvent event);
}
