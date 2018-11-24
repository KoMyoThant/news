package com.ps.news.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "news.db";

    private static final String SQL_CREATE_NEWS = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
            NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsContract.NewsEntry.COLUMN_AUTHOR + " VARCHAR(256), " +
            NewsContract.NewsEntry.COLUMN_TITLE + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_DESCRIPTION + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_URL + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_PUBLISHED_DATE + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_CONTENT + " TEXT, " +
            NewsContract.NewsEntry.COLUMN_SOURCE_ID + " TEXT, " +
            " UNIQUE (" + NewsContract.NewsEntry.COLUMN_TITLE + "," + NewsContract.NewsEntry.COLUMN_URL + ") ON CONFLICT REPLACE" +
            ");";

    private static final String SQL_CREATE_SOURCE = "CREATE TABLE " + NewsContract.SourceEntry.TABLE_NAME + " (" +
            NewsContract.SourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsContract.SourceEntry.COLUMN_SOURCE_ID + " VARCHAR(256), " +
            NewsContract.SourceEntry.COLUMN_SOURCE_NAME + " TEXT, " +
            " UNIQUE (" + NewsContract.SourceEntry.COLUMN_SOURCE_ID + ") ON CONFLICT REPLACE" +
            ");";

    public NewsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SOURCE);
        db.execSQL(SQL_CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.SourceEntry.TABLE_NAME);

        onCreate(db);
    }
}
