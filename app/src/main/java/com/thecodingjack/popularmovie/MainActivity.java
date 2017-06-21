package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thecodingjack.popularmovie.utilities.NetworkUtil;

import org.json.JSONException;

import java.net.URL;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private String queryParam;
//    private String[] movieData = {"movie1",
//            "movie2",
//            "movie3",
//            "movie4",
//            "movie5",
//            "movie6",
//            "movie7",
//            "movie8",
//            "movie9",
//            "movie10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);


        new FetchMovieTask().execute(queryParam);
    }

    @Override
    public void onListItemClick(String movieDetails) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("input", movieDetails);
        startActivity(intent);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            URL url = NetworkUtil.buildUrl(params[0]);
            try {
                String jsonResponse = NetworkUtil.getResponseFromHttp(url);
                String[] jsonData = NetworkUtil.extractJSONData(jsonResponse);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] jsonData) {
            if (jsonData == null){
                return;
            }
            String[] movieTitleArray = new String[jsonData.length];
            String[] moviePathArray = new String[jsonData.length];
            for(int i =0; i<jsonData.length;i++){
                String[] holder = jsonData[i].split(",");
                moviePathArray[i]=holder[0];
                movieTitleArray[i]=holder[1];
            }
            mMovieAdapter.setMovieNames(movieTitleArray);
            mMovieAdapter.setMoviePosters(moviePathArray);
        }
    }
}

