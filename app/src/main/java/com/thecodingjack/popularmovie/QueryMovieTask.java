package com.thecodingjack.popularmovie;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thecodingjack.popularmovie.data.MovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lamkeong on 6/30/2017.
 */

public class QueryMovieTask extends AsyncTask<Void, Void, List<Movies>> {

    private Context context;
    private MovieAdapter.MovieClickListener mMovieClickListener;
    private RecyclerView mRecyclerView;

    public static final int INDEX_MOVIEID = 1;
    public static final int INDEX_MOVIETITLE = 2;
    public static final int INDEX_POSTERURL = 3;
    public static final int INDEX_SYPNOSIS = 4;
    public static final int INDEX_RATING = 5;
    public static final int INDEX_RELEASED_DATE = 6;

    public QueryMovieTask(Context ctx, MovieAdapter.MovieClickListener movieClickListener, RecyclerView view) {
        context = ctx;
        mMovieClickListener = movieClickListener;
        mRecyclerView = view;
    }

    @Override
    protected List<Movies> doInBackground(Void[] params) {
        Cursor cursor;
        ArrayList<Movies> moviesList = new ArrayList<>();
        try {
            cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, MovieContract.MovieEntry._ID + " DESC");
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int movieID = cursor.getInt(INDEX_MOVIEID);
                String movieTitle = cursor.getString(INDEX_MOVIETITLE);
                String posterURL = cursor.getString(INDEX_POSTERURL);
                String synopsis = cursor.getString(INDEX_SYPNOSIS);
                double rating = cursor.getDouble(INDEX_RATING);
                String releasedDate = cursor.getString(INDEX_RELEASED_DATE);
                Movies movies = new Movies(movieID, movieTitle, posterURL, synopsis, rating, releasedDate);
                moviesList.add(movies);
            }
            return moviesList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movies> moviesList) {
        if (moviesList == null) {
            return;
        }

        MovieAdapter mMovieAdapter = new MovieAdapter(mMovieClickListener, moviesList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);


        mMovieAdapter.setMoviesList(moviesList);

    }
}

