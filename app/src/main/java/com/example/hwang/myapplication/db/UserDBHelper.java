package com.example.hwang.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by hwang on 2018-01-27.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    private static UserDBHelper dbHelper;
    private static final String DATABASE_CREATE =
            "create table gr_user(" +
                    "_id integer primary key autoincrement, " +
                    "id text not null, " +
                    "name text not null, " +
                    "pwd text not null);";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="data";
    public static  final String DATABASE_TABLE = "gr_user";

    SQLiteDatabase db;
    Cursor cursor;

    private UserDBHelper(Context context) {
     super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static UserDBHelper getInstance(Context context){
        if (dbHelper == null) {
            dbHelper = new UserDBHelper(context);
        }

        return dbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
        } catch (Exception e) {
            Log.e(TAG,"Exception in DATABASE_CREATE_SQL",e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        String sql = "drop table if exists gr_user"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성

    }
}
