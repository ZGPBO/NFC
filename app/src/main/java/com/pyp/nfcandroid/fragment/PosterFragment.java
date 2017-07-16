package com.pyp.nfcandroid.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;
import android.widget.Toast;

import com.pyp.nfcandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/11.
 */

public class PosterFragment extends BaseFragment {
    @Override
    public int loadView() {
        return R.layout.fragment_poster;
    }

    @Override
    public void dealWithData(JSONObject Data) {

        JSONObject jsonObject=Data;
        String posterAddress=null;
        try {
            posterAddress= (String) jsonObject.get("posterAddress");
            //校验网址是否有效
            if (Patterns.WEB_URL.matcher(posterAddress).matches()) {
                //符合标准
                Intent intent=new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(posterAddress);
                intent.setData(content_url);
                startActivity(intent);
            } else{
                //不符合标准
                Toast.makeText(getActivity(),"网址格式不对",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
