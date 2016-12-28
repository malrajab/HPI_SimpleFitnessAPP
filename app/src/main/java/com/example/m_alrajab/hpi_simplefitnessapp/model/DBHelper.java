package com.example.m_alrajab.hpi_simplefitnessapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by m_alrajab on 12/27/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, DbContract.FeedEntry.DATABASE_NAME, null, DbContract.FeedEntry.DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qr = "CREATE TABLE "+ DbContract.FeedEntry.TABLE_NAME + "("
                + DbContract.FeedEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.FeedEntry.COL_USERNAME + " TEXT, " +
                DbContract.FeedEntry.COL_EMAIL + " TEXT, " +
                DbContract.FeedEntry.COL_FNAME + " TEXT, " +
                DbContract.FeedEntry.COL_DOB + " TEXT, " +
                DbContract.FeedEntry.COL_SNAME + " TEXT, " +
                DbContract.FeedEntry.COL_ADDRESS + " TEXT, " +
                DbContract.FeedEntry.COL_PASSWORD + " TEXT)" ;
        db.execSQL(qr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FeedEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDB(String username, String fname,String sname, String pass,String address, String email, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.FeedEntry.COL_USERNAME,username);
        values.put(DbContract.FeedEntry.COL_FNAME,fname);
        values.put(DbContract.FeedEntry.COL_SNAME,sname);
        values.put(DbContract.FeedEntry.COL_PASSWORD,pass);
        values.put(DbContract.FeedEntry.COL_ADDRESS,address);
        values.put(DbContract.FeedEntry.COL_EMAIL,email);
        values.put(DbContract.FeedEntry.COL_DOB,dob);
        long id = db.insert(DbContract.FeedEntry.TABLE_NAME, null, values);
        return id == -1? false:true;
    }

    public boolean updateDB(String entryID,String fname,String sname, String pass,String address, String email, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(fname!=null)values.put(DbContract.FeedEntry.COL_FNAME,fname);
        if(sname!=null)values.put(DbContract.FeedEntry.COL_SNAME,sname);
        if(pass!=null)values.put(DbContract.FeedEntry.COL_PASSWORD,pass);
        if(address!=null)values.put(DbContract.FeedEntry.COL_ADDRESS,address);
        if(email!=null)values.put(DbContract.FeedEntry.COL_EMAIL,email);
        if(dob!=null)values.put(DbContract.FeedEntry.COL_DOB,dob);
        long id = db.update(DbContract.FeedEntry.TABLE_NAME, values, "entryid = ?",new String[]{entryID});
        return id == -1? false:true;
    }

    public Cursor getRecForUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = null;
        try{
            cur = db.rawQuery("SELECT * FROM "+ DbContract.FeedEntry.TABLE_NAME+" WHERE "+ DbContract.FeedEntry.COL_USERNAME +" LIKE '"+username+"' ;", null);
            return cur;
        }
        catch (SQLiteException exce){
            System.out.println(exce.getMessage());
            return null;
        }
    }

    public Integer deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DbContract.FeedEntry.TABLE_NAME,"entryid = ?",new String[]{id});
    }
}
