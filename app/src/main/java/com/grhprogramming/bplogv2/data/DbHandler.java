package com.grhprogramming.bplogv2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bp_database.db";
    private static final String TABLE_NAME = "bp_table";

    private static final String KEY_ID = "id";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";
    private static final String KEY_YEAR = "year";
    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DYSTOLIC = "dystolic";
    private static final String KEY_HEARTRATE = "heartrate";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_HOUR + " INTEGER, " +
                KEY_MINUTE + " INTEGER, " +
                KEY_MONTH + " INTEGER, " +
                KEY_DAY + " INTEGER, " +
                KEY_YEAR + " INTEGER, " +
                KEY_SYSTOLIC + " INTEGER, " +
                KEY_DYSTOLIC + " INTEGER, " +
                KEY_HEARTRATE + " INTEGER)";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Add record
    void insertBp(int i_hour, int i_minute, int i_month, int i_day, int i_year, int i_systolic, int i_dystolic, int i_heartrate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(KEY_HOUR, i_hour);
        cValues.put(KEY_MINUTE, i_minute);
        cValues.put(KEY_MONTH, i_month);
        cValues.put(KEY_DAY, i_day);
        cValues.put(KEY_YEAR, i_year);
        cValues.put(KEY_SYSTOLIC, i_systolic);
        cValues.put(KEY_DYSTOLIC, i_dystolic);
        cValues.put(KEY_HEARTRATE, i_heartrate);

        long newRowID = db.insert(TABLE_NAME, null, cValues);
        db.close();
    }

    // Delete record by id
    public void deleteBp(int bpId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(bpId)});
        db.close();
    }

    // Retrieve all records into a list
    public ArrayList<BP> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<BP> dataList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

            dataList.add(new BP(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HOUR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MINUTE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MONTH)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DAY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_YEAR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SYSTOLIC)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DYSTOLIC)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HEARTRATE))));
        }

        cursor.close();
        db.close();

        return dataList;
    }
}
