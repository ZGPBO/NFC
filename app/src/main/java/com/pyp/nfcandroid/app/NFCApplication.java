package com.pyp.nfcandroid.app;

import android.app.Application;
import android.nfc.NfcAdapter;

/**
 * Created by hasee on 2017/7/8.
 */

public class NFCApplication extends Application {

    private NfcAdapter mNfcAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        mNfcAdapter=NfcAdapter.getDefaultAdapter(this);
    }

    public NfcAdapter getNfcAdapter() {
        return mNfcAdapter;
    }



}
