package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;
import com.android.push_up.home.HomeActivity;

public class MyPlanActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private LinearLayout lmakeMyPlan;
    private TextView tvPlanLevel;
    private LinearLayout planLevel1;
    private LinearLayout planLevel2;
    private LinearLayout planLevel3;
    int planLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        SysApplication.getInstance().addActivity(this);
        lmakeMyPlan = (LinearLayout) findViewById(R.id.lmakeMyPlan);

        tvPlanLevel = (TextView) findViewById(R.id.tvPlanLevel);

        planLevel1 = (LinearLayout) findViewById(R.id.planLevel1);
        planLevel2 = (LinearLayout) findViewById(R.id.planLevel2);
        planLevel3 = (LinearLayout) findViewById(R.id.planLevel3);

        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        planLevel = sharedPreferences.getInt("planLevel",0);
        switch(planLevel){
            case 0:
                new AlertDialog.Builder(MyPlanActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_BACK){
                            dialog.dismiss();
                            MyPlanActivity.this.finish();
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                }).setTitle("提示").setMessage("还没有制定健身计划，是否前往制定？").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyPlanActivity.this,PlanCountActivity.class));
                        MyPlanActivity.this.finish();
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyPlanActivity.this.finish();
                    }
                }).show();
                break;
            case 1:
                //生成健身计划1
                lmakeMyPlan.setVisibility(View.VISIBLE);
                tvPlanLevel.setText("锻炼计划等级1");
                planLevel1.setVisibility(View.VISIBLE);
                planLevel2.setVisibility(View.GONE);
                planLevel3.setVisibility(View.GONE);
                break;
            case 2:
                //生成健身计划2
                lmakeMyPlan.setVisibility(View.VISIBLE);
                tvPlanLevel.setText("锻炼计划等级2");
                planLevel1.setVisibility(View.GONE);
                planLevel2.setVisibility(View.VISIBLE);
                planLevel3.setVisibility(View.GONE);
                break;
            case 3:
                //生成健身计划3
                lmakeMyPlan.setVisibility(View.VISIBLE);
                tvPlanLevel.setText("锻炼计划等级3");
                planLevel1.setVisibility(View.GONE);
                planLevel2.setVisibility(View.GONE);
                planLevel3.setVisibility(View.VISIBLE);
                break;
        }
    }
}
