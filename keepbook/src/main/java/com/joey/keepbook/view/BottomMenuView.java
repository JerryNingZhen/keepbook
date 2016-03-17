package com.joey.keepbook.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/2/15.
 */
public class BottomMenuView extends LinearLayout {

    private TextView tvDesc;
    private ImageView iv;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public BottomMenuView(Context context, AttributeSet attrs) {
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
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainView, 0, 0);
        String desc = ta.getString(R.styleable.MainView_main_desc);
        int srcId = ta.getResourceId(R.styleable.MainView_main_src, 0);
        setSrcId(srcId);
        setTvDesc(desc);
    }

    /**
     * 初始化View
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_main_bottom_menu, this);
        tvDesc = (TextView) findViewById(R.id.tv_view_main);
        iv = (ImageView) findViewById(R.id.iv_view_main);
    }

    /**
     * 设置描述信息
     *
     * @param desc
     */
    public void setTvDesc(String desc) {
        tvDesc.setText(desc);
    }

    /**
     * 设置图片源文件
     *
     * @param srcId
     */
    public void setSrcId(int srcId) {
        iv.setImageResource(srcId);
    }
}
