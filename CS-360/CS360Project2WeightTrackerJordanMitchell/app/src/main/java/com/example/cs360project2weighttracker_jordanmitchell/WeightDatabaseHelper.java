package com.example.cs360project2weighttracker_jordanmitchell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeightDatabaseHelper extends SQLiteOpenHelper {

    // Database Name + Version
    private static final String DATABASE_NAME = "weight_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_WEIGHTS = "weights";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WEIGHT = "weight";

    // Create Table SQL
    private static final String CREATE_TABLE_WEIGHTS =
            "CREATE TABLE " + TABLE_WEIGHTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_WEIGHT + " REAL NOT NULL);";

    // Constructor
    public WeightDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WEIGHTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTS);
        onCreate(db);
    }

    // CRUD for database

    // Insert new weight entry
    public boolean insertWeight(String date, double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WEIGHT, weight);

        long result = db.insert(TABLE_WEIGHTS, null, values);
        db.close();
        return result != -1; // Returns true if insertion is successful
    }

    // Retrieve all weight entries
    public Cursor getAllWeights() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WEIGHTS + " ORDER BY " + COLUMN_DATE + " DESC", null);
    }

    // Update weight entry
    public boolean updateWeight(int id, double newWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, newWeight);

        int rowsUpdated = db.update(TABLE_WEIGHTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    // Delete weight entry
    public boolean deleteWeight(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_WEIGHTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
}
