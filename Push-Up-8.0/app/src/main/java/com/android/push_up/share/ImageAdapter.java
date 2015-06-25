package com.android.push_up.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.push_up.guide.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.graphics.Bitmap.Config.*;

/**
 * Created by 周宇环 on 2015/6/2.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> mapList;
    private boolean gridViewItemState[];

    public ImageAdapter(Context context,List<Map<String, Object>> mapList){
        this.context = context;
        this.mapList = mapList;
        if(this.mapList == null){
            this.mapList = new ArrayList<Map<String, Object>>();
        }
    }


    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);//缩放方式
        iv.setLayoutParams(new GridView.LayoutParams(100,100));//调整布局，指定图片以100 * 100显示
        Map<String,Object> map = mapList.get(position);
        String fName = (String) map.get("fName");
        BitmapFactory.Options opt = new BitmapFactory.Options();//以何种方式加载显示图片
        opt.inPreferredConfig = RGB_565;//解码配置信息
        opt.inPurgeable=true;//软引用机制
        opt.inSampleSize=5;
        opt.inInputShareable=true;//是否复制inPurgeable对象引用
        File file = new File(fName);
        try{
            FileInputStream fis=new FileInputStream(file);
            BufferedInputStream bis=new BufferedInputStream(fis);

            Bitmap bm=BitmapFactory.decodeStream(bis,null,opt);//将一个输入流解码成位图
            Bitmap tbu= ThumbnailUtils.extractThumbnail(bm, 100, 100);//创建指定大小的缩略图
            iv.setImageBitmap(tbu);
            bis.close();
            if(!bm.isRecycled()){//位图没有回收
                bm.recycle();//回收
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return iv;
    }
}
