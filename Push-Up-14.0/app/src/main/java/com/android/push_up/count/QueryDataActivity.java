package com.android.push_up.count;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.push_up.guide.R;

import java.util.ArrayList;
import java.util.List;

public class QueryDataActivity extends ActionBarActivity {

    private List<String> usernameList = new ArrayList<String>(3);
    private List<String> timeList = new ArrayList<String>(3);
    private List<Integer> countList = new ArrayList<Integer>(3);
    private TextView firstName,firstTime,firstRecord,secondName,secondTime,secondRecord,thirdName,thirdTime,thirdRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_data);
        firstName = (TextView) findViewById(R.id.firstName);
        firstTime = (TextView) findViewById(R.id.firstTime);
        firstRecord = (TextView) findViewById(R.id.firstRecord);

        secondName = (TextView) findViewById(R.id.secondName);
        secondTime = (TextView) findViewById(R.id.secondTime);
        secondRecord = (TextView) findViewById(R.id.secondRecord);

        thirdName = (TextView) findViewById(R.id.thirdName);
        thirdTime = (TextView) findViewById(R.id.thirdTime);
        thirdRecord = (TextView) findViewById(R.id.thirdRecord);



        DataToSQLite dataToSQLite = new DataToSQLite(QueryDataActivity.this);

        SQLiteDatabase dbRead = dataToSQLite.getReadableDatabase();
        Cursor cursor = dbRead.query(
                "data_count",
                new String[]{"username","time","count"},
                null,
                null,
                null,
                null,
                "count desc",
                "3");
        while(cursor.moveToNext()){
            String dbUsername = cursor.getString(cursor.getColumnIndex("username"));
            String dbTime = cursor.getString(cursor.getColumnIndex("time"));
            int dbCount = cursor.getInt(cursor.getColumnIndex("count"));
            usernameList.add(dbUsername);
            timeList.add(dbTime);
            countList.add(dbCount);
        }
        if(usernameList.size() == 0){

        }
        else if(usernameList.size() == 1){
            //显示前三条数据
            firstName.setText(usernameList.get(0));
            firstTime.setText(timeList.get(0));
            firstRecord.setText(countList.get(0).toString());
        }
        else if(usernameList.size() == 2){
            firstName.setText(usernameList.get(0));
            firstTime.setText(timeList.get(0));
            firstRecord.setText(countList.get(0).toString());

            secondName.setText(usernameList.get(1));
            secondTime.setText(timeList.get(1));
            secondRecord.setText(countList.get(1).toString());
        }
        else if(usernameList.size() == 3){
            firstName.setText(usernameList.get(0));
            firstTime.setText(timeList.get(0));
            firstRecord.setText(countList.get(0).toString());

            secondName.setText(usernameList.get(1));
            secondTime.setText(timeList.get(1));
            secondRecord.setText(countList.get(1).toString());

            thirdName.setText(usernameList.get(2));
            thirdTime.setText(timeList.get(2));
            thirdRecord.setText(countList.get(2).toString());
        }


        dbRead.close();
    }
}
