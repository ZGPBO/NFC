package com.pyp.nfcandroid.util;

import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Bluetooth {

    private static BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

    public static Boolean OpenBluetooth(){
        if(bluetoothAdapter==null){
            return false;
        }else{
            if (bluetoothAdapter.disable()){
                bluetoothAdapter.enable();
                return true;
            }else{
                return true;
            }
        }
    }

    public static Boolean CloseBluetooth(){
        if(bluetoothAdapter==null){
            return false;
        }else{
            if(bluetoothAdapter.isEnabled()){
                bluetoothAdapter.disable();
                return true;
            }else{
                return true;
            }
        }
    }

}
