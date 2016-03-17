package com.joey.keepbook.view.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/2/15.
 */
public abstract class ImageTwoTextView extends LinearLayout {

    private TextView tvFirst;
    private TextView tvSecond;
    private ImageView ivFirst;
    private Button btFirst;

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
        ivFirst = (ImageView) findViewById(R.id.iv_image_two_text_first);
        btFirst = (Button) findViewById(R.id.bt_image_two_text_first);
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
        ivFirst.setImageResource(srcId);
    }

    /**
     * 设置点击监听
     * @param listener
     */
    public void setButtonOnClickListener(OnClickListener listener){
        btFirst.setOnClickListener(listener);
    }

    public void setButtonClickable(boolean clickable){
        btFirst.setClickable(clickable);
    }

    public void setButtonFocusable(boolean focusable){
        btFirst.setFocusable(focusable);
    }

    /**
     * 设置属性
     * @param tvFirst
     * @param tvSecond
     */
    protected abstract void setTextAttribute(TextView tvFirst, TextView tvSecond);

}
