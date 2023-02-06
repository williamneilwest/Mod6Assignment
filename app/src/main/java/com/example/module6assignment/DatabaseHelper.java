package com.example.module6assignment;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "email";
    private static final String COL3 = "pwd";



    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT, " + COL3 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String email, String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, email);
        contentValues.put(COL3, pwd);

        Log.d(TAG, "addData: Adding " + email + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    @SuppressLint("Range")
    public String getData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT pwd FROM " + TABLE_NAME + " WHERE email = '" + email + "'";
        Log.d(TAG,"getting "+email+" from list");
        Cursor data = db.rawQuery(query, null);
        String num = null;
        if( data != null && data.moveToFirst() ){
            num = data.getString(data.getColumnIndex("pwd"));
            data.close();
        }
        else {
            Log.d(TAG,"Something went wrong...");
        }
        return num;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COL2 + " = ?",
                new String[] { id });
        return rowsDeleted > 0;
    }
}
