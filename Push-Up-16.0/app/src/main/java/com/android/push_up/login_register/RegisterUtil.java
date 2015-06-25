package com.android.push_up.login_register;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周宇环 on 2015/6/17.
 */
public class RegisterUtil {

    private static final int TIME_OUT = 10 * 1000;

    public static String register(String username,String password,String requestUrl){
        String responseInfo = "";

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(TIME_OUT);
            httpURLConnection.setConnectTimeout(TIME_OUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);

            String content = "username=" + username + "&password=" + password;
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            dos.writeBytes(content);
            dos.flush();
            dos.close();
            InputStream isOut = httpURLConnection.getInputStream();//实际发送请求,同时也执行了获取服务器端响应任务
            InputStreamReader isr = new InputStreamReader(isOut, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            isOut.close();
            int res = httpURLConnection.getResponseCode();
            Log.e("Register", "response code:" + res);
            if (res == 200) {
                Log.e("Register", "request success");
                if(result.equals("success")){
                    responseInfo = "success";
                }
                else if(result.equals("repeat")){
                    responseInfo = "repeat";
                }
                else if(result.equals("fail")){
                    responseInfo = "fail";
                }

            } else {
                responseInfo = "fail";
                Log.e("Register", "request error");
            }

        } catch (MalformedURLException e) {
            responseInfo = "error";
            e.printStackTrace();
        } catch (IOException e) {
            responseInfo = "error";
            e.printStackTrace();
        }
        Log.e("responseInfo",responseInfo);

        return responseInfo;
    }
}
