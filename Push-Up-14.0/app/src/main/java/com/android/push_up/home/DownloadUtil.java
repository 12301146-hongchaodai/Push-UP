package com.android.push_up.home;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周宇环 on 2015/6/12.
 */
public class DownloadUtil {

    private static final int TIME_OUT = 10 * 1000;//超时

    public static void downloadFile(Context context,String sdPath,String requestUrl){
        try {
            File file = new File(sdPath);
            if(!file.exists()){
                file.mkdirs();
            }

            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(TIME_OUT);
            httpURLConnection.setConnectTimeout(TIME_OUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);

            //声明输出流,需要获取服务器响应流的

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context,"数据恢复失败",Toast.LENGTH_LONG);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"数据恢复失败",Toast.LENGTH_LONG);
        }
    }
}
