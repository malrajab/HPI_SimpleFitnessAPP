package com.example.m_alrajab.hpi_simplefitnessapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.m_alrajab.hpi_simplefitnessapp.model.DBHelper;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.User;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;

import java.util.Calendar;

public class RegistrationPage extends AppCompatActivity {

    private UserLocalDetails mUserLocalDetails;

    private EditText mUserNameEditText;
    private EditText mPasswordEditText1;
    private EditText mPasswordEditText2;
    private EditText mFirstNameEditText;
    private EditText mFamilyNameEditText;
    private EditText mAddressEditText;
    private EditText mDoBEditText;
    private EditText mEmailEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Access the field of the registration form
        mUserLocalDetails   =               new UserLocalDetails(this);
        mUserNameEditText   = (EditText) findViewById(R.id.reg_userName);
        mPasswordEditText1  = (EditText) findViewById(R.id.Reg_pass1);
        mPasswordEditText2  = (EditText) findViewById(R.id.Reg_pass2);
        mFirstNameEditText  = (EditText) findViewById(R.id.reg_fname);
        mFamilyNameEditText = (EditText) findViewById(R.id.reg_sname);
        mAddressEditText    = (EditText) findViewById(R.id.reg_address);
        mDoBEditText        = (EditText) findViewById(R.id.Reg_Dob);
        mEmailEditText      = (EditText) findViewById(R.id.reg_email);

        //Fetch the current calendar timings
        //set the default value with current calendar time
        mDoBEditText.setText(mUserLocalDetails.getDateFormat(Calendar.getInstance().get(Calendar.YEAR),
                (Calendar.getInstance().get(Calendar.MONTH) + 1), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));

        mDoBEditText.setOnClickListener(new View.OnClickListener() {
            //Using DatePickerDialog for selecting the calendar.
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datepickerlistener = new DatePickerDialog.OnDateSetListener() {
                    //set the selected date value from the date picker dialog
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDoBEditText.setText(mUserLocalDetails.getDateFormat(year, monthOfYear, dayOfMonth));
                    }
                };
                DatePickerDialog dobDiag = new DatePickerDialog(RegistrationPage.this, datepickerlistener, 1990, 12, 12);
                dobDiag.show();
            }
        });


        //textChanges events used for validating the mEmailEditText while typing
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(s) || s.length() < 0) mEmailEditText.setError("Invalid email address");
            }
        });


        final Button reg = (Button) findViewById(R.id.btn_Reg);
        //Register user details to both the preferences and re-direct to the landing page
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate all the fields before storing in preferences
                if(validateAll(mUserNameEditText, mFamilyNameEditText, mEmailEditText,mPasswordEditText1, mPasswordEditText2, mDoBEditText))
                {
                    DBHelper db = new DBHelper(RegistrationPage.this);
                    Cursor cur = db.getRecForUsername(mUserNameEditText.getText().toString());
                    if(cur == null || !cur.moveToFirst())
                    {
                        db.insertDB(mUserNameEditText.getText().toString(), mFirstNameEditText.getText().toString(),
                                mFamilyNameEditText.getText().toString(), mPasswordEditText1.getText().toString(),
                                mAddressEditText.getText().toString(), mEmailEditText.getText().toString(), mDoBEditText.getText().toString());
                        //Create a cache copy for logged in user - local preference
                        mUserLocalDetails.storeUserData(new User(mUserNameEditText.getText().toString(), mPasswordEditText2.getText().toString()));
                        mUserLocalDetails.setUserLoggedIn(true);
                        Intent landingPage = new Intent(RegistrationPage.this, LandingScreen.class);
                        landingPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(landingPage);
                    }
                    else
                    {
                        mUserNameEditText.setError("User name already exist");
                    }
                }
            }
        });
    }

    //Function to validate all the passed fields, whether they are empty or specific condition
    public final boolean validateAll(EditText uName, EditText name, EditText email, EditText pass1, EditText pass2, EditText dob){
        boolean result = true;
        if(uName.getText().toString().trim().length() == 0){
            uName.setError("Username is empty.");
            result = false;
        }
        if(email.getText().toString().trim().length() == 0){
            email.setError("Email is empty.");
            result = false;
        }
        else if(!isValidEmail(email.getText().toString())){
            email.setError("Invalid email address");
            result = false;
        }

        if(name.getText().toString().trim().length() == 0){
            name.setError("Name is empty.");
            result = false;
        }
        if(dob.getText().toString().trim().length() == 0){
            dob.setError("DOB is empty.");
            result = false;
        }
        if(pass1.getText().toString().trim().length() == 0){
            pass1.setError("Password is empty.");
            result = false;
        }
        else if(!(pass1.getText().toString().matches(".*[0-9]+.*") )){
            pass1.setError("Password doesn't contain numbers and characters");
            result = false;
        }
        if(pass2.getText().toString().trim().length() == 0){
            pass2.setError("Password is empty.");
            result = false;
        }
        else if(!(pass2.getText().toString().matches(".*[0-9].*") && pass2.getText().toString().matches(".*[a-zA-Z].*"))){
            pass2.setError("Password doesn't contain numbers and characters");
            result = false;
        }
        else if(!pass2.getText().toString().equals(pass1.getText().toString())){
            pass2.setError("Passwords did not match.");
            pass1.setError("Passwords did not match.");
            result = false;
        }
        return result;
    }

    //Function to validate the email
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}