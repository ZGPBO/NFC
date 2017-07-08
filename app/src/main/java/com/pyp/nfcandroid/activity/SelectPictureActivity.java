package com.pyp.nfcandroid.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;

import com.pyp.nfcandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPictureActivity extends Activity {

    //显示图片名称的list
    ListView show_list;
    ArrayList names = null;
    ArrayList descs = null;
    ArrayList fileNames = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        show_list = (ListView) findViewById(R.id.listview);
        names = new ArrayList();
        descs = new ArrayList();
        fileNames = new ArrayList();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            names.add(name);
            descs.add(desc);
        }
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names.get(i));
            map.put("desc", descs.get(i));
            listItems.add(map);
        }

        Log.d("TAG",names.toString());
        Log.d("TAG",descs.toString());
        Log.d("TAG",fileNames.toString());

    }


}