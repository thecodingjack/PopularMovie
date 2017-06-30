package com.thecodingjack.popularmovie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_MOVIEID;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_MOVIETITLE;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_POSTERURL;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_RATING;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_RELEASED_DATE;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.COLUMN_SYPNOSIS;
import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by lamkeong on 6/29/2017.
 */

public class MovieOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public MovieOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MOVIEID + " INTEGER NOT NULL UNIQUE, " +
                COLUMN_MOVIETITLE + "TEXT NOT NULL, " +
                COLUMN_POSTERURL + "TEXT NOT NULL, " +
                COLUMN_SYPNOSIS + "TEXT, " +
                COLUMN_RATING + "REAL, " +
                COLUMN_SYPNOSIS + "TEXT, " +
                COLUMN_RELEASED_DATE + "TEXT );";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
