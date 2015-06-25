package com.android.push_up.home;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new MeFragment()).commit();


        imageMe = (ImageView) findViewById(R.id.home_tab_me);
        imageTeach = (ImageView) findViewById(R.id.home_tab_teach);
        imageSet = (ImageView) findViewById(R.id.home_tab_set);

        imageMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MeFragment()).commit();
            }
        });

        imageTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeachFragment()).commit();
            }
        });

        imageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SetFragment()).commit();
            }
        });
    }
}
