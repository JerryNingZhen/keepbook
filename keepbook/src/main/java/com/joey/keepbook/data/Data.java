package com.joey.keepbook.data;

import com.joey.keepbook.manager.MyActivityManager;

/**
 * Created by Joey on 2016/3/8.
 */
public class Data {
    private static Data data;
    private static DbConstant dbConstant;
    private static GlobalVariable globalVariable;

    private Data() {
    }

    public static Data getInstance() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    //获取db常量
    public DbConstant getDbConstant() {
        if (dbConstant == null) {
            dbConstant = DbConstant.getInstance();
        }
        return dbConstant;
    }

    //获取全局变量
    public GlobalVariable getGlobalVariable(){
        if (globalVariable==null){
            globalVariable=GlobalVariable.getInstance();
        }
        return globalVariable;
    }

    public void close(){
        if (dbConstant!=null){
            dbConstant=null;
        }
        if (globalVariable!=null){
            globalVariable=null;
        }
        if (data!=null){
            data=null;
        }
    }
}
