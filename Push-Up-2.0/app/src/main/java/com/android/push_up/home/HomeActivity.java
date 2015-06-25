package com.android.push_up.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.push_up.guide.R;


/**
 * Created by 周宇环 on 2015/5/21.
 */
public class HomeActivity extends ActionBarActivity {
    private ImageView imageMe;
    private ImageView imageTeach;
    private ImageView imageSet;
    private SharedPreferences sharedPreferences;
    boolean stateMe = true;
    boolean stateTeach = false;
    boolean stateSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //读取文件
        sharedPreferences = getSharedPreferences("back_state", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        stateMe = sharedPreferences.getBoolean("stateMe",true);
        stateTeach = sharedPreferences.getBoolean("stateTeach",false);
        stateSet = sharedPreferences.getBoolean("stateSet",false);

        //关闭前页面是我
        if(stateMe){
            getSupportFragmentManager().beginTransaction().add(R.id.home_container,new MeFragment()).commit();
        }
        //关闭前页面是教你
        if(stateTeach){
            getSupportFragmentManager().beginTransaction().add(R.id.home_container, new TeachFragment()).commit();
        }
        //关闭前页面是设置
        if(stateSet){
            getSupportFragmentManager().beginTransaction().add(R.id.home_container,new SetFragment()).commit();
        }

        imageMe = (ImageView) findViewById(R.id.home_tab_me);
        imageTeach = (ImageView) findViewById(R.id.home_tab_teach);
        imageSet = (ImageView) findViewById(R.id.home_tab_set);

        imageMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, new MeFragment()).commit();
                editor.putBoolean("stateMe",true);
                editor.putBoolean("stateTeach",false);
                editor.putBoolean("stateSet",false);
                editor.commit();
            }
        });

        imageTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, new TeachFragment()).commit();
                editor.putBoolean("stateMe",false);
                editor.putBoolean("stateTeach",true);
                editor.putBoolean("stateSet",false);
                editor.commit();
            }
        });

        imageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_container, new SetFragment()).commit();
                editor.putBoolean("stateMe",false);
                editor.putBoolean("stateTeach",false);
                editor.putBoolean("stateSet",true);
                editor.commit();
            }
        });
    }
}
