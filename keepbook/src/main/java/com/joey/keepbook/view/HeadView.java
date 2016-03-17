package com.joey.keepbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/2/15.
 */
public class HeadView extends LinearLayout {

    private Button bt_view_head;

    public HeadView(Context context, AttributeSet attrs) {
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

    }

    /**
     * 初始化View
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_head, this);
        bt_view_head = (Button) findViewById(R.id.bt_view_head);

    }

    public void setHeadButtonClickListener(OnClickListener listener) {
        bt_view_head.setOnClickListener(listener);
    }


}
