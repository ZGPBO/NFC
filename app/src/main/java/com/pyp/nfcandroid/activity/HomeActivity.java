package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/13.
 */

public class HomeActivity extends BaseActivity {
    private Spinner sp_wifi,sp_bell;
    private Button writeBtn;
    private String[] items={"打开","关闭"};//sp_wifi绑定的数据源
    private String[] models={"正常","静音","震动"};  //sp_bell 也就是情景模式的三种选择
    private JSONObject jsonObject; //该jsonObject用来存储相对应的数据，并把该json数据存进NFC标签
    JSONArray array;


    //初始化控件
    @Override
    protected void initParameter() {
        super.initParameter();

        array=new JSONArray();
        jsonObject=new JSONObject();

        writeBtn= (Button) findViewById(R.id.writeBtn);
        sp_wifi= (Spinner) findViewById(R.id.sp_wifi);
        sp_bell=(Spinner) findViewById(R.id.sp_bell);
        ArrayAdapter<String> spinnerwifiAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        ArrayAdapter<String> spinnerbellAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,models);
        sp_wifi.setAdapter(spinnerwifiAdapter);
        sp_bell.setAdapter(spinnerbellAdapter);
    }

    //事件的处理
    @Override
    protected void initEvent() {
        super.initEvent();
//sp_wifi 这个Spinner控件用来控制是否打开WIFI状态
        sp_wifi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONObject json=new JSONObject();
                String name=items[position];
                //获得相对应的事件
                switch (name){
                    case "打开":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            json.put("act","WIFI");
                            json.put("sta","open");
                            array.put(json);
                            json=null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "关闭":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            json.put("act","WIFI");
                            json.put("sta","close");
                            array.put(json);
                            json=null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //选择手机情景模式的类型
        sp_bell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONObject json=new JSONObject();
                String name=models[position];
                switch (name){
                    case "正常":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            // 这里的nor代表是normal
                            json.put("act","BELL");
                            json.put("sta","nor");
                            array.put(json);
                            json=null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "静音":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            //这里的sil代表的是silient
                            json.put("act","BELL");
                            json.put("sta","sil");
                            array.put(json);
                            json=null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "震动":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            //这里的sh代表shake
                            json.put("act","BELL");
                            json.put("sta","sh");
                            array.put(json);
                            json=null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject.put("type","HOME");
                    jsonObject.put("data",array);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundle=new Bundle();
                bundle.putByteArray("data",jsonObject.toString().getBytes());

                //通过一个Bundle对象把json数据传到写入NFC标签的activity中
                Intent intent=new Intent(HomeActivity.this,InputNdefActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_write_home;
    }

    @Override
    protected String setActivityName() {
        return null;
    }
}
