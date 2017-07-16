package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/13.
 */

public class MsgActivity extends BaseActivity {
    private Button writeBtn;
    private EditText tel_number,content;
    private JSONObject jsonObject;  //该jsonObject用来存储相对应的数据，并把该json数据存进NFC标签


    //控件初始化
    @Override
    protected void initParameter() {
        super.initParameter();
        writeBtn= (Button) findViewById(R.id.writeBtn);
        tel_number= (EditText) findViewById(R.id.tel_number);
        content= (EditText) findViewById(R.id.content);
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
                    //这里是 act代表的是action, T代表的是"Telphone",C代表的是信息内容,由于NFC标签的容量不大，尽可能的简写
                    jsonObject.put("act","Msg");
                    jsonObject.put("T",tel_number.getText().toString());
                    jsonObject.put("C",content.getText().toString());

                    jsonObject.put("type","Msg");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundle=new Bundle();
                bundle.putByteArray("data",jsonObject.toString().getBytes());
                //通过一个Bundle对象把json数据传到写入NFC标签的activity中
                Intent intent=new Intent(MsgActivity.this,InputNdefActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_write_msg;
    }

    @Override
    protected String setActivityName() {
        return null;
    }
}
