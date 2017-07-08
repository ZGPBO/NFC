package com.pyp.nfcandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyp.nfcandroid.appconfig.FragementType;
import com.pyp.nfcandroid.util.L;

import org.json.JSONObject;

/**
 * Created by hasee on 2017/7/7.
 */


//HandlerActivity界面fragemnt的基类
public abstract class BaseFragment extends Fragment {

    public static final String BUNDLE_DATA="bundle_data";
    protected View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(loadView(),container,false);

        try {
            initParameter();
            initView();
            initEvent();
        }catch (Exception e){
            L.showTag(L.ERROR_FRAGMENT,"Fragment初始化错误");
        }

        try {
            //获得绑定的数据
            Bundle bundle=getArguments();
            JSONObject Data= new JSONObject((String)bundle.get(BUNDLE_DATA));
            if(null!=Data){
                dealWithData(Data);
            }

        }catch (Exception e){
            L.showTag(L.ERROR_FRAGMENT,"Fragment处理数据错误");
        }

        return v;

    }

    public abstract int loadView();

    //在这个方法中写处理数据的逻辑
    public abstract void dealWithData(JSONObject Data);

    protected void initParameter(){}
    protected void initView(){}
    protected void initEvent(){}


    //调用这个方法来创建Fragment实体类
    //type为Fragment的类型ID，在
    public static Fragment CreateFragment(JSONObject Data,int type){


        //取出对应的Fragment
        Fragment TargetFrag=FragementType.getFragment(type);

        //对Fragment绑定数据
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_DATA,Data.toString());
        TargetFrag.setArguments(bundle);
        return TargetFrag;
    }

}
