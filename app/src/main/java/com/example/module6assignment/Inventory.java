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
        populateListView();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String data = item.getText().toString();
                if (item.length() != 0) {
                    mDatabaseHelper.addItem(item.getText().toString());
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
                if (data.length() != 0) {
                    mDatabaseHelper.deleteItem(data);
                    if(mDatabaseHelper.getItemAmount(data).equals(0)){
                     mDatabaseHelper.removeItem(data);
                     Log.d("TAG","Deleting entry...");
                    }
                    populateListView();

                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });


    }

    private void populateListView() {
        Cursor data = mDatabaseHelper.getListData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            String entry = data.getString(1) + " Count: " + mDatabaseHelper.getItemAmount(data.getString(1));
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