package com.pyp.nfcandroid.fragment;

import android.content.Context;
import android.media.AudioManager;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.util.SceneMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/13.
 */

public class BedFragment extends BaseFragment {

    AudioManager audio;
    SceneMode sceneModeBean;  //一个管理情景模式的类

    //初始化控件
    @Override
    protected void initParameter() {
        super.initParameter();
        audio = (AudioManager)getActivity().getApplication().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        sceneModeBean=new SceneMode(audio);
    }
    //加载布局文件
    @Override
    public int loadView() {
        return R.layout.fragment_wifi;
    }


    //事件处理
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
                    case "CLOCK":

                        break;
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
