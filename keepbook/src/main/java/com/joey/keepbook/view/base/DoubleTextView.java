package com.joey.keepbook.view.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/2/15.
 */
public abstract class DoubleTextView extends LinearLayout {

    private TextView tvSecond;
    private TextView tvFirst;

    public DoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initData(context, attrs);
    }

    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        //或许布局文件数据，并设置文本内容
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleTextView, 0, 0);
        String secondString = ta.getString(R.styleable.DoubleTextView_double_text_text_second);
        String firstString = ta.getString(R.styleable.DoubleTextView_double_text_text_first);
        setFirstText(firstString);
        setSecondText(secondString);
    }

    /**
     * 初始化View
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_double_text, this);
        tvFirst= (TextView) findViewById(R.id.double_text_view_tv_first);
        tvSecond = (TextView) findViewById(R.id.double_text_view_tv_second);
        setTextViewAttribute(tvFirst, tvSecond);
    }



    /**
     * 设置描述信息
     */
    public void setSecondText(String secondText) {
        tvSecond.setText(secondText);
    }

    /**
     * 设置数值
     */
    public void setFirstText(String firstText) {
        tvFirst.setText(firstText);
    }

    /**
     * 设置文本属性
     */
    protected abstract void setTextViewAttribute(TextView tvFirst, TextView tvSecond);
}
