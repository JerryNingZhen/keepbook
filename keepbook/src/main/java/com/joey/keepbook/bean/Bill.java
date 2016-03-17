package com.joey.keepbook.bean;



public class Bill {
    /**
     * date     smallint
     * money    float
     * remark   varchar
     * classes  varchar
     * classify smallint
     * page     smallint
     */
    private long date;
    private float money;
    private String remark;
    private String classes;
    private int classify;
    private int page;



    public Bill(long date, float money, String remark, String classes, int classify,int page) {
        this.date = date;
        this.money = money;
        this.remark = remark;
        this.classes = classes;
        this.classify = classify;
        this.page=page;
    }

    public long getDate() {
        return date;
    }

    public float getMoney() {
        return money;
    }

    public String getRemark() {
        return remark;
    }

    public String getClasses() {
        return classes;
    }

    public int getClassify() {
        return classify;
    }

    public int getPage(){return page;}
    /**
     * date     smallint
     * money    float
     * remark   varchar
     * classes  varchar
     * classify smallint
     */
    @Override
    public String toString() {
        return "Bill class date="+date+" money="+money+" remark="+remark+
                " classes="+classes+" classify="+classify+" page="+page;
    }
}
