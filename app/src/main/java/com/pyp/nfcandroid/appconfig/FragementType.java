package com.pyp.nfcandroid.appconfig;

import android.support.v4.app.Fragment;

import com.pyp.nfcandroid.fragment.TextFragment;

/**
 * Created by hasee on 2017/7/7.
 */

public class FragementType {

    public static final String TYPE_NAME="type_name";

    //单一纯文本Fragement
    public static final int TEXT_SINGLE=1;

    //根据对应的type返回对应fragment
    public static Fragment getFragment(int type){

        Fragment fragment=null;

        switch (type){
            case TEXT_SINGLE:
                fragment=new TextFragment();
                break;
            default:
                fragment=null;
        }

        return fragment;
    }
}
