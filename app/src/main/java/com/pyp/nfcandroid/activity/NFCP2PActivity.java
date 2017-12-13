package com.pyp.nfcandroid.activity;

import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.bean.FileData;

import java.util.ArrayList;

/**
 * Created by hasee on 2017/7/8.
 */

//该Activity主要为通过NFC的Beam功能对文件进行传输，文件类型可以为所有类型

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class NFCP2PActivity extends BaseActivity implements NfcAdapter.CreateBeamUrisCallback,NfcAdapter.OnNdefPushCompleteCallback{

    private NfcAdapter mNfcAdapter;
    private ArrayList<FileData> Datas;


    @Override
    protected void initParameter() {
        super.initParameter();
        mNfcAdapter= NfcAdapter.getDefaultAdapter(this);
//        CheckNfcTool.checkBeam(mNfcAdapter,this);
//
//        //对该Activity进行注册，NFC发送端就发射磁场，等待NFC接收端的接收，当NFC接收端进入磁场时，
//        //就会触发createNdefMessage方法
        mNfcAdapter.setBeamPushUrisCallback(this,this);
        mNfcAdapter.setOnNdefPushCompleteCallback(this,this);
        Datas=getData();
        ((TextView)findViewById(R.id.frag_text_content)).setText(Uri.parse(Datas.get(0).getUrl()).toString());
    }

    private ArrayList<FileData> getData(){
        return getIntent().getParcelableArrayListExtra("Data");
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_send_file;
    }

    @Override
    protected String setActivityName() {
        return null;
    }

    @Override
    public Uri[] createBeamUris(NfcEvent nfcEvent) {


        if(null!=Datas){
            Uri[] uri=new Uri[Datas.size()];
            for(int i=0;i<Datas.size();i++){
                uri[i]=Uri.parse(Datas.get(i).getUrl());
            }

            return uri;
        }

        return new Uri[0];
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
    }


//    //当NFC接收端进入磁场时，触发该方法
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
//        return null;
//    }


}
