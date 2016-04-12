package com.joey.keepbook.activity;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.R;
import com.joey.keepbook.utils.PrefUtils;

/**
 * book in fragment
 */
public class BookInFragment extends BookFragment {
    private static final String TAG = "调试BookInFragment";

    @Override
    public void onStart() {
        super.onStart();
        getTvMoney().setTextColor(getResources().getColor(R.color.green));
        String strClasses = PrefUtils.getString(getActivity(), AppConfig.KEY_IN_CLASSES + getPage(), "");
        getTvClasses().setText(strClasses);
    }
}
