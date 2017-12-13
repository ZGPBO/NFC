package com.pyp.nfcandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.bean.FileData;

import java.util.List;

/**
 * Created by hasee on 2017/7/11.
 */

public class SelectGirdAdapter extends BaseAdapter {

    private List<FileData> mItemList;
    private Context mCon;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public SelectGirdAdapter(Context con, List<FileData> ItemList){
        mCon=con;
        mItemList=ItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(null==view){
            view= LayoutInflater.from(mCon).inflate(R.layout.girdview_select_img,viewGroup,false);
            holder.img=(ImageView)view.findViewById(R.id.girdview_select_img);
        }


        FileData imageItem=mItemList.get(i);
        imageLoader.displayImage("file:///"+imageItem.getUrl(), holder.img);

        WindowManager wm = (WindowManager) mCon
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(width/3, width/3);
        view.setLayoutParams(layoutParams);

        return view;
    }

    private static class holder {
        public static ImageView img;

    }
}
