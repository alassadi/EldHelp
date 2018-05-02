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
    public static final String TABLE_MEDICINE = "medicine";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    //SQL for creating users table
    public static final String SQL_TABLE_MEDICINE = " CREATE TABLE " + TABLE_MEDICINE
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_MEDICINE_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT"
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
    public void addUser(Account user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.getName());

        values.put(KEY_PASSWORD, user.getPassword());

        values.put(KEY_BALANCE, user.getBalance());

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public Account Authenticate(Account user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_USER_NAME, KEY_PASSWORD, KEY_BALANCE},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{user.getName()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email
            Account user1 = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2));

            //Match both passwords check they are same or not
            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public String showBalance(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_USER_NAME, KEY_BALANCE},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{name},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            Account user1 = new Account(cursor.getString(0), cursor.getString(1));
            return user1.getBalance();
        }

        //if name does not exist return false
        return "0";
    }

    public String getBalance() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_USER_NAME, KEY_BALANCE},
                KEY_USER_NAME + "=?",
                new String[]{"Name"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true

            String balance;
            balance = cursor.getString(1);
            return balance;
        }
        return null;

    }
}