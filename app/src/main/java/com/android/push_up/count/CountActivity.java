package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.push_up.guide.R;

public class CountActivity extends ActionBarActivity {

    boolean showNext = true;
    private SharedPreferences sharedPreferences;
    private LinearLayout rootCount;
    private TextView textViewCount;
    MyHandler myHandler;
    MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        //判断是否第一次使用
        sharedPreferences = getSharedPreferences("alert_count", Context.MODE_PRIVATE);
        showNext = sharedPreferences.getBoolean("showNext",true);
        //弹出窗口提醒
        if(showNext){
            View alertView = getLayoutInflater().inflate(R.layout.alertcount,null);
            new AlertDialog.Builder(CountActivity.this).setTitle("使用说明").setView(alertView)
                    .setNeutralButton("下次不再显示",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("showNext",false);
                            editor.commit();
                        }
                    }).show();
        }

        rootCount = (LinearLayout) findViewById(R.id.rootCount);
        textViewCount = (TextView) findViewById(R.id.textViewCount);

        rootCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler = new MyHandler();
                myThread = new MyThread();
                new Thread(myThread).start();
            }
        });
    }

    class MyHandler extends Handler {
        public MyHandler(){

        }

        public MyHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int count = b.getInt("count");
            CountActivity.this.textViewCount.setText(String.valueOf(count+1));
        }
    }

    class MyThread implements Runnable{

        @Override
        public void run() {
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putInt("count",Integer.parseInt(textViewCount.getText().toString()));
            msg.setData(b);
            CountActivity.this.myHandler.sendMessage(msg);

        }
    }
}
