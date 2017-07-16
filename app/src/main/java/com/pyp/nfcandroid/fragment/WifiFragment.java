package com.pyp.nfcandroid.fragment;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;

import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.app.NFCApplication;
import com.pyp.nfcandroid.util.WifiAutoConnectManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2017/7/9.
 */

public class WifiFragment extends BaseFragment {

    WifiAutoConnectManager wifiBean;    //一个管理WIFI的类

    private PendingIntent pendingIntent;
    private TextView wifiStaticText;


    //初始化控件
    @Override
    protected void initParameter() {

        super.initParameter();
        wifiStaticText= (TextView) v.findViewById(R.id.wifiStaticText);
        //获得Wifi的管理对象
        WifiManager wifiManager = (WifiManager) getActivity().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //实例化WifiAutoConnectManager
        wifiBean = new WifiAutoConnectManager(wifiManager);
        //将被调用的Intent，用于重复被Intent触发后将要执行的跳转
        pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getContext(),
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    //加载布局
    @Override
    public int loadView()
    {
        return R.layout.fragment_wifi;
    }


    //事件处理
    @Override
    public void dealWithData(JSONObject Data) {
        //把读取到的json数据进行解析
        JSONObject jsonObject=Data;

        String wifiName=null;
        String wifiPassword=null;

        try {
            //获取wifiName
            wifiName= (String) jsonObject.get("N");
            //获取wifi密码
            wifiPassword=(String)jsonObject.get("P");
            //wifi自动连接
            wifiBean.connect(wifiName,wifiPassword,wifiPassword.equals("")? WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS: WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
