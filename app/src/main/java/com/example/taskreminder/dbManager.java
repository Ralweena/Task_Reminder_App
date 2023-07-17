package com.example.taskreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbManager extends SQLiteOpenHelper {
    //Table  name to store reminders in sqllite
    private static String dbname = "Reminder";

    public dbManager(@Nullable Context context) {

        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sql query to insert data in sqllite
        String query = "create table tbl_reminder(id integer primary key autoincrement,title text,date text,time text,category text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //sql query to check table with the same name or not
        String query = "DROP TABLE IF EXISTS tbl_reminder";
        //executes the sql command
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    public String addreminder(String title, String date, String time, String category) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        //Inserts  data into sqllite database
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("category", category);

        //returns -1 if data is not inserted into database
        float result = database.insert("tbl_reminder", null, contentValues);

        if (result == -1) {
            return "Failed to Insert";
        } else {
            return "Successfully Inserted";
        }

    }

    public Cursor readPersonalReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String personalQuery = "select * from tbl_reminder where category = 'Personal' order by id desc";
        Cursor personal = database.rawQuery(personalQuery, null);
        return personal;
    }

    public Cursor readWorkReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String workQuery = "select * from tbl_reminder where category = 'Work' order by id desc";
        Cursor work = database.rawQuery(workQuery, null);
        return work;
    }

    public Cursor readMedicineReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String medicineQuery = "select * from tbl_reminder where category = 'Medicine' order by id desc";
        Cursor medicine = database.rawQuery(medicineQuery, null);
        return medicine;
    }

    public Cursor readShoppingReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String shoppingQuery = "select * from tbl_reminder where category = 'Shopping' order by id desc";
        Cursor shopping = database.rawQuery(shoppingQuery, null);
        return shopping;
    }

    public Cursor readPartyReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String partyQuery = "select * from tbl_reminder where category = 'Party' order by id desc";
        Cursor party = database.rawQuery(partyQuery, null);
        return party;
    }

    public Cursor readMoviesReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        //Sql query to  retrieve  data from the database
        String moviesQuery = "select * from tbl_reminder where category = 'Movies' order by id desc";
        Cursor movies = database.rawQuery(moviesQuery, null);
        return movies;
    }


}
