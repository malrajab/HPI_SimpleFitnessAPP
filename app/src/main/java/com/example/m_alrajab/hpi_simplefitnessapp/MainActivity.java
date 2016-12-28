package com.example.m_alrajab.hpi_simplefitnessapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m_alrajab.hpi_simplefitnessapp.model.DBHelper;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.User;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;

import static com.example.m_alrajab.hpi_simplefitnessapp.utils.Utils.setStethoWatch;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private UserLocalDetails userdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mContext=this;

       // getContentResolver().
      //  if(!isNetworkAvailable(mContext))
      //     Snackbar.make(findViewById(R.id.activity_main),"No network is available", Snackbar.LENGTH_INDEFINITE).show();
        setStethoWatch(this);


        Button regButton = (Button) findViewById(R.id.btn_register_MA);
        regButton.setOnClickListener(new View.OnClickListener() {
            //Registration page re-direction
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
            }
        });

        final EditText password = (EditText) findViewById(R.id.editText_pass_MA2);
        final EditText username = (EditText) findViewById(R.id.editText_name_MA1);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //On keyboard action , authenticate and login
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                DBHelper db = new DBHelper(MainActivity.this);
                Cursor cur = db.getRecForUsername(username.getText().toString());
                if( cur != null && cur.moveToFirst())
                {
                    //Authentication based on password and re-direct to landing page if password id valid
                    if(cur.getString(cur.getColumnIndex("password")).equals(password.getText().toString())){
                        userdetails = new UserLocalDetails(MainActivity.this);
                        userdetails.storeUserData(new User(username.getText().toString(), password.getText().toString()));
                        userdetails.setUserLoggedIn(true);
                        userdetails.storeHistory(MainActivity.this, "Logged in");
                        Intent landingpage = new Intent(MainActivity.this, LandingScreen.class);
                        landingpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(landingpage);
                        return false;
                    }
                    else
                    {
                        password.setError("Incorrect Password.");
                        return true;
                    }
                }
                else
                {
                    username.setError("User name does not exist, please register.");
                    return true;
                }
            }
        });

        Button forgotPass = (Button) findViewById(R.id.btn_forgetpassword_MA);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            //Forgot password page re-direction
            @Override
            public void onClick(View v) {
                Intent frgtPass = new Intent(MainActivity.this,ForgotPasswordScreen.class);
                startActivity(frgtPass);
            }
        });
    }

}
