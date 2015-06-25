package com.android.push_up.count;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QueryDataActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private String QueryTime = "2015/6/25";
    private List<String> usernameList;
    private List<String> timeList;
    private List<Integer> countList;
    private TextView firstName,firstTime,firstRecord,secondName,secondTime,secondRecord,thirdName,thirdTime,thirdRecord,forthName,forthTime,forthRecord,fifthName,fifthTime,fifthRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_data);
        SysApplication.getInstance().addActivity(this);

        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        usernameList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        countList = new ArrayList<Integer>();

        firstName = (TextView) findViewById(R.id.firstName);
        firstTime = (TextView) findViewById(R.id.firstTime);
        firstRecord = (TextView) findViewById(R.id.firstRecord);

        secondName = (TextView) findViewById(R.id.secondName);
        secondTime = (TextView) findViewById(R.id.secondTime);
        secondRecord = (TextView) findViewById(R.id.secondRecord);

        thirdName = (TextView) findViewById(R.id.thirdName);
        thirdTime = (TextView) findViewById(R.id.thirdTime);
        thirdRecord = (TextView) findViewById(R.id.thirdRecord);

        forthName = (TextView) findViewById(R.id.forthName);
        forthTime = (TextView) findViewById(R.id.forthTime);
        forthRecord = (TextView) findViewById(R.id.forthRecord);

        fifthName = (TextView) findViewById(R.id.fifthName);
        fifthTime = (TextView) findViewById(R.id.fifthTime);
        fifthRecord = (TextView) findViewById(R.id.fifthRecord);

        System.out.println(QueryTime);
        Log.e("Time",QueryTime);
        DataToSQLite dataToSQLite = new DataToSQLite(QueryDataActivity.this);

        SQLiteDatabase dbRead = dataToSQLite.getReadableDatabase();
        Cursor cursor = dbRead.query(
                "data_count",
                new String[]{"username","time","count"},
                "username=?",
                new String[]{sharedPreferences.getString("username",null)},
                null,
                null,
                null,
                "5");
        while(cursor.moveToNext()){
            String dbUsername = cursor.getString(cursor.getColumnIndex("username"));
            String dbTime = cursor.getString(cursor.getColumnIndex("time"));
            int dbCount = cursor.getInt(cursor.getColumnIndex("count"));
            usernameList.add(dbUsername);
            timeList.add(dbTime);
            countList.add(dbCount);
        }
        sharedPreferences = getSharedPreferences("alert_plan", Context.MODE_PRIVATE);
        int planLevel = sharedPreferences.getInt("planLevel",0);
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(planLevel == 0){
            new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("您还没有制定健身计划").setPositiveButton("确认",null).show();
        }
        else if(planLevel == 1){
            if(week == 1){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 5 && countList.get(1) >= 6 && countList.get(2) >= 5 && countList.get(3) >= 5 && countList.get(4) >= 6){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 3){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 6 && countList.get(1) >= 8 && countList.get(2) >= 6 && countList.get(3) >= 6 && countList.get(4) >= 7){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 5){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 7 && countList.get(1) >= 10 && countList.get(2) >= 7 && countList.get(3) >= 7 && countList.get(4) >= 9){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else{
                new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天休息").setPositiveButton("确认",null).show();
            }
        }
        else if(planLevel == 2){
            if(week == 1){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 13 && countList.get(1) >= 14 && countList.get(2) >= 11 && countList.get(3) >= 11 && countList.get(4) >= 13){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 3){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 14 && countList.get(1) >= 15 && countList.get(2) >= 12 && countList.get(3) >= 12 && countList.get(4) >= 14){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 5){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 15 && countList.get(1) >= 16 && countList.get(2) >= 13 && countList.get(3) >= 13 && countList.get(4) >= 15){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else{
                new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天休息").setPositiveButton("确认",null).show();
            }
        }
        else if(planLevel == 3){
            if(week == 1){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 19 && countList.get(1) >= 20 && countList.get(2) >= 18 && countList.get(3) >= 18 && countList.get(4) >= 19){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 3){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 20 && countList.get(1) >= 21 && countList.get(2) >= 20 && countList.get(3) >= 20 && countList.get(4) >= 20){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else if(week == 5){
                if(countList.size() < 5){
                    new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                }
                else{
                    if(countList.get(0) >= 21 && countList.get(1) >= 23 && countList.get(2) >= 20 && countList.get(3) >= 20 && countList.get(4) >= 22){
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划完成了").setPositiveButton("确认",null).show();
                    }
                    else{
                        new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天训练计划没有完成").setPositiveButton("确认",null).show();
                    }
                }
            }
            else{
                new AlertDialog.Builder(QueryDataActivity.this).setTitle("提示").setMessage("今天休息").setPositiveButton("确认",null).show();
            }
        }

        if(usernameList.size() == 0){
            //默认不处理，页面将不显示内容
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
        else if(usernameList.size() == 4){
            firstName.setText(usernameList.get(0));
            firstTime.setText(timeList.get(0));
            firstRecord.setText(countList.get(0).toString());

            secondName.setText(usernameList.get(1));
            secondTime.setText(timeList.get(1));
            secondRecord.setText(countList.get(1).toString());

            thirdName.setText(usernameList.get(2));
            thirdTime.setText(timeList.get(2));
            thirdRecord.setText(countList.get(2).toString());

            forthName.setText(usernameList.get(3));
            forthTime.setText(timeList.get(3));
            forthRecord.setText(countList.get(3).toString());
        }
        else if(usernameList.size() == 5){
            firstName.setText(usernameList.get(0));
            firstTime.setText(timeList.get(0));
            firstRecord.setText(countList.get(0).toString());

            secondName.setText(usernameList.get(1));
            secondTime.setText(timeList.get(1));
            secondRecord.setText(countList.get(1).toString());

            thirdName.setText(usernameList.get(2));
            thirdTime.setText(timeList.get(2));
            thirdRecord.setText(countList.get(2).toString());

            forthName.setText(usernameList.get(3));
            forthTime.setText(timeList.get(3));
            forthRecord.setText(countList.get(3).toString());

            fifthName.setText(usernameList.get(4));
            fifthTime.setText(timeList.get(4));
            fifthRecord.setText(countList.get(4).toString());
        }


        dbRead.close();
    }
}
