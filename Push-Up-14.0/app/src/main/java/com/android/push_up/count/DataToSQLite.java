package com.android.push_up.count;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 周宇环 on 2015/6/3.
 */
public class DataToSQLite extends SQLiteOpenHelper {
    public DataToSQLite(Context context) {
        super(context, "PushUp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table data_count(_id integer primary key autoincrement,username varchar(20),time varchar(20),count int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
