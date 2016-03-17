package com.joey.keepbook.activity.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.HomeActivity;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.data.Data;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.engine.BillDateFormat;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.HeadView;

import java.util.List;

/**
 * Created by Joey on 2016/3/4.
 */
public abstract class BaseDetailActivity extends Activity {
    private ListView lv;
    private MyAdapter mAdapter;
    private TextView tvTitle;
    private BillDao dao;

    /**
     * view 属性
     */
    protected List<Bill> billList;
    protected String title = "";

    /**
     * 工具
     */
    protected BillDateFormat dateFormat;
    protected MyActivityManager activityManager;


    /**
     * 状态
     */
    protected int classify=-1;
    protected long today;

    /**
     * 创建界面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        dao = BillDao.getInstance(this);
        activityManager = MyActivityManager.getInstance();
        String strKeyPage= activityManager.KEY_PAGE;

        int intPage = PrefUtils.getInt(this, strKeyPage, 1);
        billList = dao.query(intPage);
        dateFormat = BillDateFormat.getInstance();
        today = System.currentTimeMillis();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        setContentView(R.layout.activity_bill_detail);
        /**
         * 三个View
         */
        HeadView hv = (HeadView) findViewById(R.id.hv_bill_detail);
        tvTitle = (TextView) findViewById(R.id.tv_bill_detail);
        lv = (ListView) findViewById(R.id.lv_bill_detail);

        /**
         * 设置View属性
         */
        tvTitle.setText(title);

        /**
         * 点击按钮返回
         */
        hv.setHeadButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new MyAdapter();
        lv.setAdapter(mAdapter);

        //列表监听
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(billList.get(position));

                return true;
            }
        });
    }

    private void showDeleteDialog(final Bill bill) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确认删除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(bill);
                billList.remove(bill);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 跟新数据
     */
    protected void updateData() {
        if (billList != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return billList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /**
             * 初始化界面
             */
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                View view = View.inflate(BaseDetailActivity.this, R.layout.view_list_bill_detail, null);
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_bill_detail_title);
                holder.tvMoney = (TextView) view.findViewById(R.id.tv_bill_detail_money);
                holder.tvDesc = (TextView) view.findViewById(R.id.tv_bill_detail_desc);
                convertView = view;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /**
             * 初始化数据
             */
            Bill bill = billList.get(position);
            holder.tvMoney.setText(String.valueOf(bill.getMoney()));
            holder.tvTitle.setText(bill.getClasses());
            //格式化 小时：分钟
            holder.tvDesc.setText(dateFormat.format(bill.getDate(), dateFormat.getPattern().SHOWTIME)+
                    bill.getRemark());
            return convertView;
        }
    }

    public static class ViewHolder {
        TextView tvTitle;
        TextView tvMoney;
        TextView tvDesc;
    }

}
