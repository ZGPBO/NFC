package com.pyp.nfcandroid.isodephandler;

import android.nfc.tech.IsoDep;

import com.pyp.nfcandroid.util.BtyeTo16bit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyp on 2017/7/9.
 */

public class BusCardHanler {

    private final static byte[] DFN_PSE = { (byte) '1', (byte) 'P',
            (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y',
            (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F',
            (byte) '0', (byte) '1', };

    private final static byte[] Balance = { (byte) 0x80, (byte) 0x5C, (byte) 0x00,
            (byte) 0x02, (byte) 0x04, };

    //羊城通ID
    private final static byte[] DFN_SRV_YCT = { (byte) 'P', (byte) 'A', (byte) 'Y',
            (byte) '.', (byte) 'A', (byte) 'P', (byte) 'P', (byte) 'Y', };



    private static Map<String,byte[]> DFN_SRV=null;

    static {
        DFN_SRV=new HashMap<>();
        DFN_SRV.put("羊城通",DFN_SRV_YCT);
    }


    private final static byte[] INFO = { (byte) 0x00, // CLA Class
            (byte) 0xB0, // INS Instruction
            (byte) 0x95, // P1 Parameter 1
            (byte) 0x00, // P2 Parameter 2
            (byte) 0x00, // Le
    };

    private final static byte[] DFN_SRV_S2 = { (byte) 'P', (byte) 'A',
            (byte) 'Y', (byte) '.', (byte) 'T', (byte) 'I', (byte) 'C',
            (byte) 'L', };


//
//    line+="支付系统："+pse.length+"\n";
//    line+="支付系统："+HexToString(pse)+"\n";
//    byte[] srv=isoDep.transceive(getSelectCommand(DFN_SRV));
//    line+="羊城通："+srv.length+"\n";
//    line+="羊城通："+HexToString(srv)+"\n";

//    byte[] info=isoDep.transceive(INFO);
//    String ss=toHexString(info, 11, 5);
//    String version = String.format("%02X.%02X", info[44], info[45]);
//    String date = String.format("%02X%02X.%02X.%02X - %02X%02X.%02X.%02X", info[23],
//            info[24], info[25], info[26], info[27], info[28], info[29], info[30]);
//    line+="信息："+info.length+ss+"\n";
//    line+="信息："+version+"\n";
//    line+="信息："+date+"\n";
//    line+="信息："+HexToString(info)+"\n";
//    byte[] DFN_SRV_S1 = { (byte) 'P', (byte) 'A',
//            (byte) 'Y', (byte) '.', (byte) 'P', (byte) 'A', (byte) 'S',
//            (byte) 'D', };
//    byte[] DFN_SRV_S2 = { (byte) 'P', (byte) 'A',
//            (byte) 'Y', (byte) '.', (byte) 'T', (byte) 'I', (byte) 'C',
//            (byte) 'L', };
//    byte[] blance1=isoDep.transceive(getSelectCommand(DFN_SRV_S2));
//    line+="记录："+blance1.length+"\n";
//    line+="记录："+HexToString(blance1)+"\n";
//    byte[] balance=isoDep.transceive(Balance);
//    int cash1 = toInt(balance, 2, 2);
//    line+="余额："+balance.length+"\n";
//    line+="余额："+HexToString(balance)+cash1+"\n";
//                    for(int i=1;i<=10;i++){
//        byte[] cmd = { (byte) 0x00, // CLA Class
//                (byte) 0xB2, // INS Instruction
//                (byte) i, // P1 Parameter 1
//                (byte) 0xC4, // P2 Parameter 2
//                (byte) 0x00, // Le
//        };
//        byte[] record1=isoDep.transceive(cmd);
//        int cash = toInt(record1, 5, 4);
//        String s=String.format("%02X%02X.%02X.%02X %02X:%02X%02X",
//                record1[16], record1[17], record1[18], record1[19], record1[20], record1[21],
//                record1[22],record1[23]);
//        line+="记录"+i+"："+record1.length+"\n";
//        line+="记录"+i+"："+HexToString(record1)+"\n";
//        line+="记录"+i+"："+s+"消费"+cash+"\n";

    public static JSONObject BusCardHanler(IsoDep isoDep){
        try {
            JSONObject json=new JSONObject();

            if (null!=isoDep){

                for (Map.Entry<String, byte[]> entry : DFN_SRV.entrySet()) {
                    isoDep.connect();
                    byte[] pse=isoDep.transceive(BtyeTo16bit.getSelectCommand(DFN_PSE));
                    byte[] srv=isoDep.transceive(BtyeTo16bit.getSelectCommand(entry.getValue()));
                    if( BtyeTo16bit.isSuccessfully(srv)){
                        json.put("CardName",entry.getKey());
                        byte[] info=isoDep.transceive(INFO);
                        String CardId=String.format("%02X%02X%02X%02X%02X",
                                info[11], info[12], info[13], info[14], info[15]);
                        json.put("Card",CardId);
                        String BornDate=String.format("%02X%02X.%02X.%02X",
                                info[23], info[24], info[25], info[26]);
                        json.put("BornDate",BornDate);
                        String Expire=String.format("%02X%02X.%02X.%02X",
                                info[27], info[28], info[29], info[30]);
                        json.put("Expire",Expire);
                        isoDep.transceive(BtyeTo16bit.getSelectCommand(DFN_SRV_S2));
                        byte[] balance=isoDep.transceive(Balance);
                        int cash=BtyeTo16bit.toInt(balance, 2, 2);
                        json.put("CardBalance",String.format("%4.2f",cash/100.0));
                        break;
                    }else {
                        isoDep.close();
                    }
                }

                return json;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
