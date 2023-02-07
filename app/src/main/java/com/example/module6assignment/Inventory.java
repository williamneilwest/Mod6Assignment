package com.example.module6assignment;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        EditText item = (EditText) findViewById(R.id.invItem);
        mDatabaseHelper = new DatabaseHelper(this, "inventory_table");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String data = item.getText().toString();
                if (item.length() != 0) {
                    mDatabaseHelper.addItem(item.getText().toString());
                    toastMessage(mDatabaseHelper.getItemAmount(item.getText().toString()).toString());
                    populateListView();

                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String data = item.getText().toString();
                if (item.length() != 0) {
                    mDatabaseHelper.deleteItem(item.getText().toString());
                    toastMessage(mDatabaseHelper.getItemAmount(item.getText().toString()).toString());

                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });


    }

    private void populateListView() {
        Cursor data = mDatabaseHelper.getListData();
        Log.d("TAG", data.toString());
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            String entry = data.getString(1) + "Count: " + mDatabaseHelper.getItemAmount(data.getString(1));
            listData.add(entry);
        }
        Log.d("TAG", listData.toString());

        @SuppressLint("ResourceType") ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listData.sort(String.CASE_INSENSITIVE_ORDER);
        ListView mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
}