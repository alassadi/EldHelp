package com.company.eldhelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "EldHelp";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE NAME
    public static final String TABLE_MEDICINE = "medicines";

    //TABLE NAME
    public static final String TABLE_EVENT = "events";

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

    //TABLE EVENT COLUMNS
    //ID COLUMN @primaryKey
    public static final String EVENT_ID = "id";

    //COLUMN event name
    public static final String EVENT_NAME = "event";

    //COLUMN time
    public static final String EVENT_TIME = "time";

    //COLUMN date
    public static final String EVENT_DATE = "date";


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

    //SQL for creating events table
    public static final String SQL_TABLE_EVENT = " CREATE TABLE " + TABLE_EVENT
            + " ( "
            + EVENT_ID + " INTEGER PRIMARY KEY, "
            + EVENT_NAME + " TEXT, "
            + EVENT_TIME + " TEXT, "
            + EVENT_DATE + " TEXT"
            + " ) ";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_MEDICINE);
        sqLiteDatabase.execSQL(SQL_TABLE_EVENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_EVENT);

    }

    //using this method we can add users to user table
    public void addUser(Person user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(PERSON_NAME, user.getName());

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public void addMedicine(Medicine medicine) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put medicine in  @values
        values.put(MEDICINE_NAME, medicine.getName());
        values.put(MEDICINE_TIME, medicine.getTime());

        // insert row
        long todo_id = db.insert(TABLE_MEDICINE, null, values);
    }

    public void addEvent (Event event) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put medicine in  @values
        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_TIME, event.getTime());
        values.put(EVENT_DATE, event.getDate());

        // insert row
        long todo_id = db.insert(TABLE_EVENT, null, values);
    }

    public ArrayList<Medicine> getAllElements() {

        ArrayList<Medicine> list = new ArrayList<Medicine>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINE;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Medicine obj = new Medicine();
                        //only one column
                        obj.setName(cursor.getString(1));
                        obj.setTime(cursor.getString(2));

                        //you could add additional columns here..

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> list = new ArrayList<Event>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Event obj = new Event();
                        //only one column
                        obj.setName(cursor.getString(1));
                        obj.setTime(cursor.getString(2));
                        obj.setDate(cursor.getString(3));

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }
}