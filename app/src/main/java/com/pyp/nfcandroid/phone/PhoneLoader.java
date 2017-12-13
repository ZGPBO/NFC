package com.pyp.nfcandroid.phone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.pyp.nfcandroid.activity.SelectFileActivity;
import com.pyp.nfcandroid.bean.FileData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/7/16.
 */

public class PhoneLoader {

    private static ContentResolver mContentResolver;
    private static String[] projection =
                    {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE};

    //初始化内容提供者
    private static void initContentResolver(ContentResolver contentResolver){
        if(null==mContentResolver){
            mContentResolver=contentResolver;
        }
    }

    //得到手机的所有文本
    public static List<FileData> getTextFileData(ContentResolver contentResolver){

        initContentResolver(contentResolver);

        Uri contentUri=MediaStore.Files.getContentUri("external");
        String Where=
                "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.doc'"
                        + " or "
                        + MediaStore.Files.FileColumns.DATA + " LIKE '%.docx'"
                        + " or "
                        + MediaStore.Files.FileColumns.DATA + " LIKE '%.xls'"
                        + " or "
                        + MediaStore.Files.FileColumns.DATA + " LIKE '%.ppt'"
                        + " or "
                        + MediaStore.Files.FileColumns.DATA + " LIKE '%.txt'" + ")";

        Cursor cursor = mContentResolver.query(contentUri,projection,Where, null, null);


        return getList(cursor,SelectFileActivity.CODE_TEXT);

    }

    //得到手机的所有图片信息
    public static List<FileData> getImageFileData(ContentResolver contentResolver){

        initContentResolver(contentResolver);

        Uri contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Images.Media._ID + " desc";
        String Where=null;

        Cursor cursor = contentResolver.query(contentUri, projection, Where, null, sortOrder);

        return getList(cursor,SelectFileActivity.CODE_ING);
    }

    //得到手机的所有音乐信息
    public static List<FileData> getMusicFileData(ContentResolver contentResolver){

        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') " +
                "and _display_name <> 'audio' and is_music > 0";
        String sortOrder = MediaStore.Images.Media._ID + " desc";
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);

        return getList(cursor,SelectFileActivity.CODE_MUNSIC);

    }

    //对传入cursor进行遍历，生产相对应的列表项
    private static List<FileData> getList(Cursor cursor,int Type){

        List<FileData> FileDatas=new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                int imageId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));

                FileData imageItem = new FileData();
                imageItem.setType(Type);
                imageItem.setID(imageId);
                imageItem.setUrl(imagePath);

                if(null!=name){
                    imageItem.setName(name);
                }else {
                    int position_x = imagePath.lastIndexOf( File.separator );
                    String displayName = imagePath.substring( position_x + 1 , imagePath.length() );
                    imageItem.setName(displayName);
                }


                imageItem.setSize(size);
                FileDatas.add(imageItem);
            }while(cursor.moveToNext());
        }

        return FileDatas;

    }
}
