package com.joey.keepbook.utils;

/**
 * Created by Joey on 2016/3/15.
 */
public class ActivityUtils {
    /**
     * Fragment
     */
    public static final int bookFragment = 100;
    public static final int bookOutFragment = 1001;
    public static final int bookInFragment = 1002;
    /**
     * Activity
     */
    public static final int classesListActivity = 11;
    /**
     * mPref
     */
    public static final String key_book_state = "book_fg_state";// int
    public static final String key_book_out_classes = "book_out_classes";//String
    public static final String key_book_in_classes = "book_in_classes";//String
    public static final String key_page = "page";//int

    //extra
    public static final String key_result = "result";//String
    public static final String key_request_code = "request_code";//String

    //result
    public static final int RESULT_OK = 1;
    public static final int RESULT_ERROR = 2;
    public static final int RESULT_NULL = 3;
}
