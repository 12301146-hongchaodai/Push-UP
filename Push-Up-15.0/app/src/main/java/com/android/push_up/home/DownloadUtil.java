package com.android.push_up.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 周宇环 on 2015/6/12.
 */
public class DownloadUtil {

    private static final int TIME_OUT = 30 * 1000;//超时

    public static void downloadFile(Context context,String sdPath,String fileName,String requestUrl){
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

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.flush();
            dos.close();
            //执行发送请求，获取响应
            InputStream inputStream = httpURLConnection.getInputStream();
            //将响应流写入SD卡
            int res = httpURLConnection.getResponseCode();
            Log.e("downloadFile","response code:" + String.valueOf(res));
            if(res == 200){
                //写入数据
                FileOutputStream fos = new FileOutputStream(sdPath + "/"+ fileName +".zip");
                byte[] buffer = new byte[1024]; // 每次读8K字节
                int count = 0;
                // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                while ((count = inputStream.read(buffer)) > 0){
                    fos.write(buffer, 0, count); // 向服务端文件写入字节流
                }
                fos.close(); // 关闭FileOutputStream对象
                inputStream.close();
                Toast.makeText(context,"数据恢复成功",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"数据恢复失败",Toast.LENGTH_LONG).show();
            }

            //声明输出流,需要获取服务器响应流的

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context,"数据恢复失败",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"数据恢复失败",Toast.LENGTH_LONG).show();
        }
    }
}
