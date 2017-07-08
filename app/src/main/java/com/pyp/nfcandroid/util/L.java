package com.pyp.nfcandroid.util;

import android.util.Log;

/**
 * Created by hasee on 2017/7/6.
 */

//用于显示Log信息
public class L {

    public static final String ERROR_ACTIVITY = "error_activity";
    public static final String ERROR_FRAGMENT = "error_fragment";
    public static final String TAG_NFC = "tag_nfc";



    public static void showTag(String Tag,String message){
        Log.d(Tag,message);
    }
}
