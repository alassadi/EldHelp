package com.company.eldhelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "EldHelp";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE NAME
    public static final String TABLE_MEDICINE = "medicines";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String PERSON_ID = "id";

    //COLUMN user name
    public static final String PERSON_NAME = "username";


    //TABLE MEDICINE COLUMNS
    //ID COLUMN @primaryKey
    public static final String MEDICINE_ID = "id";

    //COLUMN medicine name
    public static final String MEDICINE_NAME = "medicine";

    //COLUMN time
    public static final String MEDICINE_TIME = "time";



    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + PERSON_ID + " INTEGER PRIMARY KEY, "
            + PERSON_NAME + " TEXT"
            + " ) ";

    //SQL for creating medicine table
    public static final String SQL_TABLE_MEDICINE = " CREATE TABLE " + TABLE_MEDICINE
            + " ( "
            + MEDICINE_ID + " INTEGER PRIMARY KEY, "
            + MEDICINE_NAME + " TEXT, "
            + MEDICINE_TIME + " TEXT"
            + " ) ";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    //using this method we can add users to user table
    public void addUser(Person user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.getName());

        values.put(KEY_PASSWORD, user.getPassword());


        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }
}