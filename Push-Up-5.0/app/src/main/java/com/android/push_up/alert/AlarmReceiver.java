package com.android.push_up.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;

import com.android.push_up.guide.R;

import java.util.Calendar;

/**
 * Created by 周宇环 on 2015/5/27.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("alarm_record", Context.MODE_PRIVATE);
        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String now = null;
        if(Integer.parseInt(minute) < 10){
            now = hour + ":0" + minute;
        }
        else{
            now = hour + ":" + minute;
        }
        String timeSet = sharedPreferences.getString("timeSet", "");
        Boolean openAlarm = sharedPreferences.getBoolean("openAlarm",false);
        Boolean openVibrator = sharedPreferences.getBoolean("openVibrator",false);
        System.out.println(timeSet+" "+openAlarm+" "+openVibrator);
        if(timeSet.equals(now)){
            if(openAlarm){
                final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
            if(openVibrator){
                AlarmActivity.vib.vibrate(2000);
            }
        }
    }
}
