package com.joey.keepbook.utils;

import android.util.Log;

/**
 * Created by joey on 2016/4/11.
 */
public class TraversalUtils {
    public static void array(String arrayName,float[]floats){
        String strArray=arrayName+" =[";
        if (floats!=null){
            for (int i=0;i<floats.length;i++){
                strArray=strArray+floats[i]+",";
            }
            strArray=strArray+"]";
        }
        Log.e("调试",strArray);
    }

    public static void array(String arrayName,String[]strings){
        String strArray=arrayName+" =[";
        if (strings!=null){
            for (int i=0;i<strings.length;i++){
                strArray=strArray+strings[i]+",";
            }
            strArray=strArray+"]";
        }
        Log.e("调试",strArray);
    }
}
