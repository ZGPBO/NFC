package com.pyp.nfcandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.util.tagItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class TagAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<tagItem> data;

    public TagAdapter(String [] title, int[] images, String[] detail, Context context){
        super();
        data=new ArrayList<tagItem>();
        inflater=LayoutInflater.from(context);
        for(int i=0;i<images.length;i++){
            tagItem tag=new tagItem(images[i],title[i],detail[i]);
            data.add(tag);
        }
    }


    @Override
    public int getCount() {

        if(data!=null){
            return data.size();
        }else
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.tagitem,null);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)convertView.findViewById(R.id.txt_title);
            viewHolder.detail=(TextView)convertView.findViewById(R.id.txt_detail);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.img_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
           viewHolder.title.setText(data.get(position).getTitle());
            viewHolder.detail.setText(data.get(position).getDetail());
            viewHolder.image.setImageResource(data.get(position).getImage());

        return convertView;
    }

    class ViewHolder{
        public TextView title,detail;
        public ImageView image;
    }
}
