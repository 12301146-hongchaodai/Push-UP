package com.android.push_up.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;
import com.android.push_up.ip.IP;

public class RegisterActivity extends Activity {

    private EditText registerName;
    private EditText registerPassword;
    private EditText registerConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SysApplication.getInstance().addActivity(this);
        registerName = (EditText) findViewById(R.id.registerName);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerConfirm = (EditText) findViewById(R.id.registerConfirm);

        findViewById(R.id.btnRegisterPushUp).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(0xFF000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    findViewById(R.id.btnRegisterPushUp).setBackgroundColor(0x96000000);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    findViewById(R.id.btnRegisterPushUp).setBackgroundColor(0x96000000);

                    //检测网络状态
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo == null){
                        Toast.makeText(RegisterActivity.this, "未接入网络，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String username = registerName.getText().toString();
                        String password = registerPassword.getText().toString();
                        String confirm = registerConfirm.getText().toString();
                        if(username == null || username.equals("") || password == null || password.equals("") || confirm==null || confirm.equals("")){
                            new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("用户名或密码不能为空").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                        else{
                            if(!password.equals(confirm)){//两次密码不一样
                                new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("两次密码输入不一样，请重新输入").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                            else{
                                //数据提交到服务器，等待响应
                                String responseInfo = RegisterUtil.register(username,password,"http://" + IP.getIP() + ":8888/upload_file_service/RegisterServlet");
                                if(responseInfo.equals("success")){
                                    new AlertDialog.Builder(RegisterActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
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
                                    }).setTitle("提示").setMessage("注册成功，是否前往登录界面？").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                            RegisterActivity.this.finish();
                                        }
                                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            RegisterActivity.this.finish();
                                        }
                                    }).show();
                                }
                                else if(responseInfo.equals("repeat")){
                                    new AlertDialog.Builder(RegisterActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
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
                                    }).setTitle("提示").setMessage("该用户名已被注册，请更换用户名重新注册").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            RegisterActivity.this.finish();
                                        }
                                    }).show();
                                }
                                else if(responseInfo.equals("fail")){
                                    new AlertDialog.Builder(RegisterActivity.this).setOnKeyListener(new DialogInterface.OnKeyListener() {
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
                                    }).setTitle("提示").setMessage("注册失败，是否重新注册？").setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            RegisterActivity.this.finish();
                                        }
                                    }).show();
                                }
                                else if(responseInfo.equals("error")){
                                    Toast.makeText(RegisterActivity.this,"无法连接服务器，请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
        });
    }
}
