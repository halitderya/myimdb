package com.example.myimdb;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AlertDialog;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "films";
    public static final String ID = "_id";
    public static final String NAME = "filmName";
    public static final String YEAR = "filmYear";
    public static final String GENRE = "filmGenre";
    public static final String PLOT = "filmPlot";
    public static final String DIRECTOR = "filmDirector";


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
                + PLOT + " VARCHAR(255), "
                + DIRECTOR + " VARCHAR(255));";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    // CRUD Operations

    // Add data
    public void addData(String name, String genre, String year, String plot,String director) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(YEAR, year);
        values.put(GENRE, genre);
        values.put(PLOT, plot);
        values.put(DIRECTOR,director);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }



    public void updateData(Context context,int id, String newName, String newYear, String newGenre, String newPlot, String newdirctor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, newName);
        values.put(YEAR, newYear);
        values.put(GENRE, newGenre);
        values.put(PLOT, newPlot);
        values.put(DIRECTOR, newdirctor);


        int numRowsUpdated = db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(id)});

        // For debugging: Display the number of rows updated
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Status")
                .setMessage(numRowsUpdated + " rows updated.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog updateDialog = builder.create();
        updateDialog.show();



        db.update(TABLE_NAME, values, id + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    // Delete data
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor searchData(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id","filmName","filmYear","filmGenre", "filmPlot", "filmDirector"};
        String selection = "filmName LIKE ?";
        String[] selectionArgs = { "%" + query + "%" };
        Cursor cursor = db.query("films", columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

}
