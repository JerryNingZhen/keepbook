package com.joey.keepbook.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.view.base.ImageTwoTextView;

/**
 * Created by Joey on 2016/3/4.
 */
public class HomeBodyView extends ImageTwoTextView {
    public HomeBodyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setTextAttribute(TextView tvFirst, TextView tvSecond) {
        Resources resources = getResources();
        //设置字体颜色位白色
        tvFirst.setTextColor(resources.getColor(R.color.black));
        tvSecond.setTextColor(resources.getColor(R.color.gray));
        //设置字体大小
        tvFirst.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.text_size_body));
        tvSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.text_size_body_small));
    }
}
