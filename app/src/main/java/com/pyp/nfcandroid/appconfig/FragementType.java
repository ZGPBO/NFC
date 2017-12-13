package com.pyp.nfcandroid.appconfig;

import android.support.v4.app.Fragment;

import com.pyp.nfcandroid.fragment.BedFragment;
import com.pyp.nfcandroid.fragment.CallFragment;
import com.pyp.nfcandroid.fragment.CarFragment;
import com.pyp.nfcandroid.fragment.MsgFragment;
import com.pyp.nfcandroid.fragment.Office_Home_Fragment;
import com.pyp.nfcandroid.fragment.PosterFragment;
import com.pyp.nfcandroid.fragment.ReceivedFileFragment;
import com.pyp.nfcandroid.fragment.TextFragment;
import com.pyp.nfcandroid.fragment.WifiFragment;

/**
 * Created by hasee on 2017/7/7.
 */

public class FragementType {

    public static final String TYPE_NAME="type_name";

    //单一纯文本Fragement
    public static final int TEXT_SINGLE=1;
    //Wifi的连接
    public static final int WIFI_CONNECT=2;

    public static final int POSTER_CONNECT=3;

    public static final int CAR_CONTENT=4;

    public static final int OFFICE_HOME_CONTENT=5;

    public static final int BED_CONTENT=6;

    public static final int MSG_CONTENT=7;

    public static final int CALL_CONTENT=8;

    public static final int FILE_FINSH=9;


    //根据对应的type返回对应fragment
    public static Fragment getFragment(int type){

        Fragment fragment=null;

        switch (type){
            case TEXT_SINGLE:
                fragment=new TextFragment();
                break;
            case WIFI_CONNECT:
                fragment=new WifiFragment();
                break;
            case POSTER_CONNECT:
                fragment=new PosterFragment();
                break;
            case CAR_CONTENT:
                fragment=new CarFragment();
                break;
            case OFFICE_HOME_CONTENT:
                fragment=new Office_Home_Fragment();
                break;
            case BED_CONTENT:
                fragment=new BedFragment();
                break;
            case MSG_CONTENT:
                fragment=new MsgFragment();
                break;
            case CALL_CONTENT:
                fragment=new CallFragment();
                break;
            case FILE_FINSH:
                fragment=new ReceivedFileFragment();
        }

        return fragment;
    }
}
