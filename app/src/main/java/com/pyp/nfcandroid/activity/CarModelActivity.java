package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/11.
 */


public class CarModelActivity extends BaseActivity {

    private Spinner sp_bluetooth,sp_map;
    private Button writeBtn;
    private String[] items={"打开","关闭"};
    private JSONObject jsonObject;//该jsonObject用来存储相对应的数据，并把该json数据存进NFC标签
    JSONArray array;

    //控件初始化
    @Override
    protected void initParameter() {
        super.initParameter();
        array=new JSONArray();
        jsonObject=new JSONObject();

        writeBtn= (Button) findViewById(R.id.writeBtn);

        sp_bluetooth=(Spinner)findViewById(R.id.sp_bluetooth);
        sp_map=(Spinner)findViewById(R.id.sp_map);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        sp_bluetooth.setAdapter(spinnerAdapter);
        sp_map.setAdapter(spinnerAdapter);

    }

    //事件处理
    @Override
    protected void initEvent() {
        super.initEvent();

            //sp_bluetooth 这个Spinner控件用来控制是否打开蓝牙状态
        sp_bluetooth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONObject json=new JSONObject();
                //获得相对应的事件
                String name=items[position];
                switch (name){
                    //是否选择打开蓝牙
                    case "打开":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            json.put("act","BLUETOOTH");
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
                            json.put("act","BLUETOOTH");
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

        //sp_map选择是否开启地图功能

        sp_map.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONObject json=new JSONObject();
                String name=items[position];
                switch (name){
                    case "打开":
                        try {
                            //这里是 act代表的是action, sta代表的是status,由于NFC标签的容量不大，尽可能的简写
                            json.put("act","MAP");
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
                            json.put("act","MAP");
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


        //写入标签按钮
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject.put("type","CAR");
                    jsonObject.put("data",array);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundle=new Bundle();
                bundle.putByteArray("data",jsonObject.toString().getBytes());

                //通过一个Bundle对象把json数据传到写入NFC标签的activity中

                Intent intent=new Intent(CarModelActivity.this,InputNdefActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_write_carmodel;
    }

    @Override
    protected String setActivityName() {
        return null;
    }
}
