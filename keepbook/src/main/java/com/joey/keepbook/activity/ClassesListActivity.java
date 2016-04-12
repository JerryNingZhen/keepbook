package com.joey.keepbook.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.joey.keepbook.AppConfig;
import com.joey.keepbook.App;
import com.joey.keepbook.R;
import com.joey.keepbook.activity.base.BaseActivity;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.utils.ToastUtils;
import com.joey.keepbook.view.HeadView;

import java.util.ArrayList;
import java.util.List;

public class ClassesListActivity extends BaseActivity {
    //常量
    private final String strTitleIn = "收入类别";
    private final String strTitleOut = "支出类别";
    //状态
    private int mIntState;
    private int mIntPage;

    //view控件
    private ListView lv;

    //ListView数据
    private List<Classes> classesList;
    private MyAdapter mAdapter;

    //数据库
    private String mStrClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("列别列表 ClassesListActivity onCreate()");
        setContentView(R.layout.activity_classes_list);
        TextView tvTitle = (TextView) findViewById(R.id.tv_classes_list_title);
        HeadView hv = (HeadView) findViewById(R.id.hv_activity_classes_list);
        lv = (ListView) findViewById(R.id.lv_activity_classes_list);
        Button btAddClasses = (Button) findViewById(R.id.bt_add_classes);

        //获取状态 state page
        mIntState = PrefUtils.getInt(this, AppConfig.KEY_BOOK_STATE, AppConfig.BOOK_OUT_FRAGMENT);
        mIntPage = PrefUtils.getInt(this, AppConfig.KEY_PAGE, 0);

        //设置标题
        if (mIntState == AppConfig.BOOK_IN_FRAGMENT) {
            tvTitle.setText(strTitleIn);
        } else if (mIntState == AppConfig.BOOK_OUT_FRAGMENT) {
            tvTitle.setText(strTitleOut);
        }

        //点击 返回 按钮
        hv.setHeadButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击 类别 列表
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStrClasses = classesList.get(position).getClasses();
                setResult();
                finish();
            }
        });
        //长按 类别 列表
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(classesList.get(position));
                return true;
            }
        });
        //点击 添加 按钮
        btAddClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddClassesDialog();
            }
        });

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<Classes> classes = ClassesDao.getInstance(this).query(mIntPage);
        classesList = new ArrayList<Classes>();
        for (Classes c : classes) {
            if (c.getClassify() == mIntState) {
                classesList.add(c);
            }
        }
        //设置ListView适配
        mAdapter = new MyAdapter();
        lv.setAdapter(mAdapter);
    }


    //删除对话框
    private void showDeleteDialog(final Classes deleteClasses) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确认删除类别 " + deleteClasses);
        //确认
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                classesList.remove(deleteClasses);
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
     * 类别类别适配器
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return classesList.size();
        }

        @Override
        public Object getItem(int position) {
            return classesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ClassesListActivity.this, R.layout.view_list_classes, null);
                holder.bt = (Button) convertView.findViewById(R.id.bt_view_list_classes);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = classesList.get(position).getClasses();
            holder.bt.setText(s);
            return convertView;
        }
    }

    //ListView适配器优化
    private static class ViewHolder {
        Button bt;
    }

    /**
     * 显示对话框
     */
    private void showAddClassesDialog() {
        final View view = getLayoutInflater().inflate(R.layout.view_dialog_classes, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText etDialog = (EditText) view.findViewById(R.id.et_dialog_classes);
        Button btOk = (Button) view.findViewById(R.id.bt_dialog_classes_ok);
        Button btCancel = (Button) view.findViewById(R.id.bt_dialog_classes_cancel);
        /**
         * 取消按钮
         */
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        /**
         * 确定按钮
         */
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etDialog.getText().toString().trim();
                Classes tempClasses = new Classes(text, mIntState, mIntPage);
                if (text.equals("") || text == null) {
                    ToastUtils.makeTextShort(dialog.getContext(), "输入的类别不能为空");
                    return;
                }
                if (classesList.contains(etDialog)) {
                    ToastUtils.makeTextShort(dialog.getContext(), "输入的类别已经存在");
                    return;
                }
                ClassesDao.getInstance(App.getContextObject()).insert(tempClasses);
                classesList.add(tempClasses);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public void setResult() {
        Intent resultIntent = new Intent(this, BookActivity.class);
        if (mStrClasses == null && mStrClasses == "") {
            resultIntent.putExtra(AppConfig.KEY_RESULT, "");
            setResult(AppConfig.RESULT_NULL, resultIntent);
        } else {
            resultIntent.putExtra(AppConfig.KEY_RESULT, mStrClasses);
            setResult(AppConfig.RESULT_OK, resultIntent);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        LogUtils.e("类别列表 被销毁  onDestroy");
    }
}
