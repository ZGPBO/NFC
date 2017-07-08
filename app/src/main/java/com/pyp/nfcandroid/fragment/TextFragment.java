package com.pyp.nfcandroid.fragment;

import android.widget.TextView;

import com.pyp.nfcandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasee on 2017/7/7.
 */

//纯文本的Fragment
public class TextFragment extends BaseFragment{

    private TextView showView;

    @Override
    protected void initParameter() {
        super.initParameter();
        showView= (TextView)v.findViewById(R.id.frag_text_content);
    }

    @Override
    public int loadView() {
        return R.layout.fragment_text_single;
    }

    @Override
    public void dealWithData(JSONObject Data) {
        try {
            String data=Data.getString("Data");
            showView.setText(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
