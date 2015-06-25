package com.android.push_up.share;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShareActivity extends ActionBarActivity {

    private List<Map<String, Object>> mapList = null;
    private GridView gridView;
    private ImageView ivTakePhoto;
    Map map;
    String fName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        SysApplication.getInstance().addActivity(this);
        gridView = (GridView) findViewById(R.id.girdView);
        ivTakePhoto = (ImageView) findViewById(R.id.ivTakePhoto);
        ivTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdStatus = Environment.getExternalStorageState();//获取SD卡状态
                if(sdStatus.equals(Environment.MEDIA_MOUNTED)){//sd卡状态良好
                    //创建文件目录和图像文件名称
                    File filePath = new File(Environment.getExternalStorageDirectory() + "/Push-Up/Image/");
                    if(!filePath.exists()){
                        filePath.mkdirs();
                    }
                    //设置照片名称
                    String fileName =  new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoIntent.putExtra(MediaStore.Images.Media.ORIENTATION,0);
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Push-Up/Image/"+fileName)));
                    startActivity(photoIntent);
                }
                else{
                    Toast.makeText(ShareActivity.this, "没有存储卡", Toast.LENGTH_SHORT);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                map = mapList.get(position);
                fName = (String) map.get("fName");
                shareMsg("Push-Up分享", "这是分享的标题", "这是分享的内容", fName);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapList = FileList.findFile(Environment.getExternalStorageDirectory() + "/Push-Up/Image/");
        ImageAdapter imageAdapter = new ImageAdapter(ShareActivity.this,mapList);
        gridView.setAdapter(imageAdapter);
    }

    //横屏拍照获取Uri不为空
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //android分享功能
    public void shareMsg(String dialogTitle, String msgTitle, String msgText,String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File file = new File(imgPath);
            if (file != null && file.exists() && file.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(file);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        //设置以新任务打开
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, dialogTitle));
    }
}
