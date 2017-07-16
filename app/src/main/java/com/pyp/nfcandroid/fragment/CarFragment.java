package com.pyp.nfcandroid.fragment;

import android.content.Intent;
import android.net.Uri;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.util.Bluetooth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CarFragment extends BaseFragment {
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
                    case "BLUETOOTH":

                        if(status.equals("open"))
                            //蓝牙状态打开
                            new Bluetooth().OpenBluetooth();
                        else
                            //蓝牙状态关闭
                            new Bluetooth().CloseBluetooth();
                        break;
                    case  "MAP":
                        //打开地图的intent对象

                        if(status.equals("open")) {
                            Uri uri = Uri.parse("geo:38.899533,-77.036476");
                            Intent it = new Intent(Intent.ACTION_VIEW,uri);
                            startActivity(it);
                        }
                        break;
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
