package com.joey.keepbook.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.ClassesListActivity;
import com.joey.keepbook.activity.HomeActivity;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.data.Data;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.db.dao.ClassesDao;
import com.joey.keepbook.listener.ActivityListener;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.CalcView;

/**
 * Created by Joey on 2016/3/1.
 */
public abstract class BookFragment extends Fragment implements ActivityListener {
    //常量
    private static final String TAG = "调试BookFragment";
    //状态
    protected int mIntState;
    protected int mIntPage;
    //状态列表
    protected int mIntStateOut;
    protected int mIntStateIn;
    //result
    protected int mIntResultOK;
    protected int mIntResultError;
    protected int mIntResultNull;
    //数据库
    protected Activity mActivity;
    protected ClassesDao mDao;
    //key
    protected String mStrKeyClassesIn;
    protected String mStrKeyClassesOut;
    protected String mStrKeyResult;


    /**
     * 虚拟键盘
     * integerDigit         整数位
     * decimalDigit         小数位
     * moneyStr             金额
     * isInputPoint         是否已经输入小数点
     * decimalDigitCount    小数位输入次数
     */
    private String integerDigit = "0";
    private String decimalDigit = "00";
    private boolean isInputPoint = false;
    private int decimalDigitCount = 0;
    /**
     * View控件
     */
    private View view;
    private CalcView cv;
    private Button btMoney;
    private Button btClasses;
    private Button btOk;
    private Button btCancel;
    private TextView tvMoney;
    private EditText etRemark;
    private TextView tvClasses;

    /**
     * View 属性 设置默认值
     */
    protected int mIntMoneyColor = Color.BLACK;
    ;
    protected String remark = "";
    protected String classes = "";
    protected String moneyStr = "0.00";
    private String mStrKeyRequestCode;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);
        findView();
        return view;
    }

    private void findView() {
        mActivity = getActivity();
        //自定义虚拟键盘
        cv = (CalcView) mActivity.findViewById(R.id.cv_activity_book);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_cancel_ok);
        /**
         * 自定义虚拟键盘  包含大量按钮
         * 点击确定   隐藏虚拟键盘
         * 点击删除   删除一位金额
         * 点击数字   增加一位金额
         */
        /**
         * button
         */
        //金额    点击弹出虚拟键盘    并关闭自带的键盘
        btMoney = (Button) view.findViewById(R.id.bt_fg_book_money);
        //类别    点击弹出类别列表界面  并等待返回的类名
        btClasses = (Button) view.findViewById(R.id.bt_fg_book_classes);
        //保存    点击保存数据
        btOk = (Button) view.findViewById(R.id.bt_fg_book_ok);
        //取消    点击finish()
        btCancel = (Button) view.findViewById(R.id.bt_fg_book_cancel);
        /**
         * text
         */
        //类别
        tvClasses = (TextView) view.findViewById(R.id.tv_fg_book_classes);
        //备注
        etRemark = (EditText) view.findViewById(R.id.et_fg_book_remark);
        //金额
        tvMoney = (TextView) view.findViewById(R.id.tv_fg_book_money);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initFinalData();
        initFg();
        initListener();
    }

    /**
     * 初始化数据
     */
    protected void initFinalData() {
        MyActivityManager mActivityManager=MyActivityManager.getInstance();
        mDao = ClassesDao.getInstance(mActivity);
        //key
        String keyState = mActivityManager.KEY_BOOK_STATE;
        String keyPage = mActivityManager.KEY_PAGE;

        mStrKeyClassesIn = mActivityManager.KEY_IN_CLASSES;
        mStrKeyClassesOut =  mActivityManager.KEY_OUT_CLASSES;
        mStrKeyResult = mActivityManager.KEY_RESULT;
        mStrKeyRequestCode = mActivityManager.KEY_REQUEST_CODE;
        //状态列表
        mIntStateOut = mActivityManager.BOOKOUTFRAGMENT;
        mIntStateIn = mActivityManager.BOOKINFRAGMENT;
        //状态
        mIntPage = PrefUtils.getInt(mActivity, keyPage, 1);
        mIntState = PrefUtils.getInt(mActivity, keyState, mIntStateOut);
        //result
        mIntResultOK = mActivityManager.RESULT_OK;
        mIntResultError = mActivityManager.RESULT_ERROR;
        mIntResultNull = mActivityManager.RESULT_NULL;
    }

    /**
     * 初始化Fg
     */
    protected void initFg() {
        tvMoney.setTextColor(mIntMoneyColor);
        tvMoney.setText(moneyStr);
        tvClasses.setText(classes);
    }


    protected void initListener() {
        /**
         * button   虚拟键盘点击监听
         */
        cv.setInputOnClickListener(new CalcView.CalcListener() {
            @Override
            public void inputNumberChange(String inputNum) {
                LogUtils.e(TAG, "inputNumberChange: " + inputNum);
                inputANum(inputNum);
            }
        });

        //删除
        cv.setDeleteOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteANum();
            }
        });

        //确认
        cv.okOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
            }
        });


        /**
         * button
         */
        //金额
        btMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 隐藏虚拟键盘
                 */
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                /**
                 * 显示自定义键盘
                 */
                cv.setVisibility(View.VISIBLE);
            }
        });
        //保存
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Bill 参数列表
                 * date     smallint
                 * money    float
                 * remark   varchar
                 * classes  varchar
                 * classify smallint
                 */
                long date = System.currentTimeMillis();
                float money = Float.valueOf(moneyStr);
                remark = etRemark.getText().toString();
                BillDao billDao = BillDao.getInstance(mActivity);
                Bill bill = new Bill(date, money, remark, classes, mIntState, mIntPage);
                //打印
                billDao.insert(bill);
                mActivity.finish();
            }
        });
        //取消
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
                mActivity.finish();
            }
        });

        //类别
        btClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ClassesListActivity.class);
                intent.putExtra(mStrKeyRequestCode, mIntState);
                mActivity.startActivityForResult(intent, mIntState);
                LogUtils.e(TAG, "类别.OnClick()");
            }
        });
    }

    /**
     * 删除一个数字
     */
    private void deleteANum() {
        //为输入过小数点
        if (!isInputPoint) {
            if (integerDigit.length() > 1) {
                integerDigit = integerDigit.substring(0, integerDigit.length() - 1);
            } else if (integerDigit.length() == 1) {
                integerDigit = "0";
            }
        } else {
            switch (decimalDigitCount) {
                case 0:
                    isInputPoint = false;
                    break;
                case 1:
                    decimalDigit = "00";
                    decimalDigitCount--;
                    break;
                case 2:
                    decimalDigit = String.valueOf(decimalDigit.charAt(0)) + "0";
                    decimalDigitCount--;
                    break;
                default:
                    break;
            }
        }
        //跟新金额
        updateMoney();
    }

    /**
     * 跟新显示金额
     */
    private void updateMoney() {
        //更新金额
        moneyStr = integerDigit + "." + decimalDigit;
        tvMoney.setText(moneyStr);
    }


    /**
     * 输入一个数字
     *
     * @param inputNum
     */
    private void inputANum(String inputNum) {
        //为输入过小数点
        if (!isInputPoint) {
            if (inputNum.equals(".")) {
                //第一次输入小数点跳转至小数位
                isInputPoint = true;
            } else if (integerDigit.equals("0") && !inputNum.equals("0")) {
                //整数位为0 且输入字符非0 非小数点
                integerDigit = inputNum;
            } else if (!integerDigit.toString().equals("0")) {
                //整数位不为0 且输入字符非小数点
                integerDigit = integerDigit + inputNum;
            }
        } else {
            //输入小数点后
            //表示小数位，设置最大为两位小数
            if (!inputNum.equals(".")) {
                switch (decimalDigitCount) {
                    case 0:
                        //第一次输入
                        decimalDigit = inputNum + "0";
                        decimalDigitCount++;
                        break;
                    case 1:
                        //第二次输入
                        decimalDigit = decimalDigit.charAt(0) + inputNum;
                        decimalDigitCount++;
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        }
        //更新金额
        updateMoney();
    }

    protected void setMoneyText(String text) {
        tvMoney.setText(text);
    }


}
