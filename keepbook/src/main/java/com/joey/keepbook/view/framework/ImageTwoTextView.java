package com.joey.keepbook.view.framework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/2/15.
 */
public abstract class ImageTwoTextView extends LinearLayout {
    private TextView tvFirst;
    private TextView tvSecond;
    private OnClickListener listener;
    private LinearLayout ll;

    public ImageTwoTextView(Context context, AttributeSet attrs) {
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
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageTwoTextView, 0, 0);
        String desc = ta.getString(R.styleable.ImageTwoTextView_image_two_text_text_first);
        String money = ta.getString(R.styleable.ImageTwoTextView_image_two_text_text_second);
        int srcId = ta.getResourceId(R.styleable.DetailView_detail_src, 0);
        setSrcId(srcId);
        setTvSecond(money);
        setTvFirst(desc);
    }

    /**
     * 初始化View
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_image_two_text, this);
        tvFirst = (TextView) findViewById(R.id.tv_image_two_text_first);
        tvSecond = (TextView) findViewById(R.id.tv_image_two_text_second);
        ll = (LinearLayout) findViewById(R.id.ll_image_two_text);
        setTextAttribute(tvFirst, tvSecond);
    }


    /**
     * 设置描述信息
     *
     * @param desc
     */
    public void setTvFirst(String desc) {
        tvFirst.setText(desc);
    }

    /**
     * 设置结果
     *
     * @param result
     */
    public void setTvSecond(String result) {
        tvSecond.setText(result);
    }

    /**
     * 设置图片源文件
     *
     * @param srcId
     */
    public void setSrcId(int srcId) {
        Drawable drawable= getResources().getDrawable(srcId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFirst.setCompoundDrawables(drawable, null, null, null);
    }
    /**
     * 设置属性
     * @param tvFirst
     * @param tvSecond
     */
    protected abstract void setTextAttribute(TextView tvFirst, TextView tvSecond);

    public void setViewListener(OnClickListener listener){
        ll.setOnClickListener(listener);
    }
}
