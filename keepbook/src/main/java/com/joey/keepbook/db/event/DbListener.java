package com.joey.keepbook.db.event;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.utils.LogUtils;

/**
 * 默认的 Db 监听
 */
public class DbListener implements IDbListener {
    private static final String TAG = "调试DbListener";
    @Override
    public void handleDbChanged(IDbEvent event) {
        Object object = event.getObject();
        if (object instanceof Bill) {
            //Bill数据库被修改
            onChanged(event,Bill.class.getName());

        } else if (object instanceof Classes) {
            //Classes db 被修改
            onChanged(event,Classes.class.getName());

        } else if (object instanceof Page) {
            //Page db 被修改
            onChanged(event,Page.class.getName());

        }
    }

    private void onChanged(IDbEvent event, String className) {
        String strEvent = event.getEvent();
        if (strEvent.equals(IDbEvent.deleteAllEvent)) {
            LogUtils.e(TAG, className + " is deleted all!!");
        } else if (strEvent.equals(IDbEvent.deleteEvent)) {
            LogUtils.e(TAG, className + " is deleted one item!!");
        } else if (strEvent.equals(IDbEvent.insertEvent)) {
            LogUtils.e(TAG, className + " is inserted one item!!");
        }else if (strEvent.equals(IDbEvent.queryEvent)) {
            LogUtils.e(TAG, className + " is query!!");
        }
    }

}
