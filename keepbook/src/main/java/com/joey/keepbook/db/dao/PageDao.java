package com.joey.keepbook.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joey.keepbook.bean.Page;
import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.PageOpenHelper;
import com.joey.keepbook.db.base.BaseDao;
import com.joey.keepbook.db.event.DbEvent;
import com.joey.keepbook.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joey on 2016/3/6.
 */
public class PageDao extends BaseDao {
    private static final String TAG = "PageDao";
    private static PageDao dao;
    private PageOpenHelper mHelper;
    private static String tableName;
    private static String[][] columns;

    protected PageDao(Context context) {
        mHelper = new PageOpenHelper(context);
    }

    public static PageDao getInstance(Context context) {
        if (dao == null) {
            dao = new PageDao(context);
            AppConstant appConstant = AppConstant.getInstance();
            tableName = appConstant.pageTableName;
            columns = appConstant.pageColumns;
        }
        return dao;
    }

    /**
     * insert   添加
     * page     smallint
     * title    varchar(10)
     */
    public long insert(Page page) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(columns[0][0], page.getPage());
        values.put(columns[0][1], page.getTitle());
        long result = db.insert(tableName, null, values);
        db.close();
        values.clear();
        LogUtils.e(TAG, "insert: " + page.toString());
        handleChanged(new DbEvent(DbEvent.insertEvent, page));
        return result;
    }

    /**
     * deleteAll   删全部
     */
    public int deleteAll() {
        handleChanged(new DbEvent(DbEvent.deleteAllEvent, null));
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(tableName, null, null);
        db.close();
        return count;
    }

    /**
     * delete   删
     * page     smallint
     * title    varchar(10)
     */
    public int delete(Page page) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(tableName, "page = ?",
                new String[]{String.valueOf(page.getPage())});
        db.close();
        LogUtils.e(TAG, "delete: " + page.toString());
        handleChanged(new DbEvent(DbEvent.deleteEvent, page));
        return count;
    }

    /**
     * insert   查
     * page     smallint
     * title    varchar(10)
     */
    public List<Page> query() {
        List<Page> pageList = new ArrayList<Page>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, columns[0], null, null, null, null, null);
        while (cursor.moveToNext()) {
            int page = cursor.getInt(0);
            String title = cursor.getString(1);
            Page c = new Page(page, title);
            pageList.add(c);
        }
        LogUtils.e(TAG, "delete: " + pageList.size());
        cursor.close();
        handleChanged(new DbEvent(DbEvent.queryEvent, null));
        return pageList;
    }

    public int getCount() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, columns[0], null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void replace(Page page){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(columns[0][0], page.getPage());
        values.put(columns[0][1], page.getTitle());
        db.update(tableName,values,"page = ?",new String[]{String.valueOf(page.getPage())});
        handleChanged(new DbEvent(DbEvent.replace, page));
    }
}
