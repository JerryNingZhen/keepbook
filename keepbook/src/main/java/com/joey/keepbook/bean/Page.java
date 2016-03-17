package com.joey.keepbook.bean;

/**
 * Created by Joey on 2016/3/13.
 */
public class Page {
    /**
     * page table
     * page     smallint
     * title    varchar(10)
     */
    private int page;
    private String title;

    public Page(int page, String title) {
        this.page = page;
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Page: page="+page+" title="+title;
    }
}
