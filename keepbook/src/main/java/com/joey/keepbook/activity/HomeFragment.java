package com.joey.keepbook.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.db.DbManager;
import com.joey.keepbook.cache.HomeCache;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.ToastUtils;
import com.joey.keepbook.view.HomeBodyView;
import com.joey.keepbook.view.TotalAmountTextView;

/**
 * Created by Joey on 2016/3/3.
 */
public class HomeFragment extends Fragment {
    private View view;

    //view
    private TotalAmountTextView tavIn;
    private TotalAmountTextView tavOut;
    private TotalAmountTextView tavResidue;
    private TextView tvTitle;
    private HomeBodyView dvOneDayIn;
    private HomeBodyView dvOneDayOut;
    private HomeBodyView dvOneDayResidue;
    private HomeBodyView dvHelp;
    private Activity activity;
    //cache
    private HomeCache mCache;

    /**
     * get HomeFragment
     */
    public HomeFragment setCache(HomeCache cache) {
        this.mCache = cache;
        return this;
    }

    /**
     * on attach activity
     * get activity
     */
    @Override
    public void onAttach(Activity activity) {
        this.activity = activity;
        super.onAttach(activity);
    }

    /**
     * get view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //记一笔 速记
        Button btNote = (Button) view.findViewById(R.id.bt_home_book_note);
        Button btNoteQuick = (Button) view.findViewById(R.id.bt_home_book_note_quick);
        //标题 本月收入 支出 余额
        tvTitle = (TextView) view.findViewById(R.id.tv_home_title);
        tavIn = (TotalAmountTextView) view.findViewById(R.id.tav_total_in);
        tavOut = (TotalAmountTextView) view.findViewById(R.id.tav_total_out);
        tavResidue = (TotalAmountTextView) view.findViewById(R.id.tav_total_residue);
        //今日支出 今日收入 今日结存 天天帮助
        dvOneDayIn = (HomeBodyView) view.findViewById(R.id.dv_one_day_in);
        dvOneDayOut = (HomeBodyView) view.findViewById(R.id.dv_one_day_out);
        dvOneDayResidue = (HomeBodyView) view.findViewById(R.id.dv_one_day_residue);
        dvHelp = (HomeBodyView) view.findViewById(R.id.dv_helper);

        tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputTitleDialog();
                return false;
            }
        });

        /**
         * 点击监听
         */
        //点击 记一笔
        btNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转 记账界面
                activity.startActivity(new Intent(activity, BookActivity.class));
            }
        });

        //点击 快记
        btNoteQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, OnCompleting.class));
            }
        });

        //点击 今日收入
        dvOneDayIn.setViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, TodayIn.class));
            }
        });

        //点击 今日支出
        dvOneDayOut.setViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, TodayOut.class));
            }
        });

        //设置 今日结存 不可点击
        dvOneDayResidue.setClickable(false);
        dvOneDayResidue.setFocusable(false);

        //点击 帮助
        dvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, OnCompleting.class));

            }
        });

        updateView(mCache);
        return view;
    }

    /**
     * 刷新 view
     */
    private void updateView(HomeCache cache) {
        //标题 本月收入 支出 余额
        tvTitle.setText(cache.getTitle());
        tavIn.setFirstText(cache.getStrThisMonthIn());
        tavOut.setFirstText(cache.getStrThisMonthOut());
        tavResidue.setFirstText(cache.getStrThisMonthResidue());
        //今日支出 今日收入 今日结存 天天帮助
        dvOneDayIn.setTvSecond(cache.getStrTodayIn());
        dvOneDayOut.setTvSecond(cache.getStrTodayOut());
        dvOneDayResidue.setTvSecond(cache.getStrTodayResidue());
        dvHelp.setTvSecond(cache.getHelp());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void inputTitleDialog() {
        final EditText inputServer = new EditText(activity);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("修改标题").setIcon(
                R.mipmap.ic_launcher).setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = inputServer.getText().toString();
                        LogUtils.e("home fragment 标题被修改为=" + text);
                        if (text.equals("")) {
                            ToastUtils.makeTextShort(activity, "不能为空");
                        } else {
                            PageDao dao = DbManager.getInstance().getPageDao(activity);
                            dao.replace(new Page(mCache.getPage(), text));
                            updateView(mCache);
                        }
                    }
                });
        builder.show();
    }

}
