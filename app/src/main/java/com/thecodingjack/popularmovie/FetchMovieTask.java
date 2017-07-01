package com.thecodingjack.popularmovie;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thecodingjack.popularmovie.utilities.NetworkUtil;

import java.net.URL;
import java.util.List;

/**
 * Created by lamkeong on 6/21/2017.
 */

public class FetchMovieTask extends AsyncTask<String, Void, List<Movies>> {
    private Context context;
    private MovieAdapter.MovieClickListener mMovieClickListener;
    private RecyclerView mRecyclerView;
    private int savedScrollPosition;

    public FetchMovieTask(Context ctx, MovieAdapter.MovieClickListener movieClickListener, RecyclerView view, int scrollPosition) {
        context = ctx;
        mMovieClickListener = movieClickListener;
        mRecyclerView = view;
        savedScrollPosition = scrollPosition;
    }

    @Override
    protected List<Movies> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        URL url = NetworkUtil.buildUrl(params[0]);
        try {
            String jsonResponse = NetworkUtil.getResponseFromHttp(url);
            return NetworkUtil.extractJSONData(jsonResponse);
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
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollBy(0, savedScrollPosition);
            }
        });

    }
}
