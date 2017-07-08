package com.pyp.nfcandroid.handle;

import android.content.Intent;
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
import android.os.Message;

import org.json.JSONObject;

/**
 * Created by hasee on 2017/7/7.
 */


//在这个类中对NDEF数据进行预处理，从而分配到不同的处理中
public class BeforehandHandler {

    private int mType =-1;

    //对NDEF数据进行解析，产生不同的解析结果，结果用JSONObject存储,且于handler放回
    public Void Analysis(Intent intent, Handler handler){

        JSONObject Data=infTypeAnalysis(intent);
        Message message=new Message();
        if(null!=Data){
            message.what=mType;
            message.obj=Data;
        }
        handler.sendMessage(message);
        return null;
    }

    public JSONObject Analysis(Intent intent){
        return infTypeAnalysis(intent);
    }


    //对NDEF数据的inf类型进行判断
    //该APP  inf类型为｛
    // action.TECH_DISCOVERED(大多数发行卡类型，例如公交卡，校园卡，银行卡等)
    // action.TAG_DISCOVERED(自定义标签，用于读特殊标签)
    // action.NDEF_DISCOVERED(规范化标签，用于自己读写)
    // ｝
    private JSONObject infTypeAnalysis(Intent intent){
        String name=intent.getAction();
        JSONObject Date=null;
        switch (name){
            case NfcAdapter.ACTION_NDEF_DISCOVERED:
                break;
            case NfcAdapter.ACTION_TAG_DISCOVERED:
                break;
            case NfcAdapter.ACTION_TECH_DISCOVERED:
                Date=TechAnalysis(intent);
                break;
            default:
                Date=null;
        }

        return Date;

    }

    //对action.TECH_DISCOVERED类型的NDEF数据进行解析，
    //这种类型有九种协议
    //主流为3种协议{
    // iso 14443      ---->IsoDep
    // jis 6319-4     ---->NfcF
    // iso 15693      ---->NfcV
    // }
    private JSONObject TechAnalysis(Intent intent){

        Tag tag= intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //取卡的第一个协议为解析协议
        String TechName= tag.getTechList()[0];

//        setType(FragementType.TEXT_SINGLE);
//                JSONObject json=new JSONObject();
//                try {
//                    json.put("Data",tag.getTechList()[0]);
//                    return json;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

        switch (TechName){
            case "android.nfc.tech.IsoDep":
                IsoDep isodep = IsoDep.get(tag);
                break;
            case "android.nfc.tech.NfcV":
                NfcV nfcv = NfcV.get(tag);
                break;
            case "android.nfc.tech.NfcF":
                NfcF nfcf = NfcF.get(tag);
                break;
            case "android.nfc.tech.NfcA":
                NfcA nfcA=NfcA.get(tag);
                break;
            case "android.nfc.tech.NfcB":
                NfcB nfcB=NfcB.get(tag);
                break;
            case "android.nfc.tech.MifareClassic":
                MifareClassic classic=MifareClassic.get(tag);
                break;
            case "android.nfc.tech.MifareUltralight":
                MifareUltralight ultralight=MifareUltralight.get(tag);
                break;
            case "android.nfc.tech.Ndef":
                Ndef ndef_nfcf=Ndef.get(tag);
                break;
            case "android.nfc.tech.NdefFormatable":
                NdefFormatable formatable=NdefFormatable.get(tag);
                break;
        }


        return null;
    }


}
