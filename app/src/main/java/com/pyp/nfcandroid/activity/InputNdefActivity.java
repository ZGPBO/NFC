package com.pyp.nfcandroid.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.pyp.nfcandroid.R;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/9.
 */

public class InputNdefActivity extends BaseActivity{

    private byte[] info;
    private IntentFilter[] mWriteTagFilters;
    private NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    String[][] mTechLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        info=bundle.getByteArray("data");


    }
    //初始化控件
    @Override
    protected void initParameter() {
        super.initParameter();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addCategory("*/*");
        mWriteTagFilters = new IntentFilter[] { ndef };
        mTechLists = new String[][] { new String[] { NfcA.class.getName() },
                new String[] { NfcF.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { NfcV.class.getName() } };
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_inputndef;
    }

    @Override
    protected String setActivityName() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                mWriteTagFilters, mTechLists);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(info==null){
            Toast.makeText(getApplicationContext(), "数据不能为空!",
                    Toast.LENGTH_SHORT).show();
            return;

        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                Tag tag =intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                        Ndef ndef = Ndef.get(tag);
                        try {
                            if(info.length>ndef.getMaxSize()){
                                Toast.makeText(InputNdefActivity.this,info.length+"超过最大长度了"+ndef.getMaxSize(),Toast.LENGTH_LONG).show();
                                return;
                            }
                            //数据的写入过程一定要有连接操作
                            try {
                                ndef.connect();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //构建数据包，也就是你要写入标签的数据
                            NdefRecord ndefRecord = new NdefRecord(
                                    //TNF_MIME_MEDIA 是NdefRecord中的其中一种格式，text/plain 是代表是文本信息
                                    NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),
                                    new byte[] {}, info);
                            NdefRecord[] records = { ndefRecord };
                            NdefMessage ndefMessage = new NdefMessage(records);
                            try {

                                //NdefMessage的写入
                                ndef.writeNdefMessage(ndefMessage);

                            } catch (FormatException e) {
                                e.printStackTrace();
                            }
                            System.out.println("3....");
                            Toast.makeText(getApplicationContext(), "数据写入成功!",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
        }

    }
}
