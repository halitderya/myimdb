package com.example.myimdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "films";
    public static final String ID = "id";
    public static final String NAME = "filmName";
    public static final String YEAR = "filmYear";
    public static final String GENRE = "filmGenre";
    public static final String PLOT = "filmPlot";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " VARCHAR(255), "
                + YEAR + " VARCHAR(255), "
                + GENRE + " VARCHAR(255), "
                + PLOT + " VARCHAR(255));";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }


    // CRUD Operations

    // Add data
    public void addData(String name, String genre, String year, String plot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(YEAR, year);
        values.put(GENRE, genre);
        values.put(PLOT, plot);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    public void updateData(int id, String newName, String newYear, String newGenre, String newPlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, newName);
        values.put(YEAR, newYear);
        values.put(GENRE, newGenre);
        values.put(PLOT, newPlot);

        db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    // Delete data
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
