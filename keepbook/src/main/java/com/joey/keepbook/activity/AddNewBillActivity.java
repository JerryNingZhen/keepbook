package com.joey.keepbook.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.joey.keepbook.R;
import com.joey.keepbook.base.BaseActivity;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.utils.InputMethodUtils;
import com.joey.keepbook.view.HeadView;

/**
 * Created by Joey on 2016/3/13.
 */
public class AddNewBillActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFinalData();
        initUI();//初始化UI
    }

    private void initFinalData() {

    }

    private void initUI() {
        setContentView(R.layout.activity_add_new_bill);
        HeadView headView = (HeadView) findViewById(R.id.hv_add_new_bill);
        Button btnCancel = (Button)findViewById(R.id.bt_add_new_bill_cancel);
        Button btnOk = (Button)findViewById(R.id.bt_add_new_bill_ok);
        LinearLayout ll= (LinearLayout) findViewById(R.id.ll_add_new_bill);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hide(v);
            }
        });

        final EditText editTitle = (EditText)findViewById(R.id.et_add_new_bill);
        //顶端按钮被点击
        headView.setHeadButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewBillActivity.this, HomeActivity.class));
                finish();
            }
        });


        //取消按钮被点击
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewBillActivity.this, HomeActivity.class));
                finish();
            }
        });

        //确认按钮被点击
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageDao dao = PageDao.getInstance(AddNewBillActivity.this);
                String strTitle = editTitle.getText().toString().trim();
                int intCount = dao.getCount() + 1;
                dao.insert(new Page(intCount, strTitle));
                startActivity(new Intent(AddNewBillActivity.this, HomeActivity.class));
                finish();
            }
        });
    }


}
