package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;
import com.android.push_up.home.HomeActivity;

public class DietPlanActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        SysApplication.getInstance().addActivity(this);
        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("planDiet",false)){
            //显示饮食计划
            findViewById(R.id.lDietPlan).setVisibility(View.VISIBLE);
        }
        else{
            new AlertDialog.Builder(DietPlanActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        dialog.dismiss();
                        DietPlanActivity.this.finish();
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }).setTitle("提示").setMessage("还没有制定饮食计划，是否前往制定？").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(DietPlanActivity.this,PlanCountActivity.class));
                    DietPlanActivity.this.finish();
                }
            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DietPlanActivity.this.finish();
                }
            }).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
