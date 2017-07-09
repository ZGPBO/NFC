package com.pyp.nfcandroid.listener;

import org.json.JSONObject;

/**
 * Created by pyp on 2017/7/9.
 */

public interface NDEFEndListener {

    public abstract void End(JSONObject Data,int type);
}
