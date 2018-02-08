package com.example.hwang.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by hwang on 2018-02-03.
 */

public class UserDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD ="pwd";

    private final Context context;

    private UserDBHelper dbHelper;
    private SQLiteDatabase myDB;

    public UserDB(Context context) {
        this.context = context;
    }

    public UserDB open() {
        //데이터베이스를 만들고
        dbHelper = dbHelper.getInstance(context);
        // myDB => SQLite 데이터 베이스
        myDB = dbHelper.getWritableDatabase();
        return this;
    }

    public long insertUser(String userId, String userName, String userPwd) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userId);
        values.put(KEY_NAME, userName);
        values.put(KEY_PASSWORD, userPwd);

        return myDB.insert(dbHelper.DATABASE_TABLE, null, values);
    }

    //전체 데이터 가져오는거
    public Cursor selectAllUsers() {
        return myDB.query(
                dbHelper.DATABASE_TABLE,
                new String[]{KEY_ROWID, KEY_ID, KEY_NAME, KEY_PASSWORD},
                null, null, null, null, null);
    }

    public Cursor selectUser(String userId,String userPwd){
        Cursor cursor = myDB.query(
                dbHelper.DATABASE_TABLE,
                new String[]{KEY_ROWID, KEY_ID, KEY_NAME, KEY_PASSWORD},
                KEY_ID + "=" + "'"+ userId + "'"+" AND " + KEY_PASSWORD + "=" + "'"  + userPwd + "'",
                null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor selectUserId(String userId){
        Cursor cursor = myDB.query(
                dbHelper.DATABASE_TABLE,
                new String[]{KEY_ROWID, KEY_ID, KEY_NAME, KEY_PASSWORD},
                KEY_ID + "=" + "'" + userId + "'",
                null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean deleteUser(String userId){
        return myDB.delete(
                dbHelper.DATABASE_TABLE,
                KEY_ID + "=" + "'"+  userId + "'", null) > 0;
    }
/*
    public Cursor fetchTitles(String title){
        Cursor cursor = myDB.query(
                dbHelper.DATABASE_TABLE,
                new String[]{KEY_ROWID, KEY_TITLE, KEY_BODY},
                KEY_TITLE + "LIKE ?",
                new String [] {"%" + title + "%"},
                null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean updateNote(long rowId, String title, String body){
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_BODY, body);

        return myDB.update(
                dbHelper.DATABASE_TABLE,
                values,
                KEY_ROWID + "=" + rowId, null) > 0;

    }

    */
}
