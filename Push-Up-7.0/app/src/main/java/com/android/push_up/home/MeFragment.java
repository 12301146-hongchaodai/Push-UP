package com.android.push_up.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.push_up.count.PlanCountActivity;
import com.android.push_up.guide.R;
import com.android.push_up.share.ShareActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;


public class MeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View meView = inflater.inflate(R.layout.fragment_me,container,false);

        meView.findViewById(R.id.btnMakePlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlanCountActivity.class));
            }
        });

        meView.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdStatus = Environment.getExternalStorageState();//获取SD卡状态
                if(sdStatus.equals(Environment.MEDIA_MOUNTED)){//sd卡状态良好
                    //创建文件目录和图像文件名称
                    File filePath = new File("/sdcard/Push-Up/Image/");
                    if(!filePath.exists()){
                        filePath.mkdirs();
                    }
                    //设置照片名称
                    String fileName =  new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoIntent.putExtra(MediaStore.Images.Media.ORIENTATION,0);
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File("/sdcard/Push-Up/Image/"+fileName)));
                    Toast.makeText(getActivity(),fileName + "已保存",Toast.LENGTH_SHORT);
                    startActivity(photoIntent);
                }
                else{
                    Toast.makeText(getActivity(),"没有存储卡",Toast.LENGTH_SHORT);
                }
            }
        });

        meView.findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        return meView;
    }

    //横屏拍照获取Uri不为空
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}