package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.thecodingjack.popularmovie.utilities.NetworkUtil;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;

    private String queryParam = "popular";
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

        new FetchMovieTask().execute(queryParam);
    }

    @Override
    public void onListItemClick(String movieTitle, String posterUrl, String sypnosis, double rating, String releasedDate) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("title", movieTitle);
        intent.putExtra("posterURL", posterUrl);
        intent.putExtra("sypnosis", sypnosis);
        intent.putExtra("rating", String.format("%.1f", rating));
        intent.putExtra("releasedDate", releasedDate);
        startActivity(intent);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movies>> {
        @Override
        protected List<Movies> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            URL url = NetworkUtil.buildUrl(params[0]);
            Log.v("Testing","URL: " +url);
            try {
                String jsonResponse = NetworkUtil.getResponseFromHttp(url);
                Log.v("Testing",jsonResponse);
                return NetworkUtil.extractJSONData(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movies> moviesList) {
            if (moviesList == null){
                return;
            }
//            String[] movieTitleArray = new String[jsonData.length];
//            String[] moviePathArray = new String[jsonData.length];
//            for(int i =0; i<jsonData.length;i++){
//                String[] holder = jsonData[i].split(",");
//                moviePathArray[i]=holder[0];
//                movieTitleArray[i]=holder[1];
//            }
            mMovieAdapter = new MovieAdapter(MainActivity.this,moviesList);
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setAdapter(mMovieAdapter);


            mMovieAdapter.setMoviesList(moviesList);
            Log.v("Testing", moviesList.get(1).getMovieTitle());
//            mMovieAdapter.setMovieNames(movieTitleArray);
//            mMovieAdapter.setMoviePosters(moviePathArray);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case (R.id.action_sortByPopular):
                queryParam = "popular";
                new FetchMovieTask().execute(queryParam);
                setTitle(R.string.app_name);
                break;
            case(R.id.action_sortByRating):
                queryParam = "top_rated";
                setTitle(R.string.app_name_rating);
                new FetchMovieTask().execute(queryParam);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

