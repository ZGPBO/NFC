package com.pyp.nfcandroid.util;

import android.media.AudioManager;

/**
 * Created by Administrator on 2017/7/13.
 */

public class SceneMode {
    public static AudioManager audioManager;
    public SceneMode(AudioManager audio){
        this.audioManager=audio;
    }

    //其中 ringerMode有三种情况，分别是：AudioManager.RINGER_MODE_NORMAL（铃音模式）、AudioManager.RINGER_MODE_SILENT（静音模式）、AudioManager.RINGER_MODE_VIBRATE（震动模式）

    //手机铃音模式
    public static void setNormal(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


    }
    //手机静音模式

    public static void setSilence(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    //手机震动模式
    public static void setShake(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

}
