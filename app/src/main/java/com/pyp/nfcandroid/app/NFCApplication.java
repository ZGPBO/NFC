package com.pyp.nfcandroid.app;

import android.app.Application;
import android.nfc.NfcAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by hasee on 2017/7/8.
 */

public class NFCApplication extends Application {

    private NfcAdapter mNfcAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        mNfcAdapter=NfcAdapter.getDefaultAdapter(this);
        initImageLoader();

    }

    public NfcAdapter getNfcAdapter() {
        return mNfcAdapter;
    }

    private void initImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();

        ImageLoader.getInstance().init(config);
    }





}
