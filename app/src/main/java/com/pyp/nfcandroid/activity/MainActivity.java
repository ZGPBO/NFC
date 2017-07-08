package com.pyp.nfcandroid.activity;

import android.nfc.NfcAdapter;

import com.pyp.nfcandroid.R;

/**
 * Created by hasee on 2017/7/3.
 */

public class MainActivity extends NFCReceiveActivity {

    //NFC适配器，用于检测该APP应用intent;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void initParameter() {
        super.initParameter();
        mNfcAdapter=getNfcAdapter();
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
