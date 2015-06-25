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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;

public class PlanCountActivity extends ActionBarActivity {

    //定义全局变量
    private static final String HEALTH_ONE = "强壮健康";
    private static final String HEALTH_TWO = "非常健康";
    private static final String HEALTH_THREE = "健康";
    private static final String HEALTH_FOUR = "一般";
    private static final String HEALTH_FIVE = "差";

    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int LEVEL_THREE = 3;

    private SharedPreferences sharedPreferences;
    private TextView textViewPlanCount;
    private ImageButton btnPlanCount;

    private PopupWindow popupPlanWindow;
    private Button popPlan_btnConfirm;
    private Button popPlan_btnCancel;

    boolean showNext = true;
    int planLevel = 0;
    MyHandler myHandler;
    MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_count);
        SysApplication.getInstance().addActivity(this);
        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        showNext = sharedPreferences.getBoolean("showNext",true);
        if(showNext){
            View alertPlanView = getLayoutInflater().inflate(R.layout.alertplancount,null);
            new AlertDialog.Builder(PlanCountActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        dialog.dismiss();
                        PlanCountActivity.this.finish();
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }).setView(alertPlanView)
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
                        //获取俯卧撑个数
                        final int count = Integer.parseInt(textViewPlanCount.getText().toString());
                        //用户年龄
                        final View makePlanView = LayoutInflater.from(PlanCountActivity.this).inflate(R.layout.makeplan,null);
                        final EditText etAge = (EditText) makePlanView.findViewById(R.id.etAge);

                        new AlertDialog.Builder(PlanCountActivity.this).setView(makePlanView).setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final int ageFromAlertDlg = Integer.parseInt(etAge.getText().toString());
                                //获取了用户的年龄和俯卧撑个数
                                String health_level = confirmHealthLevel(ageFromAlertDlg,count);
                                //以弹出对话框方式告知用户健康状况
                                switch(health_level){
                                    case HEALTH_ONE:
                                        new AlertDialog.Builder(PlanCountActivity.this).setMessage("您现在的健康状况：" + HEALTH_ONE).setPositiveButton("确定",null).create().show();
                                        break;
                                    case HEALTH_TWO:
                                        new AlertDialog.Builder(PlanCountActivity.this).setMessage("您现在的健康状况：" + HEALTH_TWO).setPositiveButton("确定", null).create().show();
                                        break;
                                    case HEALTH_THREE:
                                        new AlertDialog.Builder(PlanCountActivity.this).setMessage("您现在的健康状况：" + HEALTH_THREE).setPositiveButton("确定", null).create().show();
                                        break;
                                    case HEALTH_FOUR:
                                        new AlertDialog.Builder(PlanCountActivity.this).setMessage("您现在的健康状况：" + HEALTH_FOUR).setPositiveButton("确定", null).create().show();
                                        break;
                                    case HEALTH_FIVE:
                                        new AlertDialog.Builder(PlanCountActivity.this).setMessage("您现在的健康状况：" + HEALTH_FIVE).setPositiveButton("确定", null).create().show();
                                        break;
                                }

                                int plan_level = confirmPlanLevel(count);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //健康计划等级写入共享数据sharedPreferences里
                                switch(plan_level){
                                    case LEVEL_ONE:
                                        editor.putInt("planLevel",LEVEL_ONE);
                                        break;
                                    case LEVEL_TWO:
                                        editor.putInt("planLevel",LEVEL_TWO);
                                        break;
                                    case LEVEL_THREE:
                                        editor.putInt("planLevel",LEVEL_THREE);
                                        break;
                                }
                                editor.putBoolean("planDiet",true);
                                editor.commit();
                            }
                        }).show();

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

    //确定健康等级
    public String confirmHealthLevel(int age,int count){
        if(age < 30){
            if(count >= 0 && count <= 20)
                return HEALTH_FIVE;
            else if(count > 20 && count <=30)
                return HEALTH_FOUR;
            else if(count > 30 && count <=40)
                return HEALTH_THREE;
            else if(count > 40 && count <=50)
                return HEALTH_TWO;
            else
                return HEALTH_ONE;
        }
        else if(age >= 30 && age < 40){
            if(count >= 0 && count <= 16)
                return HEALTH_FIVE;
            else if(count > 16 && count <=26)
                return HEALTH_FOUR;
            else if(count > 26 && count <=36)
                return HEALTH_THREE;
            else if(count > 36 && count <=46)
                return HEALTH_TWO;
            else
                return HEALTH_ONE;
        }
        else if(age >=40 && age <50){
            if(count >= 0 && count <= 12)
                return HEALTH_FIVE;
            else if(count > 12 && count <=21)
                return HEALTH_FOUR;
            else if(count > 21 && count <=30)
                return HEALTH_THREE;
            else if(count > 30 && count <=39)
                return HEALTH_TWO;
            else
                return HEALTH_ONE;
        }
        else if(age >=50 && age <60){
            if(count >= 0 && count <= 8)
                return HEALTH_FIVE;
            else if(count > 8 && count <=16)
                return HEALTH_FOUR;
            else if(count > 16 && count <=24)
                return HEALTH_THREE;
            else if(count > 24 && count <=32)
                return HEALTH_TWO;
            else
                return HEALTH_ONE;
        }
        else{
            if(count >= 0 && count <= 4)
                return HEALTH_FIVE;
            else if(count > 4 && count <=12)
                return HEALTH_FOUR;
            else if(count > 12 && count <=20)
                return HEALTH_THREE;
            else if(count > 20 && count <=27)
                return HEALTH_TWO;
            else
                return HEALTH_ONE;
        }
    }

    //确定计划等级，传入俯卧撑个数
    public int confirmPlanLevel(int count){
        if(count >= 0 && count <=6){
            return LEVEL_ONE;
        }
        else if(count >= 7 && count <=20){
            return LEVEL_TWO;
        }
        else{
            return LEVEL_THREE;
        }
    }

}
