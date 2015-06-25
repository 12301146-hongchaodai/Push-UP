package com.android.push_up.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.GuideActivity;
import com.android.push_up.guide.R;
import com.android.push_up.login_register.LoginActivity;
import com.android.push_up.login_register.RegisterActivity;


public class StartActivity extends Activity {

    private SharedPreferences sharedPreferences;
    public static Activity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SysApplication.getInstance().addActivity(this);
        instance = this;
        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("loginState",false)){
            startActivity(new Intent(StartActivity.this, GuideActivity.class));
            StartActivity.this.finish();
        }
        else{

            findViewById(R.id.btnLogin).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        v.setBackgroundColor(0xFF000000);
                        return true;
                    }
                    else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                        findViewById(R.id.btnLogin).setBackgroundColor(0x96000000);
                        return false;
                    }
                    else if(event.getAction() == MotionEvent.ACTION_UP){
                        findViewById(R.id.btnLogin).setBackgroundColor(0x96000000);
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        return false;
                    }
                    return false;
                }
            });

            findViewById(R.id.btnRegister).setOnTouchListener(new View.OnTouchListener() {
                Drawable background = findViewById(R.id.btnRegister).getBackground();
                @Override

                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundColor(0x20000000);
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        findViewById(R.id.btnRegister).setBackgroundColor(Color.parseColor("#ffff"));
                        return false;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        findViewById(R.id.btnRegister).setBackgroundDrawable(background);
                        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                        return false;
                    }
                    return false;
                }
            });
        }
    }
}
