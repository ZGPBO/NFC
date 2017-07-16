package com.pyp.nfcandroid.handle;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Toast;

import com.pyp.nfcandroid.appconfig.FragementType;

import com.pyp.nfcandroid.isodephandler.BusCardHanler;
import com.pyp.nfcandroid.listener.NDEFEndListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by hasee on 2017/7/7.
 */


//在这个类中对NDEF数据进行预处理，从而分配到不同的处理中
public class BeforehandHandler {

    private NDEFEndListener mlistener;
    private Handler mhandler;


    public BeforehandHandler(NDEFEndListener listener, Handler handler){
        mlistener=listener;
        mhandler=handler;
    }

    //对NDEF数据进行解析，产生不同的解析结果，结果用JSONObject存储
    public Void Analysis(Intent intent){
        infTypeAnalysis(intent);
        return null;
    }



    //对NDEF数据的inf类型进行判断
    //该APP  inf类型为｛
    // action.TECH_DISCOVERED(大多数发行卡类型，例如公交卡，校园卡，银行卡等)
    // action.TAG_DISCOVERED(自定义标签，用于读特殊标签)
    // action.NDEF_DISCOVERED(规范化标签，用于自己读写)
    // ｝
    private void infTypeAnalysis(Intent intent){
        String name=intent.getAction();
        switch (name){
            case NfcAdapter.ACTION_NDEF_DISCOVERED:

                break;
            case NfcAdapter.ACTION_TAG_DISCOVERED:
                break;
            case NfcAdapter.ACTION_TECH_DISCOVERED:
                techAnalysis(intent);
                break;
            default:
        }


    }

    //对action.TECH_DISCOVERED类型的NDEF数据进行解析，
    //这种类型有九种协议
    //主流为3种协议{
    // iso 14443      ---->IsoDep
    // jis 6319-4     ---->NfcF
    // iso 15693      ---->NfcV
    // }
    private void techAnalysis(Intent intent){

        Tag tag= intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if(null!=tag) {

            IsoDep isodep = IsoDep.get(tag);
            if (null != isodep) {
                isodepType(isodep);
            }

            NfcV nfcv = NfcV.get(tag);
            if (nfcv != null) {
            }

            NfcF nfcf = NfcF.get(tag);
            if (nfcf != null) {

            }

            NfcA nfcA = NfcA.get(tag);
            if (nfcA != null) {

            }

            NfcB nfcB = NfcB.get(tag);
            if (nfcB != null) {

            }

            MifareClassic classic = MifareClassic.get(tag);
            if (classic != null) {

            }

            Ndef ndef_nfcf = Ndef.get(tag);
            if (ndef_nfcf != null) {
                //读标签的内容
                JSONObject jsonObject2=readFromTag(intent);
                try {
                    //获取读取的类型
                    String action=(String)jsonObject2.get("type");
                    if(jsonObject2!=null){

                        switch (action){
                            case "CAR":
                                sendDate(jsonObject2,FragementType.CAR_CONTENT);
                                break;
                            case "OFFICE":
                                sendDate(jsonObject2,FragementType.OFFICE_HOME_CONTENT);
                                break;
                            case "HOME":
                                sendDate(jsonObject2,FragementType.OFFICE_HOME_CONTENT);
                                break;
                            case "BED":
                                sendDate(jsonObject2,FragementType.BED_CONTENT);
                                break;
                            case "WIFI":
                                sendDate(jsonObject2,FragementType.WIFI_CONNECT);
                                break;
                            case "Msg":
                                sendDate(jsonObject2,FragementType.MSG_CONTENT);
                                break;
                            case "Call":
                                sendDate(jsonObject2,FragementType.CALL_CONTENT);
                                break;

                        }
                        return ;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }



            NdefFormatable formatable = NdefFormatable.get(tag);
            if (formatable != null) {

            }
        }
    }

    //对iso 14443      ---->IsoDep  协议进行类型分析，分到不同的解析场景中{
    // 公交卡   -----》BusCardHanler
    // 银行卡   ----->>.......
    // }
    private void isodepType(IsoDep isodep){
        if(null!=isodep){
            sendDate(BusCardHanler.BusCardHanler(isodep),FragementType.TEXT_SINGLE);
        }
    }

    //向目标传递信息{
    // Data  为 解析的数据
    // type  为 用什么Fragment加载信息
    // }
    private void sendDate(final JSONObject Data, final int type){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(null!=Data){
                    mlistener.End(Data,type);
                }
            }
        });
    }

    /**
     * 读取NFC标签数据的操作
     * @param intent
     * @return
     */
    private JSONObject readFromTag(Intent intent) {
        String readResult;
        JSONObject jsonObject=null;
        Parcelable[] rawArray = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            try {
                if (mNdefRecord != null) {
                    readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                    jsonObject=new JSONObject(readResult);

                    return jsonObject;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
        return jsonObject;
    }

}


