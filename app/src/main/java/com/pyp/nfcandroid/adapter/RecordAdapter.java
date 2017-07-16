package com.pyp.nfcandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyp.nfcandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pyp on 2017/7/12.
 */

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private JSONArray jsonArray;
    private LayoutInflater mInflater;

    public RecordAdapter(Context context, JSONArray jsonArray){
        this.mInflater=LayoutInflater.from(context);
        this.jsonArray=jsonArray;
    }



    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=mInflater.inflate(R.layout.record_list,parent,false);
            holder.recordDate_txt= (TextView) convertView.findViewById(R.id.recordDate_txt);
            holder.recordCash_txt= (TextView) convertView.findViewById(R.id.recordCash_txt);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        try {
            JSONObject jsonObject=jsonArray.getJSONObject(position);
            holder.recordDate_txt.setText(jsonObject.getString("Date"));
            holder.recordCash_txt.setText("¥"+jsonObject.getString("RecordCash"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class Holder{
        TextView recordDate_txt,recordCash_txt;
    }
}
