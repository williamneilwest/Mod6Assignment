package com.example.module6assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSIONS_CODE = 1;
    private final int REQUEST_WRITE_CODE = 0;
    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    private EditText txtEmail, txtPass;
    private Button btnLogin, btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSMSPermission(Manifest.permission.SEND_SMS, SMS_PERMISSIONS_CODE);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtEmail = (EditText) findViewById(R.id.userEmail);
        txtPass = (EditText) findViewById(R.id.userPassword);
        mDatabaseHelper = new DatabaseHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email = txtEmail.getText().toString();
                String pwd = txtEmail.getText().toString();
                if (txtEmail.length() != 0) {
                    AddData(email, pwd);
                    txtPass.setText("");
                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });








    }





    public void checkSMSPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    public void AddData(String email, String pwd){
        boolean insertData = mDatabaseHelper.addData(email, pwd);
        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        }
        else {
            toastMessage("Something went wrong!");
        }
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
}