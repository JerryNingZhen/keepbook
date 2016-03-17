package com.joey.keepbook.data;

/**
 * Created by Joey on 2016/3/10.
 */
public class DbConstant {
    private static DbConstant dbConstant;
    private DbConstant() {
    }
    public static DbConstant getInstance() {
        if (dbConstant ==null){
            dbConstant =new DbConstant();
        }
        return dbConstant;
    }

    /**
     * classes table
     * classes  varchar(10)
     * classify smallint
     * page     smallint
     */
    public  int classesVersion=1;
    public  final String classesTableName="classes";
    public  final String[][] classesColumns = {
            {"classes", "classify","page"},
            {"varchar(10)", "smallint","smallint"}
    };

    /**
     * bill table
     * date     smallint
     * money    float
     * remark   varchar(20)
     * classes  varchar(10)
     * classify smallint
     * page     smallint
     */
    public static final int billVersion=1;
    public  int billId=0;
    public  final String billTableName="bill";
    public  final String[][] billColumns = {
            {"date", "money", "remark", "classes", "classify","page"},
            {"smallint", "float", "varchar(20)", "varchar(10)", "smallint","smallint"}
    };


    /**
     * page table
     * page     smallint
     * title    varchar(10)
     */
    public  int pageVersion=1;
    public  final String pageTableName="page";
    public  final String[][] pageColumns = {
            {"page", "title"},
            {"smallint", "varchar(10)"}
    };

}
