package com.pyp.nfcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.pyp.nfcandroid.R;
import com.pyp.nfcandroid.adapter.TagAdapter;

/**
 * Created by Administrator on 2017/7/12.
 */

public class NfcTagActivity extends BaseActivity{
    //办公标签的GradView
    private GridView grad_office;
    //社交标签的GradView
    private GridView grad_social;

    //办公标签类型的标题,详细功能,图片数组
    private String[] office_titles=new String[]{"汽车","办公室","家","床边"};
    private String[] office_detail=new String[]{"激活蓝牙,地图","打开wifi,关闭铃声","打开wifi，打开铃声","静音,设置闹钟"};
    private int[] office_images =new int[]{R.mipmap.car,R.mipmap.office,R.mipmap.home,R.mipmap.bed};


    //社交类型的标题,详细功能,图片数组
    private String[] social_titles=new String[]{"短信","WIFI","电话"};
    private String[] social_detail=new String[]{"发送文本","连接到网络","打电话"};
    private int[] social_images=new int[]{R.mipmap.mes,R.mipmap.wifi,R.mipmap.tel};

    //加载相应的布局文件
    @Override
    protected int loadLayoutView() {
        return R.layout.activity_tagmain;
    }

    //初始化属性
    @Override
    protected void initParameter() {
        super.initParameter();
        //初始化办公标签的GradView控件
        grad_office= (GridView) findViewById(R.id.gridofficetag);
        //创建适配器
        TagAdapter officeAdapter=new TagAdapter(office_titles,office_images,office_detail,this);
        grad_office.setAdapter(officeAdapter);

        //初始化社交标签的GradView控件
        grad_social= (GridView) findViewById(R.id.gridsocialtag);
        //创建适配器
        TagAdapter socialAdapter=new TagAdapter(social_titles,social_images,social_detail,this);

        grad_social.setAdapter(socialAdapter);


    }

    //事件处理
    @Override
    protected void initEvent() {
        super.initEvent();
        //给事件办公标签的GradView添加点击事件
        grad_office.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //跳转到相关操作的界面
                Intent intent;
                //获得相对应的事件
                String name=office_titles[position];
                switch(name){
                    case "汽车":
                        //跳转到汽车模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,CarModelActivity.class);
                        break;
                    case "办公室":
                        //跳转到办公室模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,OfficeActivity.class);
                        break;
                    case "家":
                        //跳转到家模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,HomeActivity.class);
                        break;
                    case "床边":
                        //跳转到床边模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,BedActivity.class);
                        break;
                    default:
                        return;
                }
                //intent是否存在
                if(intent!=null){
                    startActivity(intent);
                }

            }
        });

        //给事件社交标签的GradView添加点击事件
        grad_social.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //跳转到WriteTagActivity的界面
                Intent intent;
                //获得相对应的事件
                String name=social_titles[position];
                switch (name){
                    //跳转到短信模式下的设置界面
                    case "短信":
                        intent=new Intent(NfcTagActivity.this,MsgActivity.class);
                        break;
                    case "WIFI":
                        //跳转到WIFI模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,WifiWriteActivity.class);
                        break;
                    case "电话":
                        //跳转到电话模式下的设置界面
                        intent=new Intent(NfcTagActivity.this,CallActivity.class);
                        break;
                    default:
                        return;
                }
                if(intent!=null){
                    startActivity(intent);
                }
            }
        });

    }

    //给activity起名字
    @Override
    protected String setActivityName() {
        return null;
    }
}
