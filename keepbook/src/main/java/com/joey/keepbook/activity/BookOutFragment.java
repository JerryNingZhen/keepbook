package com.joey.keepbook.activity;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.R;
import com.joey.keepbook.utils.PrefUtils;

/**
 * book out fragment
 */
public class BookOutFragment extends BookFragment{
    @Override
    public void onStart() {
        super.onStart();
        getTvMoney().setTextColor(getResources().getColor(R.color.red));
        String strClasses = PrefUtils.getString(getActivity(), AppConfig.KEY_OUT_CLASSES + getPage(), "");
        getTvClasses().setText(strClasses);
    }
}
