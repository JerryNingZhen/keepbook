package com.joey.keepbook.activity;

import android.app.Activity;
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
import com.joey.keepbook.R;
import com.joey.keepbook.base.BaseActivity;
import com.joey.keepbook.bean.Classes;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.utils.ToastUtils;
import com.joey.keepbook.view.HeadView;

import java.util.List;

public class ClassesListActivity extends BaseActivity {
    //常量
    private static final String TAG = "调试ClassesListActivity";
    private final String strDefTitleIn = "收入类别";
    private final String strDefTitleOut = "收入类别";

    //状态
    private int mIntState;
    private int mIntPage;

    //view控件
    private HeadView hv;
    private ListView lv;
    private TextView tvTitle;
    private Button btAddClasses;

    //view数据
    private String mStrTitle;


    //ListView数据
    private List<Classes> classesList;
    private MyAdapter mAdapter;

    //数据库
    private String mStrClasses;
    private ClassesDao mClassesDao;
    private String mStrKeyResult;
    private int mIntResult_ok;
    private int mIntResult_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(TAG, "onCreate()");
        initFinalData();
        initUI();
        initListener();
    }

    /**
     * 初始化最终数据
     */
    private void initFinalData() {
        mClassesDao = ClassesDao.getInstance(this);
        MyActivityManager activityManager = MyActivityManager.getInstance();
        //状态列表
        int mIntStateIn = activityManager.BOOKINFRAGMENT;
        int mIntStateOut = activityManager.BOOKOUTFRAGMENT;
        //key
        String strKeyState = activityManager.KEY_BOOK_STATE;
        String strKeyPage = activityManager.KEY_PAGE;
        mStrKeyResult = activityManager.KEY_RESULT;
        //状态
        mIntState = PrefUtils.getInt(this, strKeyState, mIntStateOut);
        mIntPage = PrefUtils.getInt(this, strKeyPage, 1);
        //result value
        mIntResult_ok = activityManager.RESULT_OK;
        mIntResult_null = activityManager.RESULT_NULL;

        //view title text
        if (mIntState == mIntStateIn) {
            mStrTitle = strDefTitleIn;
        } else if (mIntState == mIntStateOut) {
            mStrTitle = strDefTitleOut;
        }

        //view classes text
        classesList = mClassesDao.query(mIntState, mIntPage);
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        setContentView(R.layout.activity_classes_list);
        tvTitle = (TextView) findViewById(R.id.tv_classes_list_title);
        hv = (HeadView) findViewById(R.id.hv_activity_classes_list);
        lv = (ListView) findViewById(R.id.lv_activity_classes_list);
        btAddClasses = (Button) findViewById(R.id.bt_add_classes);

        //设置ui数据
        tvTitle.setText(mStrTitle);

        //设置ListView适配
        mAdapter = new MyAdapter();
        lv.setAdapter(mAdapter);


    }

    private void initListener() {
        //返回按钮点击监听
        hv.setHeadButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //类别列表被点击
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStrClasses = classesList.get(position).getClasses();
                setResult();
                finish();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(classesList.get(position));
                return true;
            }
        });

        //添加类别按钮被点击
        btAddClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddClassesDialog();
            }
        });

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
                mClassesDao.delete(deleteClasses);
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
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.view_dialog_classes, null);
        dialog.setView(view);
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
                Classes tempClasses=new Classes(text,mIntState,mIntPage);
                if (text.equals("") || text == null) {
                    ToastUtils.makeTextShort(dialog.getContext(), "输入的类别不能为空");
                    return;
                }
                if (classesList.contains(etDialog)) {
                    ToastUtils.makeTextShort(dialog.getContext(), "输入的类别已经存在");
                    return;
                }
                mClassesDao.insert(tempClasses);
                classesList.add(tempClasses);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public void setResult() {
        Intent resultIntent = new Intent(this, BookActivity.class);
        if (mStrClasses == null && mStrClasses == "") {
            resultIntent.putExtra(mStrKeyResult, "");
            setResult(mIntResult_null, resultIntent);
        } else {
            resultIntent.putExtra(mStrKeyResult, mStrClasses);
            setResult(mIntResult_ok, resultIntent);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter=null;
        mClassesDao.close();
        LogUtils.e(TAG, "onDestroy");
    }
}
