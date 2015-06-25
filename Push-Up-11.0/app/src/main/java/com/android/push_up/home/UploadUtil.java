package com.android.push_up.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by 周宇环 on 2015/6/9.
 */
public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;//超时
    private static final String CHARSET = "utf-8";//编码

    public static void uploadFile(File file,Context context,String requestUrl){
        int res = 0;
        String BOUNDARY = UUID.randomUUID().toString();//边界标志，用于分割表单中的不同数据
        String PREFIX = "--",LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";//请求内容类型

        try{
            //创建URL对象
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(TIME_OUT);
            httpURLConnection.setConnectTimeout(TIME_OUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            //设置请求头属性
            httpURLConnection.setRequestProperty("Charset", CHARSET);
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");//维持长连接
            httpURLConnection.setRequestProperty("Content-Type",CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                //匹配上传数据格式
                //获取输出流，隐式执行connect操作，故不需要connect
                DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */
                //数据描述
                sb.append("Content-Disposition: form-data; name=\"" + file.getName() + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                Log.e(TAG, sb.toString());
                dos.write(sb.toString().getBytes());
                //文件流
                InputStream is = new FileInputStream(file);
                //每次写入8192字节
                byte[] bytes = new byte[8192];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {//到文件末尾返回-1，读取的字节数
                    dos.write(bytes, 0, len);//将bytes数组从偏移量0开始的len个字节写入数据流
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();//流末尾字符串
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                //获取响应码
                InputStream isOut = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(isOut, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String result = br.readLine();
                res = httpURLConnection.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    Log.e(TAG, "request success");
                    if(result.equals("success")){
                        Toast.makeText(context, "数据备份成功", Toast.LENGTH_LONG).show();
                    }
                    else if(result.equals("fail")){
                        Toast.makeText(context, "数据备份失败", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Log.e(TAG, "request error");
                    Toast.makeText(context,"数据备份失败",Toast.LENGTH_SHORT).show();
                }
                dos.close();
                isOut.close();
            }
        } catch (MalformedURLException e) {
            Toast.makeText(context,"数据备份失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context,"数据备份失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
