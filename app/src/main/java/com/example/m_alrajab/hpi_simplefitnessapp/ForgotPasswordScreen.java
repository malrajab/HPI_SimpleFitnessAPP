package com.example.m_alrajab.hpi_simplefitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.m_alrajab.hpi_simplefitnessapp.model.DBHelper;

public class ForgotPasswordScreen extends AppCompatActivity {
    public static final String EXTRA_USERNAME="USERNAME_EXTRA";

    private EditText mUserNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button fgtBtn = (Button) findViewById(R.id.btn_fgt);
        mUserNameEditText = (EditText) findViewById(R.id.uName);
        final EditText mEmailEditText = (EditText) findViewById(R.id.email);
        fgtBtn.setOnClickListener(new View.OnClickListener() {
            //Gets back to login activity
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ForgotPasswordScreen.this);
                Cursor cur = db.getRecForUsername(mUserNameEditText.getText().toString().trim());
                Log.v("my outc",cur.toString());
                if(cur.moveToFirst()){
                    Intent fgtScreen = new Intent(ForgotPasswordScreen.this, ForgetScreen.class);
                    fgtScreen.putExtra(EXTRA_USERNAME, mUserNameEditText.getText().toString());
                    fgtScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(fgtScreen);
                }
                else
                {
                    mUserNameEditText.setError("Username and Email did not match");
                    mEmailEditText.setError("Username and Email did not match");
                }
            }
        });
    }

}
