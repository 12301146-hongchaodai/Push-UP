package com.android.push_up.share;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 周宇环 on 2015/6/2.
 */
public class FileList {
    private static List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    //将path路径下的JPG和PNG图片绝对路径以键值对的形式添加到List里面
    public static List<Map<String, Object>> findFile(String path){
        mapList.clear();
        File file = new File(path);
        File[] files = file.listFiles();
        if(files != null){
            for(int i = 0; i < files.length; i++){
                if(files[i].isDirectory()){
                    findFile(files[i].getAbsolutePath());
                }else{
                    Map<String, Object> map = new HashMap<String, Object>();
                    String fName = files[i].getAbsolutePath();
                    String ext = fName.substring(fName.lastIndexOf("."),fName.length());
                    if(ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png")){
                        map.put("fName",fName);
                        mapList.add(map);
                    }
                }
            }
        }
        return mapList;
    }
}
