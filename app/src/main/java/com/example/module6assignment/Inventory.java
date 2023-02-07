package com.example.module6assignment;

import androidx.appcompat.app.AppCompatActivity;


import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inventory extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;



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
                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });



    }
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
}