package com.thecodingjack.popularmovie.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.thecodingjack.popularmovie.data.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by lamkeong on 6/30/2017.
 */

public class MovieContentProvider extends ContentProvider {
    public static final int MOVIE_DIRECTORY = 100;
    public static final int MOVIE_WITH_ID = 101;

    private MovieOpenHelper mdbHelper;
    private UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE_DIRECTORY);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mdbHelper = new MovieOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        mdbHelper = new MovieOpenHelper(getContext());
        final SQLiteDatabase db = mdbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case MOVIE_DIRECTORY: {
                db.beginTransaction();
                try {
                    long id = db.insert(TABLE_NAME, null, values);
                    if (id > 0) {
                        returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();

                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        mdbHelper = new MovieOpenHelper(getContext());
        final SQLiteDatabase db = mdbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (match) {
            case MOVIE_DIRECTORY:
                returnCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        mdbHelper = new MovieOpenHelper(getContext());
        final SQLiteDatabase db = mdbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int movieDeleted;
        switch (match) {
            case MOVIE_WITH_ID: {
                String id = uri.getPathSegments().get(1);
                movieDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
