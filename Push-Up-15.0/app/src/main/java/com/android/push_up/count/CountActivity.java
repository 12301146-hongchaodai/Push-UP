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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;

import java.util.Calendar;
import java.util.Locale;

public class CountActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesName;
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
        SysApplication.getInstance().addActivity(this);
        //判断是否下次显示弹出窗口
        sharedPreferences = getSharedPreferences("alert_count", Context.MODE_PRIVATE);
        sharedPreferencesName = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        showNext = sharedPreferences.getBoolean("showNext",true);
        //弹出窗口提醒
        if(showNext){
            View alertView = getLayoutInflater().inflate(R.layout.alertcount,null);
            new AlertDialog.Builder(CountActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        dialog.dismiss();
                        CountActivity.this.finish();
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }).setView(alertView)
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
                        String time = new DateFormat().format("yyyy/MM/dd", Calendar.getInstance(Locale.CHINA)).toString();
                        DataToSQLite dataToSQLite = new DataToSQLite(CountActivity.this);
                        SQLiteDatabase dbWrite = dataToSQLite.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("username",sharedPreferencesName.getString("username",""));
                        cv.put("time",time);
                        cv.put("count",count);
                        dbWrite.insert("data_count",null,cv);

                        dbWrite.close();
                        new AlertDialog.Builder(CountActivity.this).setTitle("提示").setMessage("数据是否清零？").setPositiveButton("清零",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //将当前锻炼数据归0
                                CountActivity.this.textViewCount.setText("0");
                            }
                        }).setNegativeButton("取消",null).show();

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
