package com.joey.keepbook.db.base;

import com.joey.keepbook.db.event.DbListener;
import com.joey.keepbook.db.event.IDbEvent;
import com.joey.keepbook.db.event.IDbListener;

/**
 * db dao 基类
 */
public class BaseDao {
    private IDbListener mDbListener;
    /**
     * 绑定监听
     */
    public void setDbChangedListener(IDbListener listener){
        this.mDbListener=listener;
    }

    /**
     * 获取监听
     */
    protected IDbListener getListener(){
        if (mDbListener==null){
            //如果为空，则返回默认 Listener
            mDbListener=new DbListener();
        }
        return mDbListener;
    }

    public void handleChanged(IDbEvent IDbEvent){
        getListener().handleDbChanged(IDbEvent);
    }
}
