package com.example.m_alrajab.hpi_simplefitnessapp.utils;

/**
 * Created by Nishok on 3/27/2016.
 */
public class User {

    public String name, dob, email, password, username, major, history, notes;

    public User(String name, String username, String email, String password, String major, String dob) {
        this.name = name;
        this.dob = dob;
        this.username = username;
        this.history = this.username+"_history";
        this.notes = this.username+"_notes";
        this.email = email;
        this.password = password;
        this.major = major;
    }

    public User(String username, String password) {
        this("", username, "", password, "","");
    }

}
