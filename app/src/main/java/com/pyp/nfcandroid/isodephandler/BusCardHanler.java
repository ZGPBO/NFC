package com.pyp.nfcandroid.isodephandler;

import android.nfc.tech.IsoDep;

import com.pyp.nfcandroid.util.BtyeTo16bit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyp on 2017/7/9.
 */

public class BusCardHanler {

    //查询支付系统指令
    private final static byte[] DFN_PSE = {(byte) '1', (byte) 'P',
            (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y',
            (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F',
            (byte) '0', (byte) '1',};

    //查询余额指令
    private final static byte[] Balance = {(byte) 0x80, (byte) 0x5C, (byte) 0x00,
            (byte) 0x02, (byte) 0x04,};

    //羊城通ID
    private final static byte[] DFN_SRV_YCT = {(byte) 'P', (byte) 'A', (byte) 'Y',
            (byte) '.', (byte) 'A', (byte) 'P', (byte) 'P', (byte) 'Y',};

    private final static byte[] DFN_SRV_CAT= { (byte) 0xA0, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x86, (byte) 0x98,
            (byte) 0x07, (byte) 0x01, };
    private final static byte[] DFN_SRV_SZT= { (byte) 'P', (byte) 'A', (byte) 'Y',
            (byte) '.', (byte) 'S', (byte) 'Z', (byte) 'T' };
    private final static byte[] DFN_SRV_WHT = { (byte) 0x41, (byte) 0x50,
            (byte) 0x31, (byte) 0x2E, (byte) 0x57, (byte) 0x48, (byte) 0x43,
            (byte) 0x54, (byte) 0x43, };




    private static Map<String, byte[]> DFN_SRV = null;

    static {
        DFN_SRV = new HashMap<>();
        DFN_SRV.put("岭南通·羊城通", DFN_SRV_YCT);
        DFN_SRV.put("长安通", DFN_SRV_CAT);
        DFN_SRV.put("深圳通", DFN_SRV_SZT);
        DFN_SRV.put("武汉通", DFN_SRV_WHT);
    }


    private final static byte[] INFO = {(byte) 0x00, // CLA Class
            (byte) 0xB0, // INS Instruction
            (byte) 0x95, // P1 Parameter 1
            (byte) 0x00, // P2 Parameter 2
            (byte) 0x00, // Le
    };

    private final static byte[] DFN_SRV_S2 = {(byte) 'P', (byte) 'A',
            (byte) 'Y', (byte) '.', (byte) 'T', (byte) 'I', (byte) 'C',
            (byte) 'L',};

    private final static byte[] DFN_SRV_S1 = { (byte) 'P', (byte) 'A',
            (byte) 'Y', (byte) '.', (byte) 'P', (byte) 'A', (byte) 'S',
            (byte) 'D', };

    private static JSONArray jsonArray=null;



    public static JSONObject BusCardHanler(IsoDep isoDep) {
        try {
            JSONObject json = new JSONObject();
            jsonArray=new JSONArray();
            if (null != isoDep) {
                for (Map.Entry<String, byte[]> entry : DFN_SRV.entrySet()) {
                    isoDep.connect();
                    json.put("status","successful");
                    byte[] pse = isoDep.transceive(BtyeTo16bit.getSelectCommand(DFN_PSE));
                    byte[] srv = isoDep.transceive(BtyeTo16bit.getSelectCommand(entry.getValue()));
                    if (BtyeTo16bit.isSuccessfully(srv)) {
                        json.put("CardName", entry.getKey());
                        byte[] info = isoDep.transceive(INFO);
                        String CardId = String.format("%02X%02X%02X%02X%02X",
                                info[11], info[12], info[13], info[14], info[15]);
                        json.put("CardId", CardId);
                        String BornDate = String.format("%02X%02X.%02X.%02X",
                                info[23], info[24], info[25], info[26]);
                        json.put("BornDate", BornDate);
                        String Expire = String.format("%02X%02X.%02X.%02X",
                                info[27], info[28], info[29], info[30]);
                        json.put("Expire", Expire);
                        String Version = String.format("%02X.%02X", info[44], info[45]);
                        json.put("Version", Version);
                        isoDep.transceive(BtyeTo16bit.getSelectCommand(DFN_SRV_S1));
                        byte[] balance1 = isoDep.transceive(Balance);
                        int cash1 = BtyeTo16bit.toInt(balance1, 0, 4);
                        if(cash1!=0){
                            json.put("CardBalance", String.format("%4.2f", cash1 / 100.0));

                        }
                        ReadRecord(isoDep);
                        isoDep.transceive(BtyeTo16bit.getSelectCommand(DFN_SRV_S2));
                        byte[] balance2 = isoDep.transceive(Balance);
                        int cash2 = BtyeTo16bit.toInt(balance2, 0, 4);
                        if(cash2!=0){
                            json.put("CardBalance", String.format("%4.2f", cash1 / 100.0));

                        }
                        if(cash1==0&&cash2==0){
                            json.put("CardBalance", String.format("%4.2f", 0));
                        }
                        json.put("CardBalance", String.format("%4.2f", cash2 / 100.0));
                        ReadRecord(isoDep);
                        json.put("Record",jsonArray);
                        return json;
                    } else {
                        isoDep.close();
                    }
                }
                json.put("status","failed");
                return json;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ReadRecord(IsoDep isoDep) throws IOException, JSONException {
        for(int i=1;i<=10;i++){
            JSONObject jsonObject=new JSONObject();
            byte[] record = {(byte) 0x00, (byte) 0xB2, (byte) i,
                    (byte) 0xC4, (byte) 0x00,};
            byte[] RECORD=isoDep.transceive(record);
            int recordCash = BtyeTo16bit.toInt(RECORD, 5, 4);
            if(recordCash!=0){
                jsonObject.put("RecordCash",String.format("%4.2f", recordCash / 100.0));
                String code = String.format("%02X%02X",
                        RECORD[16], RECORD[17]);
                jsonObject.put("Code",code);
                String date=String.format("%02X.%02X %02X:%02X:%02X",RECORD[18], RECORD[19], RECORD[20], RECORD[21],
                        RECORD[22], RECORD[23]);
                jsonObject.put("Date",date);
                jsonArray.put(jsonObject);
            }

        }
    }


}
