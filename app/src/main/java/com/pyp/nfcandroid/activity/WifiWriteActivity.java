package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WifiWriteActivity extends BaseActivity {


    private EditText wifiName_edt,wifiPassword_edt;
    private Button writeBtn;
    private JSONObject jsonObject;//该jsonObject用来存储相对应的数据，并把该json数据存进NFC标签


    //控件初始化
    @Override
    protected void initParameter() {
        super.initParameter();

        wifiName_edt=(EditText)findViewById(R.id.wifiName_edt);
        wifiPassword_edt=(EditText)findViewById(R.id.wifiPassword_edt);
        writeBtn=(Button)findViewById(R.id.writeBtn);
        jsonObject=new JSONObject();
    }
    //事件处理
    @Override
    protected void initEvent() {
        super.initEvent();
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//这里是 act代表的是action,N代表的是"WIFI名字",P代表的是WIFI密码,由于NFC标签的容量不大，尽可能的简写
                    jsonObject.put("N",wifiName_edt.getText().toString());
                    jsonObject.put("P",wifiPassword_edt.getText().toString());
                    jsonObject.put("act","WIFI");
                    jsonObject.put("type","WIFI");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Bundle bundle=new Bundle();
                bundle.putByteArray("data",jsonObject.toString().getBytes());
                //通过一个Bundle对象把json数据传到写入NFC标签的activity中
                Intent intent=new Intent(WifiWriteActivity.this,InputNdefActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_write_wifi;
    }

    @Override
    protected String setActivityName() {
        return getClass().getName();
    }




}
