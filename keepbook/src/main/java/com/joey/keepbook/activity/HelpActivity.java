package com.joey.keepbook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.base.BaseActivity;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView tvHelpInfo= (TextView) findViewById(R.id.tv_help_information);
        StringBuffer sb=new StringBuffer();
        sb.append("本应用由joey原创,版权归joey所有\n");
        sb.append("用途：求职");
        tvHelpInfo.setText(sb.toString());
        tvHelpInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
