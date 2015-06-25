package com.android.push_up.count;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.push_up.guide.R;

public class MyPlanActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private TextView tvPlanLevel;
    private LinearLayout planLevel1;
    private LinearLayout planLevel2;
    private LinearLayout planLevel3;
    int planLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        tvPlanLevel = (TextView) findViewById(R.id.tvPlanLevel);

        planLevel1 = (LinearLayout) findViewById(R.id.planLevel1);
        planLevel2 = (LinearLayout) findViewById(R.id.planLevel2);
        planLevel3 = (LinearLayout) findViewById(R.id.planLevel3);

        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        planLevel = sharedPreferences.getInt("planLevel",0);
        switch(planLevel){
            case 0:
                tvPlanLevel.setText("还没有制定健身计划");
                break;
            case 1:
                //生成健身计划1
                tvPlanLevel.setText("锻炼计划等级1");
                planLevel1.setVisibility(View.VISIBLE);
                planLevel2.setVisibility(View.GONE);
                planLevel3.setVisibility(View.GONE);
                break;
            case 2:
                //生成健身计划2
                tvPlanLevel.setText("锻炼计划等级2");
                planLevel1.setVisibility(View.GONE);
                planLevel2.setVisibility(View.VISIBLE);
                planLevel3.setVisibility(View.GONE);
                break;
            case 3:
                //生成健身计划3
                tvPlanLevel.setText("锻炼计划等级3");
                planLevel1.setVisibility(View.GONE);
                planLevel2.setVisibility(View.GONE);
                planLevel3.setVisibility(View.VISIBLE);
                break;
        }
    }
}
