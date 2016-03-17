package com.joey.keepbook.fragment;

import android.content.Intent;

import com.joey.keepbook.R;
import com.joey.keepbook.fragment.BookFragment;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;

import java.util.List;

/**
 * Created by Joey on 2016/2/23.
 */
public class BookInFragment extends BookFragment {
    private static final String TAG = "调试BookInFragment";
    private final String mDefStrClasses="工资";
    @Override
    protected void initFinalData() {
        super.initFinalData();
        LogUtils.e(TAG, "initFinalData..");
        mIntMoneyColor = getResources().getColor(R.color.green);
        mIntState=mIntStateIn;
        classes= PrefUtils.getString(mActivity,mStrKeyClassesIn+mIntPage,null);
        if (classes==null){
            //从数据库中查找
            List<Classes> classesList = mDao.query(mIntPage);
            for (Classes c : classesList) {
                if (c.getClassify() == mIntState) {
                    classes = c.getClasses();
                    break;
                }
            }
            if (classes==null){
                //从数据库中查找 仍然为空
                //增加一个默认值 并保存至db和pref
                classes = mDefStrClasses;
                mDao.insert(new Classes(classes,mIntState,mIntPage));
                PrefUtils.putString(mActivity,mStrKeyClassesIn+mIntPage,classes);
            }
        }
    }

    @Override
    public void onReceiveActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==mIntStateIn) {
            if (resultCode == mIntResultOK) {
                classes = data.getStringExtra(mStrKeyResult);
                PrefUtils.putString(mActivity,mStrKeyClassesIn+mIntPage,classes);
                setMoneyText(classes);
            }
        }
        LogUtils.e(TAG, "onReceiveActivityResult: " + requestCode + resultCode + classes);
    }
}
