package com.pyp.nfcandroid.activity;

import android.nfc.NfcAdapter;

/**
 * Created by hasee on 2017/7/8.
 */

//该Activity主要为通过NFC的Beam功能对文件进行传输，文件类型可以为所有类型

public class NFCP2PActivity extends BaseActivity{

    private NfcAdapter mNfcAdapter=null;

    @Override
    protected void initParameter() {
        super.initParameter();
        mNfcAdapter=NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected int loadLayoutView() {
        return 0;
    }

    @Override
    protected String setActivityName() {
        return null;
    }

}
