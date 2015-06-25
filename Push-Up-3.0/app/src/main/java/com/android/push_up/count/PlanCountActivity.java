package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.push_up.guide.R;

public class PlanCountActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private TextView textViewPlanCount;
    private ImageButton btnPlanCount;

    private PopupWindow popupPlanWindow;
    private Button popPlan_btnConfirm;
    private Button popPlan_btnCancel;

    boolean showNext = true;
    MyHandler myHandler;
    MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_count);
        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        showNext = sharedPreferences.getBoolean("showNext",true);
        if(showNext){
            View alertPlanView = getLayoutInflater().inflate(R.layout.alertplancount,null);
            new AlertDialog.Builder(PlanCountActivity.this).setView(alertPlanView)
                    .setPositiveButton("下次不再显示",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("showNext",false);
                            editor.commit();
                        }
                    }).show();
        }
        btnPlanCount = (ImageButton) findViewById(R.id.btnPlanCount);
        textViewPlanCount = (TextView) findViewById(R.id.textViewPlanCount);

        btnPlanCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(PlanCountActivity.this).inflate(R.layout.countplanpop,null);

                popPlan_btnConfirm = (Button) view.findViewById(R.id.popPlan_btnConfirm);
                popPlan_btnCancel = (Button) view.findViewById(R.id.popPlan_btnCancel);

                popPlan_btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //数据保存代码
                        popupPlanWindow.dismiss();
                    }
                });

                popPlan_btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupPlanWindow.dismiss();
                    }
                });

                if(popupPlanWindow == null){
                    popupPlanWindow = new PopupWindow(PlanCountActivity.this);
                    popupPlanWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupPlanWindow.setFocusable(true);
                    popupPlanWindow.setTouchable(true);
                    popupPlanWindow.setOutsideTouchable(true);
                    popupPlanWindow.setContentView(view);
                    popupPlanWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupPlanWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                }

                popupPlanWindow.showAtLocation(btnPlanCount, Gravity.BOTTOM,0,0);//底部居中显示
                popupPlanWindow.update();
            }
        });
        textViewPlanCount.setOnClickListener(new View.OnClickListener() {
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
            PlanCountActivity.this.textViewPlanCount.setText(String.valueOf(count+1));
        }
    }

    class MyThread implements Runnable{

        @Override
        public void run() {
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putInt("count",Integer.parseInt(textViewPlanCount.getText().toString()));
            msg.setData(b);
            PlanCountActivity.this.myHandler.sendMessage(msg);
        }
    }
}
