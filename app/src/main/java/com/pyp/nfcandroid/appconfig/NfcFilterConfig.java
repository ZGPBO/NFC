package com.pyp.nfcandroid.appconfig;

import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;

/**
 * Created by hasee on 2017/7/6.
 */

public class NfcFilterConfig {

    //NFC的intent的名字过滤
    public static String[][] TECHLISTS;
    //NFC的intent的数据过滤
    public static IntentFilter[] FILTERS;

    static {
        try {
            TECHLISTS = new String[][] { { IsoDep.class.getName() },
                    { NfcV.class.getName() }, { NfcF.class.getName() },{ NfcA.class.getName() },
                    { NfcB.class.getName() },{ Ndef.class.getName() },{ NdefFormatable.class.getName() },
                    { MifareClassic.class.getName() },{ MifareUltralight.class.getName() }};

            FILTERS = new IntentFilter[] { new IntentFilter(
                    NfcAdapter.ACTION_TECH_DISCOVERED, "*/*"), };
        } catch (Exception e) {
        }
    }

}
