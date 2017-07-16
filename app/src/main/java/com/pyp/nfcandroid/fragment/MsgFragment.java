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

public class MsgFragment extends BaseFragment {
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
        //短信内容
        String Content=null;
        //进行解析封装在json数据的信息
        try {
            //T代表的是Telphone
            Tel_number= (String) jsonObject.get("T");
            //C代表的是Content
            Content=(String)jsonObject.get("C");

            //进行短信发送的intent
            Uri uri = Uri.parse("smsto:"+Tel_number);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", ""+Content);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
