package com.joey.keepbook.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.joey.keepbook.AppConfig;
import com.joey.keepbook.R;
import com.joey.keepbook.bean.Bill;
import com.joey.keepbook.db.DbManager;
import com.joey.keepbook.db.dao.BillDao;
import com.joey.keepbook.listener.IReceiveResult;
import com.joey.keepbook.utils.InputMethodUtils;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;
import com.joey.keepbook.view.CalcView;

/**
 * book fragment
 */
public abstract class BookFragment extends Fragment implements IReceiveResult{
    //常量
    private static final String TAG = "调试BookFragment";
    private int mIntPage;
    private int mIntState;
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
    private String remark = "";
    private String moneyStr = "0.00";

    private TextView tvClasses;
    private TextView tvMoney;
    private TextView etRemark;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mIntPage=PrefUtils.getInt(activity,AppConfig.KEY_PAGE,0);
        LogUtils.e("启动记一笔，，当前page="+mIntPage);
        mIntState=PrefUtils.getInt(activity,AppConfig.KEY_BOOK_STATE,AppConfig.BOOK_OUT_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_book, container, false);
        final Activity activity = getActivity();
        //自定义虚拟键盘 确定 删除 数字
        final CalcView cv = (CalcView) activity.findViewById(R.id.cv_activity_book);
        //button 金额 类别 保存 取消
        Button btMoney = (Button) view.findViewById(R.id.bt_fg_book_money);
        Button btClasses = (Button) view.findViewById(R.id.bt_fg_book_classes);
        Button btOk = (Button) view.findViewById(R.id.bt_fg_book_ok);
        Button  btCancel = (Button) view.findViewById(R.id.bt_fg_book_cancel);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_cancel_ok);
        //text 类别 备注 金额
        tvClasses = (TextView) view.findViewById(R.id.tv_fg_book_classes);
        etRemark = (EditText) view.findViewById(R.id.et_fg_book_remark);
        tvMoney = (TextView) view.findViewById(R.id.tv_fg_book_money);

        //点击 空白 隐藏 虚拟 键盘
        rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
            }
        });

        //点击 金额 弹出 自定义 虚拟键盘
        btMoney.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏系统键盘
                InputMethodUtils.hide(view);
                //显示虚拟键盘
                cv.setVisibility(View.VISIBLE);
            }
        });

        //点击 类别 跳转 到 类别 列表界面
        btClasses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ClassesListActivity.class);
                intent.putExtra(AppConfig.KEY_REQUEST_CODE, mIntState);
                activity.startActivityForResult(intent, mIntState);
                LogUtils.e(TAG, "类别.OnClick()");
            }
        });
        //点击 虚拟键盘 数字
        cv.setInputOnClickListener(new CalcView.CalcListener() {
            @Override
            public void inputNumberChange(String inputNum) {
                LogUtils.e(TAG, "inputNumberChange: " + inputNum);
                inputANum(inputNum);
            }
        });

        //点击 虚拟键盘 删除 删除一个数字
        cv.setDeleteOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteANum();
            }
        });

        //点击 虚拟键盘 确定 隐藏虚拟键盘
        cv.okOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
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
                BillDao billDao = DbManager.getInstance().getBillDao(activity);
                Bill bill = new Bill(date, money, remark, tvClasses.getText().toString(), mIntState, mIntPage);
                //打印
                billDao.insert(bill);
                activity.finish();
            }
        });
        //取消
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
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

    public TextView getTvMoney() {
        return tvMoney;
    }

    public int getState() {
        return mIntState;
    }

    public int getPage() {
        return mIntPage;
    }

    public TextView getTvClasses() {
        return tvClasses;
    }

    @Override
    public void onReceiveResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("book fragment 收到 classes 返回的列表 结果");
        if (requestCode ==mIntState) {
            if (resultCode == AppConfig.RESULT_OK) {
                String classes = data.getStringExtra(AppConfig.KEY_RESULT);
                if (mIntState==AppConfig.BOOK_OUT_FRAGMENT){
                    PrefUtils.putString(getActivity(),AppConfig.KEY_OUT_CLASSES+getPage(),classes);
                }else if(mIntState==AppConfig.BOOK_IN_FRAGMENT){
                    PrefUtils.putString(getActivity(),AppConfig.KEY_IN_CLASSES+getPage(),classes);
                }
                tvClasses.setText(classes);
            }
        }
    }
}
