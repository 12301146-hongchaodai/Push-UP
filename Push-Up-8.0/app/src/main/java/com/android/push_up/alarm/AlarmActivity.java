package com.android.push_up.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.push_up.guide.R;

public class AlarmActivity extends ActionBarActivity {

    private LinearLayout alarmRecord;
    private CheckBox cbOpenAlarm;
    private CheckBox cbOpenVibrator;
    private CheckBox cbOpenNotify;
    private TextView tvAlarmRecord;
    private SharedPreferences sharedPreferences;
    public static Vibrator vib;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        vib = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        cbOpenAlarm = (CheckBox) findViewById(R.id.cbOpenAlarm);
        cbOpenVibrator = (CheckBox) findViewById(R.id.cbOpenVibrator);
        cbOpenNotify = (CheckBox) findViewById(R.id.cbOpenNotify);
        sharedPreferences = getSharedPreferences("alarm_record", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        cbOpenAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("openAlarm",true);
                }
                else{
                    editor.putBoolean("openAlarm",false);
                }
                editor.commit();
            }
        });

        cbOpenVibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("openVibrator", true);
                }
                else{
                    editor.putBoolean("openVibrator", false);
                }
                editor.commit();
            }
        });

        cbOpenNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("openNotify",true);
                }
                else{
                    editor.putBoolean("openNotify",false);
                }
                editor.commit();
            }
        });

        alarmRecord = (LinearLayout) findViewById(R.id.alarmRecord);
        tvAlarmRecord = (TextView) findViewById(R.id.tvAlarmRecord);
        alarmRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.alarm_set, null);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
                timePicker.setIs24HourView(true);

                new AlertDialog.Builder(AlarmActivity.this).setTitle("设置提醒时间").setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String timeHour = String.valueOf(timePicker.getCurrentHour());
                                String timeMinO = String.valueOf(timePicker.getCurrentMinute());
                                String timeMinN = null;
                                if (Integer.parseInt(timeMinO) < 10) {
                                    timeMinN = "0" + timeMinO;
                                } else {
                                    timeMinN = timeMinO;
                                }
                                String timeStr = timeHour + ":" + timeMinN;
                                tvAlarmRecord.setText("时间: " + timeStr);

                                editor.putString("timeSet",timeStr);
                                editor.commit();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        tvAlarmRecord.setText("时间: " + sharedPreferences.getString("timeSet","00:00"));
        if(sharedPreferences.getBoolean("openAlarm",false)){
            cbOpenAlarm.setChecked(true);
        }
        else{
            cbOpenAlarm.setChecked(false);
        }

        if(sharedPreferences.getBoolean("openVibrator",false)){
            cbOpenVibrator.setChecked(true);
        }
        else{
            cbOpenVibrator.setChecked(false);
        }

        if(sharedPreferences.getBoolean("openNotify",false)){
            cbOpenNotify.setChecked(true);
        }
        else{
            cbOpenNotify.setChecked(false);
        }

        //开启闹钟服务返回alarmManager对象
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        //封装Intent动作
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //一分钟重复一次
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 60000, pendingIntent);
    }
}
