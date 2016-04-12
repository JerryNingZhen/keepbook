package com.joey.keepbook.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.ClassesOpenHelper;
import com.joey.keepbook.db.base.BaseDao;
import com.joey.keepbook.db.event.DbEvent;
import com.joey.keepbook.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/6.
 */
public class ClassesDao extends BaseDao {
    private static final String TAG = "调试ClassesDao";
    private static ClassesDao mDao;
    private ClassesOpenHelper mHelper;
    private static String mTableName;
    private static String[][] mColumns;

    protected ClassesDao(Context context) {
        mHelper = new ClassesOpenHelper(context);
    }


    public static ClassesDao getInstance(Context context) {
        if (mDao == null) {
            mDao = new ClassesDao(context);
            AppConstant appConstant = AppConstant.getInstance();
            mTableName = appConstant.classesTableName;
            mColumns = appConstant.classesColumns;
        }
        return mDao;
    }

    /**
     * insert   添加
     * classes  varchar(10)
     * classify smallint
     * page     smallint
     */
    public long insert(Classes classes) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mColumns[0][0], classes.getClasses());
        values.put(mColumns[0][1], classes.getClassify());
        values.put(mColumns[0][2], classes.getPage());
        long result = db.insert(mTableName, null, values);
        db.close();
        values.clear();
        LogUtils.e(TAG, "insert: " + classes.toString());
        handleChanged(new DbEvent(DbEvent.insertEvent, classes));
        return result;
    }

    /**
     * deleteAll   删全部
     */
    public int deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(mTableName, null, null);
        db.close();
        handleChanged(new DbEvent(DbEvent.deleteAllEvent, null));
        return count;
    }

    /**
     * delete   删
     * classes
     * page     smallint
     */
    public int delete(Classes classes) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(mTableName, "classes = ? and page = ?",
                new String[]{String.valueOf(classes.getClasses()), String.valueOf(classes.getPage())});
        db.close();
        LogUtils.e(TAG, "delete: " + classes.toString());
        handleChanged(new DbEvent(DbEvent.deleteEvent, classes));
        return count;
    }


    /**
     * insert   查
     * classes  varchar(10)
     * classify smallint
     * page     smallint
     */
    public List<Classes> query(int page) {
        List<Classes> classesList = new ArrayList<Classes>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(mTableName, mColumns[0], "page = ?", new String[]{String.valueOf(page)}, null, null, null);
        while (cursor.moveToNext()) {
            String classes = cursor.getString(0);
            int classify = cursor.getInt(1);
            Classes c = new Classes(classes, classify, page);
            classesList.add(c);
        }
        handleChanged(new DbEvent(DbEvent.queryEvent,null));
        return classesList;
    }

    /**
     * insert   查
     * classes  varchar(10)
     * classify smallint
     * page     smallint
     */
    public List<Classes> query(int classify,int page) {
        List<Classes> classesList = new ArrayList<Classes>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(mTableName, mColumns[0], "page = ? and classify = ?",
                new String[]{String.valueOf(page), String.valueOf(classify)}, null, null, null);
        while (cursor.moveToNext()) {
            String classes = cursor.getString(0);
            Classes c = new Classes(classes, classify, page);
            classesList.add(c);
        }
        handleChanged(new DbEvent(DbEvent.queryEvent,null));
        return classesList;
    }

    public void close() {
        mColumns =null;
        mTableName=null;
        mHelper=null;
        mDao=null;
    }

    public List<Classes> query() {
        List<Classes> classesList = new ArrayList<Classes>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(mTableName, mColumns[0], null, null, null, null, null);
        while (cursor.moveToNext()) {
            String classes = cursor.getString(0);
            int classify = cursor.getInt(1);
            int page = cursor.getInt(2);
            Classes c = new Classes(classes, classify, page);
            classesList.add(c);
        }
        handleChanged(new DbEvent(DbEvent.queryEvent,null));
        return classesList;
    }
}
