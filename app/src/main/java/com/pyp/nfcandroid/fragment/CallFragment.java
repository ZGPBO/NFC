package com.pyp.nfcandroid.fragment;

import android.content.Intent;
import android.net.Uri;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CallFragment extends BaseFragment {
    @Override
    public int loadView() {
        return R.layout.fragment_wifi;
    }

    @Override
    public void dealWithData(JSONObject Data) {
        //获得NFC标签的数据
        JSONObject jsonObject=Data;
        //电话号码
        String Tel_number=null;
        //进行解析封装在json数据的信息

        try {
            //T代表的是Telphone
            Tel_number= (String) jsonObject.get("T");
            //进行电话拨打的intent
            Uri uri = Uri.parse("tel:"+Tel_number);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
