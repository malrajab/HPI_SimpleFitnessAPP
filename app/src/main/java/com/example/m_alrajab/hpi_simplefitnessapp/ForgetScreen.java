package com.example.m_alrajab.hpi_simplefitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.m_alrajab.hpi_simplefitnessapp.model.DBHelper;
import com.example.m_alrajab.hpi_simplefitnessapp.model.DbContract.FeedEntry;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.User;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;

public class ForgetScreen extends AppCompatActivity {
    private UserLocalDetails mUserLocalDetails;

    private EditText mUserNameEditText;
    private EditText mPasswordEditText1;
    private EditText mPasswordEditText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mUserLocalDetails   =               new UserLocalDetails(this);
        mUserNameEditText   = (EditText) findViewById(R.id.upp_uname);
        mPasswordEditText1  = (EditText) findViewById(R.id.upp_pass1);
        mPasswordEditText2  = (EditText) findViewById(R.id.upp_pass2);



        //Fetch previous values
        final DBHelper db = new DBHelper(ForgetScreen.this);
        final Cursor cur = db.getRecForUsername(getIntent().getExtras().getString(ForgotPasswordScreen.EXTRA_USERNAME));
        cur.moveToFirst();


        mUserNameEditText.setText(cur.getString(cur.getColumnIndex(FeedEntry.COL_USERNAME)));
        mPasswordEditText1.setText(cur.getString(cur.getColumnIndex(FeedEntry.COL_PASSWORD)));
        mPasswordEditText2.setText(cur.getString(cur.getColumnIndex(FeedEntry.COL_PASSWORD)));

        final Button update = (Button) findViewById(R.id.btn_update);
        //Register user details to both the preferences and re-direct to the landing page
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate all the fields before storing in preferences
                if(validateAll(mUserNameEditText,mPasswordEditText1,mPasswordEditText2))
                {
                    if(db.updateDB(cur.getString(cur.getColumnIndex(FeedEntry.COL_ID)),null, null
                            , mPasswordEditText1.getText().toString(), null, null,null)){
                        //Create a cache copy for logged in user - local preference
                        mUserLocalDetails.storeUserData(new User(mUserNameEditText.getText().toString(),mPasswordEditText2.getText().toString()));
                        mUserLocalDetails.setUserLoggedIn(true);
                        mUserLocalDetails.storeHistory(ForgetScreen.this,"Updated Information");
                        Intent landingPage = new Intent(ForgetScreen.this, LandingScreen.class);
                        landingPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(landingPage);
                    }
                    else
                    {
                        Snackbar.make(v,"Database username does not exist", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //Function to validate all the passed fields, whether they are empty or specific condition
    public final boolean validateAll(EditText name,  EditText pass1, EditText pass2){
        boolean result = true;

        if(name.getText().toString().trim().length() == 0){
            name.setError("Name is empty.");
            result = false;
        }
        if(pass1.getText().toString().trim().length() == 0){
            pass1.setError("Password is empty.");
            result = false;
        }
        if(pass2.getText().toString().trim().length() == 0){
            pass2.setError("Password is empty.");
            result = false;
        }
        else if(!pass2.getText().toString().equals(pass1.getText().toString())){
            pass2.setError("Passwords did not match.");
            pass1.setError("Passwords did not match.");
            result = false;
        }
        return result;
    }

}
