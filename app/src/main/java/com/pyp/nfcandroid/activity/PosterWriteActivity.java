package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pyp.nfcandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/10.
 */

public class PosterWriteActivity extends BaseActivity {

    private EditText posterAddress;
    private Button writeposterBtn;


    @Override
    protected void initParameter() {
        super.initParameter();
        posterAddress= (EditText) findViewById(R.id.posteraddress_edt);
        writeposterBtn= (Button) findViewById(R.id.writeposterBtn);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        writeposterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json=new JSONObject();
                try {
                    json.put("posterAddress",posterAddress.getText().toString());
                    json.put("action","POSTER");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundle=new Bundle();
                bundle.putByteArray("data",json.toString().getBytes());

                Intent intent=new Intent(PosterWriteActivity.this,InputNdefActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_write_poster;
    }

    @Override
    protected String setActivityName() {
        return null;
    }

}
