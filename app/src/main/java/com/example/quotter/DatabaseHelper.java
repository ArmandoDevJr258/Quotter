package com.example.quotter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper  extends SQLiteOpenHelper {
    // QuoteDatabaseHelper.java

        private static final String DATABASE_NAME = "quotes.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "saved_quotes";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_QUOTE = "quote_text";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_QUOTE + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void saveQuote(String quote) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUOTE, quote);
            db.insert(TABLE_NAME, null, values);
            db.close();
        }

        public ArrayList<String> getAllQuotes() {
            ArrayList<String> quotes = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_QUOTE + " FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    quotes.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return quotes;
        }
    public void deleteQuote(String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_QUOTE + " = ?", new String[]{quote});
        db.close();
    }

}


