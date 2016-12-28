package com.example.m_alrajab.hpi_simplefitnessapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UserLocalDetails {

    public static final String SP_NAME = "userdetails";

    SharedPreferences userLocalDatabase;

    public UserLocalDetails(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public UserLocalDetails(Context context, String filename) {
        userLocalDatabase = context.getSharedPreferences(filename, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", user.name);
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("username", user.username);
        userLocalDatabaseEditor.putString("major", user.major);
        userLocalDatabaseEditor.putString("dob", user.dob);
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");
        String username = userLocalDatabase.getString("username", "");
        String major = userLocalDatabase.getString("major", "");
        String dob = userLocalDatabase.getString("dob", "");

        User user = new User(name,username, email, password, major, dob);
        return user;
    }

    public void storeHistory(Activity activity, String data){
        try {
            FileOutputStream fout = activity.openFileOutput(this.getLoggedInUser().history, Context.MODE_APPEND);
            fout.write((data + " : " + getCurrentTimeStamp() + "\n").getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getDateFormat(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
}
