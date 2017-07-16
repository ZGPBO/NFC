package com.pyp.nfcandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.util.Bluetooth;
import com.pyp.nfcandroid.util.SceneMode;
import com.pyp.nfcandroid.util.WifiAutoConnectManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Office_Home_Fragment extends BaseFragment{
    WifiAutoConnectManager wifiBean; //一个管理WIFI的类
    AudioManager audio;
    SceneMode sceneModeBean; //一个管理手机情景模式的类

    //初始化控件
    @Override
    protected void initParameter() {
        super.initParameter();
        //获得AudioManager的管理对象
        audio = (AudioManager)getActivity().getApplication().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        //获得Wifi的管理对象
        WifiManager wifiManager = (WifiManager) getActivity().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //实例化WifiAutoConnectManager
        wifiBean = new WifiAutoConnectManager(wifiManager);
        //实例化SceneMode 手机情景模式类
        sceneModeBean=new SceneMode(audio);
    }

    @Override
    public int loadView() {
        return R.layout.fragment_wifi;
    }

    @Override
    public void dealWithData(JSONObject Data) {
        //获得NFC标签的数据
        JSONObject jsonObject=Data;
        try {
            //进行解析封装在json数据的信息
            JSONArray array=jsonObject.getJSONArray("data");
            JSONObject object;
            for(int i=0;i<array.length();i++){
                object =array.getJSONObject(i);
                //act代表的是action，因为写入标签容量小，获得动作的类型
                String type= (String) object.get("act");
                //sta代表的是status，因为写入标签容量小，获得动作的状态；
                String status= (String) object.get("sta");
                switch (type){
                    //WIFI
                    case "WIFI":
                        if(status.equals("open"))
                           wifiBean.openWifi();
                        else
                            wifiBean.closeWifi();
                        break;

                    //手机铃声模式也就是手机情景模式
                    case  "BELL":
                        if(status.equals("nor")) {
                            //手机情景模 正常模式
                            sceneModeBean.setNormal();
                        }else if(status.equals("sil")){
                            //手机情景模 静音模式
                            sceneModeBean.setSilence();
                        }else if(status.equals("sh")){
                            //手机情景模 振动模式
                            sceneModeBean.setShake();
                        }
                        break;
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
