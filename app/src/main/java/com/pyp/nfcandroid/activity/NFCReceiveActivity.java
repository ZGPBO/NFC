package com.pyp.nfcandroid.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.pyp.nfcandroid.appconfig.NfcFilterConfig;

/**
 * Created by hasee on 2017/7/6.
 */


//具有NFC接收能力的Activity继承该类
public abstract class NFCReceiveActivity extends BaseActivity{

    //NFC适配器，用于检测该APP应用intent;
    private NfcAdapter mNfcAdapter;

    public NfcAdapter getNfcAdapter() {
        return mNfcAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNfcAdapter=NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //当该APP进入停止状态时，该mNfcAdapter将停止拦截NFC的intent,
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //当该APP进入启动状态时，该mNfcAdapter开始拦截intent,
        //当该intent符合NfcFilterConfig上的类型时，将启动HandlerActivity
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this,getPendingIntent() ,
                    NfcFilterConfig.FILTERS, NfcFilterConfig.TECHLISTS);
    }

    //得到前台调度(也叫延迟intent)，前台调度（当该方法触发时，会当收到一个intent时,将启动该intent）
    private PendingIntent getPendingIntent(){
        return PendingIntent.getActivity(this, 0, new Intent(this,
                NFCHandlerActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0 );
    }


}
