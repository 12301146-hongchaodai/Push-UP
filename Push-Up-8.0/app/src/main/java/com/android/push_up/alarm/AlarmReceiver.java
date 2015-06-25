package com.android.push_up.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.android.push_up.guide.R;
import com.android.push_up.home.HomeActivity;

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
        Boolean openNotify = sharedPreferences.getBoolean("openNotify",false);
        if(timeSet.equals(now)){
            if(openAlarm){
                final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
                mediaPlayer.start();
            }
            if(openVibrator){
                AlarmActivity.vib.vibrate(new long[]{200,1000,200,1000},-1);
            }
            if(openNotify){
                //获取系统服务
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                //创建对象
                Notification notification = new Notification();
                //设置属性
                notification.icon = R.drawable.logo;
                notification.tickerText = "Push-Up";
                notification.when = System.currentTimeMillis();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent intentNotification = new Intent(context, HomeActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentNotification, 0);
                notification.setLatestEventInfo(context,"Push-Up","闹钟提醒",pendingIntent);
                notificationManager.notify(1,notification);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notify",true);
                editor.commit();
            }
        }
    }
}
