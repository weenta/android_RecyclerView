package com.weenta.a21demorecyclerview;

import android.util.Log;

public class L {
    private static final String TAG = "rec_demo";
    private static final boolean IS_DEBUG = true;

    public static void i(String msg){
        if(IS_DEBUG) Log.i(TAG,msg);
    }

    public static void e(String msg) {
        if(IS_DEBUG) Log.e(TAG,msg);
    }
}
