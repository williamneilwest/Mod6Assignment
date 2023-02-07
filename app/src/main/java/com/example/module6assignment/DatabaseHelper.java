package com.example.module6assignment;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String INVENTORY_TABLE = "inventory_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "email";
    private static final String COL3 = "pwd";
    private static final String INV_COL1 = "ID";
    private static final String INV_COL2 = "name";
    private static final String INV_COL3 = "amount";




    public DatabaseHelper(@Nullable Context context, String db) {
        super(context, db, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT, " + COL3 + " TEXT)";
        String createTable2 = "CREATE TABLE " + INVENTORY_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + INV_COL2 +" TEXT, " + INV_COL3 + " INTEGER)";
        db.execSQL(createTable);
        db.execSQL(createTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE);
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

    public Cursor getListData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM inventory_table";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COL2 + " = ?",
                new String[] { id });
        return rowsDeleted > 0;
    }

    @SuppressLint("Range")
    public Integer  getItemAmount(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT amount FROM " + INVENTORY_TABLE + " WHERE name = '" + item + "'";
        Cursor data = db.rawQuery(query,null);
        Integer num = null;
        if( data != null && data.moveToFirst() ){
            num = data.getInt(data.getColumnIndex("amount"));
            data.close();
        }
        else {

            Log.d(TAG,"Something went wrong...");
        }
        Log.d(TAG,"Amount: "+num);
        return num;
    }

    public boolean addItem(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT amount FROM inventory_table WHERE name = '" + item + "'";
        Cursor data = db.rawQuery(Query,null);
        if( data != null && data.moveToFirst() ){
            String Query2 = "UPDATE inventory_table SET amount = amount + 1 WHERE name= '" + item + "'";
            db.execSQL(Query2);
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INV_COL2, item);
            contentValues.put(INV_COL3, 1);
            db.insert(INVENTORY_TABLE,null,contentValues);
            Log.d(TAG,"Something went wrong or added...");
        }
        Integer result = -1;
        if(result == -1) {
            return false;
        }
        else {

            return true;
        }
    }

    public void deleteItem(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT amount FROM inventory_table WHERE name = '" + item + "'";
        Cursor data = db.rawQuery(Query,null);
        if( data != null && data.moveToFirst() ){
            String Query2 = "UPDATE inventory_table SET amount = amount - 1 WHERE name= '" + item + "'";
            db.execSQL(Query2);
        }
        else{
            Log.d(TAG,"Something went wrong or none left...");

        }
    }

}
