package com.joey.keepbook.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.BookActivity;
import com.joey.keepbook.activity.HelpActivity;
import com.joey.keepbook.activity.TodayIn;
import com.joey.keepbook.activity.TodayOut;
import com.joey.keepbook.base.BaseActivity;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.bean.HomeInitData;
import com.joey.keepbook.bean.Page;
import com.joey.keepbook.engine.BillData;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.db.dao.PageDao;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.HomeBodyView;
import com.joey.keepbook.view.TotalAmountTextView;

import java.util.List;

/**
 * Created by Joey on 2016/3/3.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "调试HomeFragment";
    private View view;
    private Activity activity;
    //text数据
    protected float todayIn;
    protected float todayOut;
    protected float todayResidue;
    protected float thisMonthIn;
    protected float thisMonthOut;
    protected float thisMonthResidue;

    //常量信息
    protected int mIntPage;
    protected String strTitle;
    private TotalAmountTextView tavIn;
    private TotalAmountTextView tavOut;
    private TotalAmountTextView tavResidue;
    private HomeBodyView dvOneDayOut;
    private HomeBodyView dvOneDayIn;
    private HomeBodyView dvOneDayResidue;
    private TextView tvTitle;

    /**
     * 构造
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        initUI();
    }
    private void initData() {
        activity = getActivity();
        MyActivityManager activityManager = MyActivityManager.getInstance();
        //key
        String key_page = activityManager.KEY_PAGE;
        //获取状态信息
        mIntPage = PrefUtils.getInt(activity, key_page, 1);

        BillData billData = BillData.getInstance(activity);
        BillDao dao = BillDao.getInstance(activity);
        List<Bill> billList = dao.query(mIntPage);
        if (billList != null) {
            HomeInitData homeInitData = billData.getHomeInitData(billList);
            todayIn = homeInitData.getTodayIn();
            todayOut = homeInitData.getTodayOut();
            todayResidue = homeInitData.getTodayResidue();
            thisMonthIn = homeInitData.getThisMonthIn();
            thisMonthOut = homeInitData.getThisMonthOut();
            thisMonthResidue = homeInitData.getThisMonthResidue();
        }
        //获取title
        List<Page> pageList = PageDao.getInstance(activity).query();
        for (Page page : pageList) {
            if (page.getPage() == mIntPage) {
                strTitle = page.getTitle();
                break;
            }
        }
        if (strTitle == null) {
            strTitle = "未命名";
        }
    }

    private void initUI() {
        tvTitle = (TextView) view.findViewById(R.id.tv_home_title);
        Button btNote = (Button) view.findViewById(R.id.bt_home_book_note);//记一笔
        Button btNoteQuick = (Button) view.findViewById(R.id.bt_home_book_note_quick);//快记
        tavIn = (TotalAmountTextView) view.findViewById(R.id.tav_total_in);
        tavOut = (TotalAmountTextView) view.findViewById(R.id.tav_total_out);
        tavResidue = (TotalAmountTextView) view.findViewById(R.id.tav_total_residue);
        /**
         * 四大控件，设置点击监听
         * 今日支出
         * 今日收入
         * 今日结存
         * 天天帮助
         */
        dvOneDayOut = (HomeBodyView) view.findViewById(R.id.dv_one_day_out);
        dvOneDayIn = (HomeBodyView) view.findViewById(R.id.dv_one_day_in);
        dvOneDayResidue = (HomeBodyView) view.findViewById(R.id.dv_one_day_residue);
        HomeBodyView dvHelp = (HomeBodyView) view.findViewById(R.id.dv_helper);


        //标题
        dvHelp.setTvSecond("作者：joey");
        //setText   月合计
        tavIn.setFirstText(String.valueOf(thisMonthIn));
        tavOut.setFirstText(String.valueOf(thisMonthOut));
        tavResidue.setFirstText(String.valueOf(thisMonthResidue));
        //setText   当日
        dvOneDayIn.setTvSecond(String.valueOf(todayIn));
        dvOneDayOut.setTvSecond(String.valueOf(todayOut));
        dvOneDayResidue.setTvSecond(String.valueOf(todayResidue));
        tvTitle.setText(strTitle);

        //按钮 记一笔 被点击
        btNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转 记账界面
                activity.startActivity(new Intent(activity, BookActivity.class));
            }
        });

        //按钮 快记 被点击
        btNoteQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //按钮今日支出 被点击
        dvOneDayOut.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, TodayOut.class));
            }
        });

        //按钮今日收入 被点击
        dvOneDayIn.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, TodayIn.class));

            }
        });

        //今日结存不可被点击
        dvOneDayResidue.setButtonClickable(false);
        dvOneDayResidue.setButtonFocusable(false);


        //按钮帮助 被点击
        dvHelp.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, HelpActivity.class));

            }
        });
    }

}
