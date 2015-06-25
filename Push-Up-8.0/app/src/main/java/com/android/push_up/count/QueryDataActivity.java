package com.android.push_up.count;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.push_up.guide.R;

public class QueryDataActivity extends ActionBarActivity {

    private SimpleCursorAdapter simpleCursorAdapter;
    private ListView listView;

    @Override
    @TargetApi(11)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_data);

        DataToSQLite dataToSQLite = new DataToSQLite(QueryDataActivity.this);
        listView = (ListView) findViewById(R.id.listData);

        SQLiteDatabase dbRead = dataToSQLite.getReadableDatabase();
        Cursor cursor = dbRead.query("count",null,null,null,null,null,null);

            simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.querydatalist,
                    cursor,
                    new String[]{"username","time","count"},
                    new int[]{R.id.queryDataTextView1,R.id.queryDataTextView2,R.id.queryDataTextView3},
                    0
            );
        listView.setAdapter(simpleCursorAdapter);
        dbRead.close();
    }
}
