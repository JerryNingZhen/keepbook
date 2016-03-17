package com.joey.keepbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.joey.keepbook.R;

/**
 * Created by Joey on 2016/3/1.
 */
public class CalcView extends LinearLayout implements View.OnClickListener {
    private CalcView.CalcListener listener;
    private Button bt_calc_as;
    private Button bt_calc_delete;
    private Button bt_calc_ok;
    private Button bt_calc_0;
    private Button bt_calc_1;
    private Button bt_calc_2;
    private Button bt_calc_3;
    private Button bt_calc_4;
    private Button bt_calc_5;
    private Button bt_calc_6;
    private Button bt_calc_7;
    private Button bt_calc_8;
    private Button bt_calc_9;
    private Button bt_calc_point;

    public CalcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化界面
     */
    private void initView() {
        inflate(getContext(), R.layout.view_calc, this);
        /**
         * 找到所有的button 0-9 .
         */
        bt_calc_0 = (Button) findViewById(R.id.bt_calc_0);
        bt_calc_1 = (Button) findViewById(R.id.bt_calc_1);

        bt_calc_2 = (Button) findViewById(R.id.bt_calc_2);
        bt_calc_3 = (Button) findViewById(R.id.bt_calc_3);
        bt_calc_4 = (Button) findViewById(R.id.bt_calc_4);
        bt_calc_5 = (Button) findViewById(R.id.bt_calc_5);
        bt_calc_6 = (Button) findViewById(R.id.bt_calc_6);
        bt_calc_7 = (Button) findViewById(R.id.bt_calc_7);
        bt_calc_8 = (Button) findViewById(R.id.bt_calc_8);
        bt_calc_9 = (Button) findViewById(R.id.bt_calc_9);
        bt_calc_point = (Button) findViewById(R.id.bt_calc_point);

        /**
         * 计算器 删除 确定
         */
        bt_calc_as = (Button) findViewById(R.id.bt_calc_as);
        bt_calc_delete = (Button) findViewById(R.id.bt_calc_delete);
        bt_calc_ok = (Button) findViewById(R.id.bt_calc_ok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_calc_0:
                listener.inputNumberChange("0");
                break;
            case R.id.bt_calc_1:
                listener.inputNumberChange("1");
                break;
            case R.id.bt_calc_2:
                listener.inputNumberChange("2");
                break;
            case R.id.bt_calc_3:
                listener.inputNumberChange("3");
                break;
            case R.id.bt_calc_4:
                listener.inputNumberChange("4");
                break;
            case R.id.bt_calc_5:
                listener.inputNumberChange("5");
                break;
            case R.id.bt_calc_6:
                listener.inputNumberChange("6");
                break;
            case R.id.bt_calc_7:
                listener.inputNumberChange("7");
                break;
            case R.id.bt_calc_8:
                listener.inputNumberChange("8");
                break;
            case R.id.bt_calc_9:
                listener.inputNumberChange("9");
                break;
            case R.id.bt_calc_point:
                listener.inputNumberChange(".");
                break;
            default:
                break;
        }
    }

    /**
     * 数字被点击监听
     *
     * @param listener
     */
    public void setInputOnClickListener(CalcListener listener) {
        this.listener = listener;
        /**
         * 初始化点击监听
         */
        bt_calc_0.setOnClickListener(this);
        bt_calc_1.setOnClickListener(this);
        bt_calc_2.setOnClickListener(this);
        bt_calc_3.setOnClickListener(this);
        bt_calc_4.setOnClickListener(this);
        bt_calc_5.setOnClickListener(this);
        bt_calc_6.setOnClickListener(this);
        bt_calc_7.setOnClickListener(this);
        bt_calc_8.setOnClickListener(this);
        bt_calc_9.setOnClickListener(this);
        bt_calc_point.setOnClickListener(this);
    }

    /**
     * 计算器按钮被点击
     *
     * @param clickListener
     */
    public void calcAsOnClickListener(OnClickListener clickListener) {
        bt_calc_as.setOnClickListener(clickListener);
    }

    /**
     * 删除按钮被点击
     *
     * @param clickListener
     */
    public void setDeleteOnClickListener(OnClickListener clickListener) {
        if (clickListener != null)
            bt_calc_delete.setOnClickListener(clickListener);
    }

    /**
     * 确定按钮被点击
     *
     * @param clickListener
     */
    public void okOnClickListener(OnClickListener clickListener) {
        if (clickListener != null)
            bt_calc_ok.setOnClickListener(clickListener);
    }

    public interface CalcListener {
        //计算器
        void inputNumberChange(String inputNum);
    }
}
