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
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
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
        switch (match){
            case MOVIE_WITH_ID :{
                String id = uri.getPathSegments().get(1);
                movieDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,"_id=?",new String[]{id});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (movieDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return movieDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
