package com.pyp.nfcandroid.util;

import java.nio.ByteBuffer;

/**
 * Created by pyp on 2017/7/9.
 */

public class BtyeTo16bit {

    public static byte[] getSelectCommand(byte[] aid)
    {
        final ByteBuffer cmd_pse = ByteBuffer.allocate(aid.length + 6);
        cmd_pse.put((byte) 0x00) // CLA Class
                .put((byte) 0xA4) // INS Instruction
                .put((byte) 0x04) // P1 Parameter 1
                .put((byte) 0x00) // P2 Parameter 2
                .put((byte) aid.length) // Lc
                .put(aid).put((byte) 0x00); // Le
        return cmd_pse.array();
    }

    public static String HexToString(byte[] data) {
        String temp = "";
        for (byte d : data) {
            temp += String.format("%02x", d);
        }
        return temp;
    }

    public static boolean isSuccessfully(byte[] data){
        boolean flag=false;

        String result=HexToString(data);
        return "9000".equals(result.substring(result.length()-4,result.length()));
    }

    public static int toInt(byte[] b, int s, int n) {
        int ret = 0;

        final int e = s + n;
        for (int i = s; i < e; ++i) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }
}
