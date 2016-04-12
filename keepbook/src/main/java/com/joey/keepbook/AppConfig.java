package com.joey.keepbook;

import android.content.Context;
import android.text.TextUtils;

/**
 * config
 */
public class AppConfig {
    public static final  String APP_CONFIG = "config";
    public static final  String CONF_COOKIE = "cookie";
    public static final String KEY_LAUNCH_COUNT = "launch_count";
    public static final String SYMBOL =":";
    public static final String KEY_CHART_CACHE ="chart_cache" ;
    public static final String KEY_CURRENT_PAGE = "key_current_page";

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    /**
     * key
     */
    //config
    public static final  String KEY_BOOK_STATE = "cur_book_state";// int
    public static final  String KEY_OUT_CLASSES = "cur_out_classes";//String
    public static final  String KEY_IN_CLASSES = "cur_in_classes";//String
    public static final  String KEY_PAGE = "cur_page";//int

    //ui info
    public static final  String KEY_HOME_CACHE = "home_fragment_bean";//string expression
    public static final  String UI_INFO_REGULAREXPRESSION = ":";//正则表达式

    //intent
    public static final String KEY_RESULT = "result";//String
    public static final String KEY_REQUEST_CODE = "request_code";//String
    //result
    public static final int RESULT_OK = 1;
    public static  final int RESULT_ERROR = 2;
    public static final int RESULT_NULL = 3;
    /**
     * value
     */
    //fragment
    public static final int BOOK_OUT_FRAGMENT = 0;
    public static final int BOOK_IN_FRAGMENT = 1;
    //activity

    //db
    public static final int DELETE = 0;
    public static final int INSERT = 1;
    public static final int DELETE_All = 2;
}
