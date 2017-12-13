package com.pyp.nfcandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.activity.SelectFileActivity;
import com.pyp.nfcandroid.bean.FileData;
import com.pyp.nfcandroid.util.BtyeTo16bit;

import java.util.List;

/**
 * Created by hasee on 2017/7/11.
 */

public class SelectListAdapter extends BaseAdapter {

    private List<FileData> mList;
    private Context mCon;

    public SelectListAdapter(Context Con, List<FileData> List){
        this.mCon=Con;
        this.mList=List;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(null==view){
            view= LayoutInflater.from(mCon).inflate(R.layout.listview_select_music,viewGroup,false);
            holder.name=(TextView)view.findViewById(R.id.listview_select_name);
            holder.size=(TextView)view.findViewById(R.id.listview_select_size);
            holder.img=(ImageView)view.findViewById(R.id.listview_select_icon);
        }
        FileData Info=mList.get(i);
        holder.name.setText(Info.getName());
        holder.size.setText(BtyeTo16bit.getPrintSize(Info.getSize()));

        if(SelectFileActivity.CODE_TEXT==Info.getType()){
            holder.img.setImageResource(R.drawable.iocn_txt);
        }else if(SelectFileActivity.CODE_MUNSIC==Info.getType()){
            holder.img.setImageResource(R.drawable.icon_music);
        }


        return view;
    }

    private static class holder{

        public static TextView name;
        public static TextView size;
        public static ImageView img;
    }


}
