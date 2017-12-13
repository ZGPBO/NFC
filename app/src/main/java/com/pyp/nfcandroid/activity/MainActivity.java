package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.view.View;
import android.widget.Button;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.app.NFCApplication;
import com.pyp.nfcandroid.util.CheckNfcTool;

/**
 * Created by hasee on 2017/7/3.
 */

public class MainActivity extends NFCReceiveActivity {

    //NFC适配器，用于检测该APP应用intent;
    private NfcAdapter mNfcAdapter;
    private Button btn_InputNdef,btn_SendFile;
    private NFCApplication mNFCApplication;

    @Override
    protected void initParameter() {
        super.initParameter();
        mNFCApplication=(NFCApplication)getApplication();
        btn_InputNdef=(Button)findViewById(R.id.act_mian_input);
        btn_SendFile=(Button)findViewById(R.id.act_mian_send);
        mNfcAdapter=getNfcAdapter();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //打开写入文件的Activity
        btn_InputNdef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckNfcTool.checkNfc(mNfcAdapter,MainActivity.this);
                Intent intent=new Intent(MainActivity.this,NfcTagActivity.class);
                startActivity(intent);
            }
        });

        //打开发文件的Activity
        btn_SendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckNfcTool.checkBeam(mNfcAdapter,MainActivity.this);
                Intent intent=new Intent(MainActivity.this,SelectFileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected String setActivityName() {
        return "MainActivity";
    }


}
