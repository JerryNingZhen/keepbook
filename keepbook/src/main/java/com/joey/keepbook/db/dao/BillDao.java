package com.joey.keepbook.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.AppConstant;
import com.joey.keepbook.db.BillOpenHelper;
import com.joey.keepbook.db.base.BaseDao;
import java.util.ArrayList;
import java.util.List;
import com.joey.keepbook.db.event.DbEvent;

/**
 *
 */
public class BillDao extends BaseDao {
    private static BillDao dao;
    private BillOpenHelper mHelper;
    private static AppConstant appConstant = AppConstant.getInstance();
    private BillDao(Context context) {
        mHelper = new BillOpenHelper(context);
    }

    /**
     * 单例
     */
    public static BillDao getInstance(Context context) {
        if (dao == null) {
            dao = new BillDao(context);
        }
        return dao;
    }

    /**
     * insert   添加
     * date     smallint
     * money    float
     * remark   varchar
     * classes  varchar
     * classify smallint
     * page     smallint
     */
    public long insert(Bill bill) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(appConstant.billColumns[0][0], bill.getDate());
        values.put(appConstant.billColumns[0][1], bill.getMoney());
        values.put(appConstant.billColumns[0][2], bill.getRemark());
        values.put(appConstant.billColumns[0][3], bill.getClasses());
        values.put(appConstant.billColumns[0][4], bill.getClassify());
        values.put(appConstant.billColumns[0][5], bill.getPage());
        long result = db.insert(appConstant.billTableName, null, values);
        db.close();
        values.clear();
        handleChanged(new DbEvent(DbEvent.insertEvent,bill));
        return result;
    }

    /**
     * deleteAll   删全部
     */
    public int deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(appConstant.billTableName, null, null);
        db.close();
        handleChanged(new DbEvent(DbEvent.deleteAllEvent,null));
        return count;
    }

    /**
     * delete   删
     * date
     */
    public int delete(Bill bill) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(appConstant.billTableName, "date = ? and page = ?",
                new String[]{String.valueOf(bill.getDate()), String.valueOf(bill.getPage())});
        db.close();
        handleChanged(new DbEvent(DbEvent.deleteEvent,bill));
        return count;
    }

    /**
     * query    查
     * date     smallint
     * money    float
     * remark   varchar
     * classes  varchar
     * classify smallint
     * page     smallint
     */
    public List<Bill> query(int page) {
        List<Bill> billList = new ArrayList<Bill>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(appConstant.billTableName, appConstant.billColumns[0], "page = ?", new String[]{String.valueOf(page)}, null, null, null);
        while (cursor.moveToNext()) {
            long date = cursor.getLong(0);
            float money = cursor.getFloat(1);
            String remark = cursor.getString(2);
            String classes = cursor.getString(3);
            int classify = cursor.getInt(4);
            billList.add(new Bill(date, money, remark, classes, classify, page));
        }
        /**
         * 数据顺序，倒过来。
         */
        List<Bill> bills = new ArrayList<Bill>();
        for (int i = billList.size() - 1; i >= 0; i--) {
            bills.add(billList.get(i));
        }
        billList = bills;
        handleChanged(new DbEvent(DbEvent.queryEvent,null));
        return billList;
    }

    /**
     * query    查
     * date     smallint    0
     * money    float       1
     * remark   varchar     2
     * classes  varchar     3
     * classify smallint    4
     * page     smallint    5
     * Map      date,<activityData,money>
     */
    public List<Bill> queryAll() {
        List<Bill> billList = new ArrayList<Bill>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(appConstant.billTableName, appConstant.billColumns[0], null, null, null, null, null);
        while (cursor.moveToNext()) {
            long date = cursor.getLong(0);
            float money = cursor.getFloat(1);
            String remark = cursor.getString(2);
            String classes = cursor.getString(3);
            int classify = cursor.getInt(4);
            int page = cursor.getInt(5);
            billList.add(new Bill(date, money, remark, classes, classify, page));
        }
        /**
         * 数据顺序，倒过来。
         */
        List<Bill> bills = new ArrayList<Bill>();
        for (int i = billList.size() - 1; i >= 0; i--) {
            bills.add(billList.get(i));
        }
        billList = bills;
        handleChanged(new DbEvent(DbEvent.queryEvent,null));
        return billList;
    }


}
