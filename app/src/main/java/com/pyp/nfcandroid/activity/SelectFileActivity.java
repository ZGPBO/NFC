package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.adapter.SelectGirdAdapter;
import com.pyp.nfcandroid.adapter.SelectListAdapter;
import com.pyp.nfcandroid.bean.FileData;
import com.pyp.nfcandroid.fragment.SelectFileFragment;
import com.pyp.nfcandroid.phone.PhoneLoader;
import com.pyp.nfcandroid.util.SparseArray2Array;

import java.util.ArrayList;
import java.util.List;

public class SelectFileActivity extends BaseActivity {

    public static final int CODE_TEXT=0;
    public static final int CODE_ING=1;
    public static final int CODE_MUNSIC=2;


    private TextView mSelect_text,mSelect_img,mSelect_config,mSelect_music;
    private Button mSendButton;

    private Fragment musicFragment,ImageFragment,TxTFragment;

    //管理Fragment
    private FragmentManager mFragmentManager;


    private SparseArray<FileData> mSendData=new SparseArray<FileData>();



    @Override
    protected void initParameter() {
        mSelect_text=(TextView)findViewById(R.id.act_select_text);
        mSelect_img=(TextView)findViewById(R.id.act_select_img);
        mSelect_config=(TextView)findViewById(R.id.act_select_config);
        mSelect_music=(TextView)findViewById(R.id.act_select_music);
        mSendButton=(Button) findViewById(R.id.act_select_sendbtn);
        mFragmentManager=getSupportFragmentManager();
        super.initParameter();
    }


    @Override
    protected void initEvent() {


        mSelect_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSendData.clear();
                if(null==TxTFragment){
                    final List<FileData> TxTs=PhoneLoader.getTextFileData(getContentResolver());
                    SelectListAdapter adapter=new SelectListAdapter(SelectFileActivity.this,TxTs);
                    TxTFragment=SelectFileFragment.CreateFragment(adapter, SelectFileFragment.LIST_VIEW, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ImageView box=(ImageView)view.findViewById(R.id.listview_select_check);
                            if(box.getVisibility()==View.VISIBLE){
                                mSendData.remove(i);
                                box.setVisibility(View.GONE);
                            }else{
                                mSendData.put(i,TxTs.get(i));
                                box.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }



                mFragmentManager.beginTransaction().replace(R.id.act_select_container,TxTFragment).commit();

            }
        });

        mSelect_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSendData.clear();
                if(null==ImageFragment){
                    final List<FileData> imgs= PhoneLoader.getImageFileData(getContentResolver());
                    SelectGirdAdapter adapter=new SelectGirdAdapter(SelectFileActivity.this,imgs);

                    ImageFragment=SelectFileFragment.CreateFragment(adapter, SelectFileFragment.GIRD_VIEW,
                            new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            ImageView box=(ImageView)view.findViewById(R.id.girdview_select_box);
                            if(box.getVisibility()==View.VISIBLE){
                                mSendData.remove(i);
                                box.setVisibility(View.INVISIBLE);
                            }else{
                                mSendData.put(i,imgs.get(i));
                                box.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                }
                mFragmentManager.beginTransaction().replace(R.id.act_select_container,ImageFragment).commit();
            }
        });

        mSelect_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSendData.clear();
                if(null==musicFragment){
                    final List<FileData> musics=PhoneLoader.getMusicFileData(getContentResolver());
                    SelectListAdapter adapter=new SelectListAdapter(SelectFileActivity.this,musics);
                    musicFragment=SelectFileFragment.CreateFragment(adapter, SelectFileFragment.LIST_VIEW, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ImageView box=(ImageView)view.findViewById(R.id.listview_select_check);
                            if(box.getVisibility()==View.VISIBLE){
                                mSendData.remove(i);
                                box.setVisibility(View.GONE);
                            }else{
                                mSendData.put(i,musics.get(i));
                                box.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }



                mFragmentManager.beginTransaction().replace(R.id.act_select_container,musicFragment).commit();

            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    @Override
    protected int loadLayoutView() {
        return R.layout.activity_select_file;
    }

    @Override
    protected String setActivityName() {
        return "SelectFileActivity";
    }

    private void sendData(){

        finish();
        Intent intent=new Intent(this,NFCP2PActivity.class);
        Bundle bundle=new Bundle();
        ArrayList<FileData> datas=SparseArray2Array.transform(mSendData);
        bundle.putParcelableArrayList("Data",datas);
        intent.putExtras(bundle);

        startActivity(intent);

    }
}