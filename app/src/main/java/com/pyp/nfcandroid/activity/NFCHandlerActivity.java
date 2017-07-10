package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;


import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.fragment.BaseFragment;
import com.pyp.nfcandroid.handle.BeforehandHandler;
import com.pyp.nfcandroid.listener.NDEFEndListener;

import org.json.JSONObject;

/**
 * Created by hasee on 2017/7/6.
 */

//该Activity是处理NDEF数据的地方（用于读操作）
    //当手机收到关于NFC发送端的信号，产生相对应的intent时由Activity是第一启动项
public class NFCHandlerActivity extends NFCReceiveActivity implements NDEFEndListener{

    //前景（存在加载界面）
    private LinearLayout mForeground;
    //管理Fragment
    private FragmentManager mFragmentManager;
    //处理NDEF数据对象
    private BeforehandHandler mNDEFHandler;

    private Handler handler=new Handler();

    @Override
    protected void initParameter() {
        super.initParameter();
        mForeground=(LinearLayout)findViewById(R.id.act_handler_Foreground);
        mFragmentManager=getSupportFragmentManager();
        mNDEFHandler=new BeforehandHandler(this,handler);
        onNewIntent(getIntent());
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_handler;
    }


    @Override
    protected String setActivityName() {
        return "NFCHandlerActivity";
    }


    //当新的intent传入时会触发改方法，系统不会重新运行oncreta方法,即该界面再一次收到NDEF数据时会触发
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        showForeground();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNDEFHandler.Analysis(intent);
            }
        }).start();
    }

    private void hideForeground(){
        mForeground.setVisibility(View.INVISIBLE);
    }

    private void showForeground(){
        mForeground.setVisibility(View.VISIBLE);
    }

    private void addFragment(Fragment fragment){
        if(null!=fragment){
            mFragmentManager.beginTransaction().replace(R.id.act_handler_container,fragment).commit();
        }
    }

    @Override
    public void End(JSONObject Data, int type) {
        if(null!=Data){
            Fragment fragment= BaseFragment.CreateFragment(Data,type);
            addFragment(fragment);
            hideForeground();
        }
    }
}
