package com.joey.keepbook.manager;

/**
 * Created by Joey on 2016/3/10.
 */
public class MyActivityManager {
    private static MyActivityManager activityData;
    private MyActivityManager() {
    }
    public static MyActivityManager getInstance() {
        if (activityData ==null){
            activityData =new MyActivityManager();
        }
        return activityData;
    }

    //fragment
    public final int BOOKFRAGMENT = 100;
    public final int BOOKOUTFRAGMENT = 0;
    public final int BOOKINFRAGMENT = 1;
    //activity
    public final int CLASSESLISTACTIVITY = 11;

    //intent
    public final String KEY_RESULT = "result";//String
    public final String KEY_REQUEST_CODE = "request_code";//String

    //result
    public final int RESULT_OK = 1;
    public final int RESULT_ERROR = 2;
    public final int RESULT_NULL = 3;
    /**
     * key
     * classes 列表key=key+page
     */
    public final  String KEY_BOOK_STATE = "cur_book_state";// int
    public final  String KEY_OUT_CLASSES = "cur_out_classes";//String
    public final  String KEY_IN_CLASSES = "cur_in_classes";//String
    public final  String KEY_PAGE = "cur_page";//int




}
