package com.android.push_up.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.push_up.alarm.AlarmActivity;
import com.android.push_up.count.DataToSQLite;
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

        final View setView = inflater.inflate(R.layout.fragment_set,container,false);

        //闹钟设置
        setView.findViewById(R.id.alarm_set).setOnTouchListener(new View.OnTouchListener() {
            Drawable background = setView.findViewById(R.id.alarm_set).getBackground();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    setView.findViewById(R.id.alarm_set).setBackgroundDrawable(setView.getBackground());
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    setView.findViewById(R.id.alarm_set).setBackgroundDrawable(background);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    setView.findViewById(R.id.alarm_set).setBackgroundDrawable(background);
                    startActivity(new Intent(getActivity(), AlarmActivity.class));
                    return false;
                }
                return false;
            }
        });

        //数据备份
        setView.findViewById(R.id.data_BackUps).setOnTouchListener(new View.OnTouchListener() {
            Drawable background = setView.findViewById(R.id.alarm_set).getBackground();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    setView.findViewById(R.id.data_BackUps).setBackgroundDrawable(setView.getBackground());
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    setView.findViewById(R.id.data_BackUps).setBackgroundDrawable(background);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    setView.findViewById(R.id.data_BackUps).setBackgroundDrawable(background);
                    for (int i = 0; i < fileList.size() ; i++){
                        UploadUtil.uploadFile(fileList.get(i),getActivity(),"http://172.28.3.227:8888/upload_file_service/UploadServlet?MyDir=" + "my");
                    }
                    return false;
                }
                return false;
            }
        });

        //恢复数据
        setView.findViewById(R.id.data_Recovery).setOnTouchListener(new View.OnTouchListener() {

            Drawable background = setView.findViewById(R.id.data_Recovery).getBackground();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    setView.findViewById(R.id.data_Recovery).setBackgroundDrawable(setView.getBackground());
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    setView.findViewById(R.id.data_Recovery).setBackgroundDrawable(background);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    setView.findViewById(R.id.data_Recovery).setBackgroundDrawable(background);
                    //恢复数据操作
                    DownloadUtil.downloadFile(getActivity(),"/sdcard/Push-Up/Download","http://172.28.3.227:8888/download_file_service/DownloadServlet?MyDir=" + "my");
                    return false;
                }
                return false;
            }
        });

        //删除数据
        setView.findViewById(R.id.data_Delete).setOnTouchListener(new View.OnTouchListener() {

            Drawable background = setView.findViewById(R.id.alarm_set).getBackground();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    setView.findViewById(R.id.data_Delete).setBackgroundDrawable(setView.getBackground());
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    setView.findViewById(R.id.data_Delete).setBackgroundDrawable(background);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    setView.findViewById(R.id.data_Delete).setBackgroundDrawable(background);
                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("确认删除本地锻炼数据？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //清除本地数据
                            //清除锻炼计划
                            sharedPreferences = getActivity().getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("planLevel", 0);
                            editor.putBoolean("planDiet",false);
                            editor.commit();
                            //清除数据库锻炼记录数据
                            DataToSQLite dataToSQLite = new DataToSQLite(getActivity());
                            SQLiteDatabase db = dataToSQLite.getWritableDatabase();
                            db.delete("data_count", null, null);
                            db.close();
                            //清除本地图片
                            String path = "/sdcard/Push-Up/Image";
                            File file = new File(path);
                            File[] files = file.listFiles();
                            for(int i = 0; i < files.length; i++){
                                files[i].delete();
                            }
                            //清除闹钟设置
                            sharedPreferences = getActivity().getSharedPreferences("alarm_record", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorAlarm = sharedPreferences.edit();
                            editorAlarm.putBoolean("openAlarm",false);
                            editorAlarm.putBoolean("openVibrator",false);
                            editorAlarm.putBoolean("openNotify",false);
                            editorAlarm.putString("timeSet","00:00");
                            editorAlarm.commit();
                        }
                    }).setNegativeButton("取消", null).show();
                    return false;
                }
                return false;
            }
        });

        return setView;
    }
}
