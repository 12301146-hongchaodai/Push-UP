package com.android.push_up.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.GuideActivity;
import com.android.push_up.guide.R;
import com.android.push_up.ip.IP;
import com.android.push_up.start.StartActivity;

public class LoginActivity extends Activity {

    private EditText loginName;
    private EditText loginPassword;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SysApplication.getInstance().addActivity(this);

        loginName = (EditText) findViewById(R.id.loginName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        sharedPreferences = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);


        findViewById(R.id.btnLoginPushUp).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(0xFF000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    findViewById(R.id.btnLoginPushUp).setBackgroundColor(0x96000000);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    findViewById(R.id.btnLoginPushUp).setBackgroundColor(0x96000000);
                    //检测网络状态
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo == null){
                        Toast.makeText(LoginActivity.this, "未接入网络，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String username = loginName.getText().toString();
                        String password = loginPassword.getText().toString();
                        if(username == null || password == null || username.equals("") || password.equals("")){
                            new AlertDialog.Builder(LoginActivity.this).setMessage("用户名或密码不能为空").setPositiveButton("确认",null).show();
                        }
                        else{
                            String responseInfo = LoginUtil.login(username,password,"http://" + IP.getIP() + ":8888/upload_file_service/LoginServlet");
                            if(responseInfo.equals("success")){//登录成功
                                startActivity(new Intent(LoginActivity.this, GuideActivity.class));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("loginState",true);
                                editor.putString("username",username);
                                editor.commit();
                                LoginActivity.this.finish();
                                StartActivity.instance.finish();
                            }
                            else if(responseInfo.equals("fail")){
                                new AlertDialog.Builder(LoginActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if(keyCode == KeyEvent.KEYCODE_BACK){
                                            dialog.dismiss();
                                            return true;
                                        }
                                        else{
                                            return false;
                                        }
                                    }
                                }).setMessage("用户名或密码错误，请重新输入").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        LoginActivity.this.finish();
                                    }
                                }).show();
                            }
                            else if(responseInfo.equals("error")){
                                Toast.makeText(LoginActivity.this,"无法连接服务器，请稍后再试",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    return false;
                }
                return false;
            }
        });
    }
}
