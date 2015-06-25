package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.push_up.guide.R;

import java.util.Calendar;
import java.util.Locale;

public class CountActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private TextView textViewCount;
    private ImageButton btnCount;

    private PopupWindow popupWindow;
    private Button pop_btnConfirm;
    private Button pop_btnCancel;

    boolean showNext = true;
    MyHandler myHandler;
    MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        //判断是否下次显示弹出窗口
        sharedPreferences = getSharedPreferences("alert_count", Context.MODE_PRIVATE);
        showNext = sharedPreferences.getBoolean("showNext",true);
        //弹出窗口提醒
        if(showNext){
            View alertView = getLayoutInflater().inflate(R.layout.alertcount,null);
            new AlertDialog.Builder(CountActivity.this).setView(alertView)
                    .setPositiveButton("下次不再显示",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("showNext",false);
                            editor.commit();
                        }
                    }).show();
        }

        btnCount = (ImageButton) findViewById(R.id.btnCount);
        textViewCount = (TextView) findViewById(R.id.textViewCount);

        //训练完之后弹出菜单
        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(CountActivity.this).inflate(R.layout.countpop,null);

                pop_btnConfirm = (Button) view.findViewById(R.id.pop_btnConfirm);
                pop_btnCancel = (Button) view.findViewById(R.id.pop_btnCancel);

                pop_btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //数据保存代码
                        //获取当前计数数目
                        int count = Integer.parseInt(CountActivity.this.textViewCount.getText().toString());
                        String time = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)).toString();
                        DataToSQLite dataToSQLite = new DataToSQLite(CountActivity.this);
                        SQLiteDatabase dbWrite = dataToSQLite.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("username","admin");
                        cv.put("time",time);
                        cv.put("count",count);
                        dbWrite.insert("count",null,cv);

                        dbWrite.close();

                        //数据读取
                        SQLiteDatabase dbRead = dataToSQLite.getReadableDatabase();
                        Cursor cursor = dbRead.query("count", null, null, null, null, null, null);
                        while(cursor.moveToNext()){
                            int db_Id = cursor.getInt(cursor.getColumnIndex("_id"));
                            String dbUsername = cursor.getString(cursor.getColumnIndex("username"));
                            String dbTime = cursor.getString(cursor.getColumnIndex("time"));
                            int dbCount = cursor.getInt(cursor.getColumnIndex("count"));
                            System.out.println(String.format("_id=%d,username=%s,time=%s,count=%s",db_Id,dbUsername,dbTime,dbCount));
                        }
                        dbRead.close();
                        popupWindow.dismiss();
                    }
                });

                pop_btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                if(popupWindow == null){
                    popupWindow = new PopupWindow(CountActivity.this);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setFocusable(true);
                    popupWindow.setTouchable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setContentView(view);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                }

                popupWindow.showAtLocation(btnCount, Gravity.BOTTOM,0,0);//底部居中显示
                popupWindow.update();
            }
        });

        //计数事件
        textViewCount.setOnClickListener(new View.OnClickListener() {
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
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putInt("count",Integer.parseInt(textViewCount.getText().toString()));
            msg.setData(b);
            CountActivity.this.myHandler.sendMessage(msg);
        }
    }
}
