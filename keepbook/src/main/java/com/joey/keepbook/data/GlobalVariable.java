package com.joey.keepbook.data;

/**
 * Created by Joey on 2016/3/10.
 */
public class GlobalVariable {
    private static GlobalVariable globalVariable;

    private GlobalVariable() {
    }

    public static GlobalVariable getInstance() {
        if (globalVariable == null) {
            globalVariable = new GlobalVariable();
        }
        return globalVariable;
    }


    //记账
    private  int bookState = 0;
    //页面
    private  int intPage = 0;

    public int getBookState() {
        return bookState;
    }

    public int getIntPage() {
        return intPage;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }

    public void setIntPage(int intPage) {
        this.intPage = intPage;
    }
}
