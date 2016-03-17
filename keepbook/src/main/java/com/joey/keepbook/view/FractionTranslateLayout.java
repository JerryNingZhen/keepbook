package com.joey.keepbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Joey on 2016/3/14.
 */
public class FractionTranslateLayout extends LinearLayout {
    private int screenWidth;
    private float fractionX;
    private OnLayoutTranslateListener onLayoutTranslateListener;

    public FractionTranslateLayout(Context context) {
        super(context);
    }

    public FractionTranslateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FractionTranslateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenWidth = w;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public float getFractionX() {
        return fractionX;
    }


    public void setFractionX(float xFraction) {
        this.fractionX = xFraction;
        //设置平移量-设置x坐标值。起始为0
        setX((screenWidth > 0) ? (fractionX * screenWidth) : 0);
        //根据位置设置透明度
        if (xFraction == 1 || xFraction == -1) {
            //移出 设置为透明
            setAlpha(0);
        } else if (xFraction < 1 /* enter */ || xFraction > -1 /* exit */) {
            //未移出 设置为可见
            if (getAlpha() != 1) {
                setAlpha(1);
            }
        }

        if (onLayoutTranslateListener != null) {
            onLayoutTranslateListener.onLayoutTranslate(this, xFraction);
        }
    }

    public void setOnLayoutTranslateListener(OnLayoutTranslateListener onLayoutTranslateListener) {
        this.onLayoutTranslateListener = onLayoutTranslateListener;
    }

    public static interface OnLayoutTranslateListener {
        void onLayoutTranslate(FractionTranslateLayout view, float xFraction);
    }

}
