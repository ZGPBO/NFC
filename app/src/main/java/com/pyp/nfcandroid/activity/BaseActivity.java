package com.pyp.nfcandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.pyp.nfcandroid.util.L;

/**
 * Created by hasee on 2017/7/3.
 */

//创建基础的Activity类，在该继类中实现一些公共的功能
public abstract class BaseActivity extends AppCompatActivity {

    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(loadLayoutView());
        mName=setActivityName();

        try {
            initParameter();
            initView();
            initEvent();
        }catch (Exception e){
            L.showTag(L.ERROR_ACTIVITY,getActivityName()+"初始化错误");
        }
    }

    //加载布局
    protected abstract int loadLayoutView();

    //对Activity起名字
    protected abstract String setActivityName();

    //返回Activity的名字
    public String getActivityName(){
        return mName;
    }


    protected void initParameter(){}
    protected void initView(){}
    protected void initEvent(){}

}
