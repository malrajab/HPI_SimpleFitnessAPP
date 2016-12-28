package com.example.m_alrajab.hpi_simplefitnessapp.model;

import android.provider.BaseColumns;

/**
 * Created by m_alrajab on 12/27/16.
 *
 * @author Moaath Alrajab
 *
 * This is a contract class for the database. It implements the baseColums class
 * better compatibility with Android OS
 *
 */

public class DbContract {

    /**
     * Assumptions: The user address is stored as a string sequence not as a table
     * -* this needs a redesign so that the address is pulled from an autocomplete
     * db
     */
    public DbContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {

        // Name of the SQLite db
        public static final String DATABASE_NAME = "HPI_database.db";

        // Name of the users table inside the previous db
        public static final String TABLE_NAME = "users";

        //These are the columns of the designed table
        public static final String COL_ID = "entryid";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_FNAME = "fname";
        public static final String COL_SNAME = "sname";
        public static final String COL_ADDRESS = "address";
        public static final String COL_EMAIL = "email";
        public static final String COL_DOB = "dob";
        public static final int DB_VERSION = 1;
    }
}
