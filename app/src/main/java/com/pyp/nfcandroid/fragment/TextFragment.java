package com.pyp.nfcandroid.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.adapter.RecordAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasee on 2017/7/7.
 */

//纯文本的Fragment
public class TextFragment extends BaseFragment{

    private TextView cardName_txt,cardBalance_txt,cardExpiry_txt,cardId_txt,cardVersion_txt,comsume_txt;
    private ListView cardRecord_list;
    private RecordAdapter recordAdapter;

    @Override
    protected void initParameter() {
        super.initParameter();
        cardName_txt= (TextView)v.findViewById(R.id.cardName_txt);
        cardBalance_txt= (TextView)v.findViewById(R.id.cardBalance_txt);
        cardExpiry_txt= (TextView)v.findViewById(R.id.cardExpiry_txt);
        cardId_txt= (TextView)v.findViewById(R.id.cardId_txt);
        cardVersion_txt= (TextView)v.findViewById(R.id.cardVersion_txt);
        cardName_txt= (TextView)v.findViewById(R.id.comsume_txt);
        cardRecord_list= (ListView) v.findViewById(R.id.cardRecord_list);
    }

    @Override
    public int loadView() {
        return R.layout.card_main;
    }

    @Override
    public void dealWithData(JSONObject Data) {
        try {
            if(Data.getString("status").equals("failed")){
                cardBalance_txt.setText("不能读取数据");
                comsume_txt.setVisibility(View.GONE);
                cardRecord_list.setVisibility(View.GONE);
                return;
            }
            cardName_txt.setText(Data.getString("CardName"));
            cardBalance_txt.setText("¥"+Data.getString("CardBalance"));
            cardExpiry_txt.setText("有效期:"+Data.getString("BornDate")+"-"+Data.getString("Expire"));
            cardId_txt.setText("卡号:"+Data.getString("CardId"));
            cardVersion_txt.setText("版本:"+Data.getString("Version"));
            recordAdapter=new RecordAdapter(getContext(),Data.getJSONArray("Record"));
            cardRecord_list.setAdapter(recordAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
