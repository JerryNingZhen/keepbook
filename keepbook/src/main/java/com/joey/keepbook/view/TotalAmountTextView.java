package com.joey.keepbook.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.view.base.DoubleTextView;

/**
 * Created by Joey on 2016/2/15.
 */
public class TotalAmountTextView extends DoubleTextView {

    public TotalAmountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setTextViewAttribute(TextView tvFirst, TextView tvSecond) {
        Resources resources = getResources();
        //设置字体颜色位白色
        tvFirst.setTextColor(resources.getColor(R.color.white));
        tvSecond.setTextColor(resources.getColor(R.color.white));
        //设置字体大小
        tvFirst.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.text_size_small));
        tvSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.text_size_small));
    }
}
