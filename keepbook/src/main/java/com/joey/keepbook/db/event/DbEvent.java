package com.joey.keepbook.db.event;

/**
 * Created by joey on 2016/4/7.
 */
public class DbEvent implements IDbEvent {
    private String event;
    private Object object;
    public DbEvent(String event, Object object) {
        this.event = event;
        this.object = object;
    }
    @Override
    public String getEvent() {
        return event;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
