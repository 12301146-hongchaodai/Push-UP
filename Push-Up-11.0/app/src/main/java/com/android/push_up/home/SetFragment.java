package com.android.push_up.home;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.push_up.alarm.AlarmActivity;
import com.android.push_up.guide.R;

import java.io.File;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private List<File> fileList = null;

    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final String filePath = "/sdcard/Push-Up/Image";
        //所有图片路径
        fileList  = FindFiles.findFilesFromSD(filePath);

        View setView = inflater.inflate(R.layout.fragment_set,container,false);

        setView.findViewById(R.id.alarm_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AlarmActivity.class));
            }
        });

        setView.findViewById(R.id.data_BackUps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < fileList.size() ; i++){
                    UploadUtil.uploadFile(fileList.get(i),getActivity(),"http://172.24.7.58:8888/upload_file_service/UploadServlet?MyDir=" + "my");
                }
            }
        });

        return setView;
    }
}
