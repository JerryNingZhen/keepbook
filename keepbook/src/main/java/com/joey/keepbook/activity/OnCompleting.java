package com.joey.keepbook.activity;

import android.os.Bundle;
import android.view.View;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.base.BaseActivity;

/**
 * Created by joey on 2016/4/12.
 */
public class OnCompleting extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.on_completing, null);
        setContentView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
