package com.android.push_up.home;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周宇环 on 2015/6/9.
 */
public class FindFiles {
    public static List<File> fileList = new ArrayList<File>();
    public static List<File> findFilesFromSD(String filePath){
        fileList.clear();
        File file = new File(filePath);
        File[] files = file.listFiles();
        if(files!=null){
            for(int i = 0; i < files.length ; i++){
                fileList.add(files[i]);
            }
        }
        return fileList;
    }
}
