package com.joey.keepbook.bean;

/**
 * classes table
 * classes  varchar(10)
 * classify smallint
 */
public class Classes {
    private String classes;
    private int classify;
    private int page;


    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Classes(String classes, int classify,int page) {
        this.classes = classes;
        this.classify = classify;
        this.page=page;
    }

    public String getClasses() {
        return classes;
    }

    public int getClassify() {
        return classify;
    }

    public int getPage() {
        return page;
    }

    /**
     * classes  varchar(10)
     * classify smallint
     */
    @Override
    public String toString() {
        return "Classes class classes="+classes+" classify="+classify+" page="+page;
    }
}
