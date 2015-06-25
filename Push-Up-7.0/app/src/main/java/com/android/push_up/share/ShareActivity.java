package com.android.push_up.share;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.android.push_up.guide.R;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ShareActivity extends ActionBarActivity {

    private List<Map<String, Object>> mapList = null;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        gridView = (GridView) findViewById(R.id.girdView);
        mapList = FileList.findFile("sdcard/Push-Up/Image/");
        ImageAdapter imageAdapter = new ImageAdapter(ShareActivity.this,mapList);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
