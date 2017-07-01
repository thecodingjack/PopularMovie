package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import static com.thecodingjack.popularmovie.utilities.NetworkUtil.POPULAR_PARAM;
import static com.thecodingjack.popularmovie.utilities.NetworkUtil.TOP_RATED_PARAM;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    private String queryParam = POPULAR_PARAM;
    private RecyclerView mRecyclerView;
    public static final String EXTRA_MOVIEID = "movieID";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_POSTERURL = "posterURL";
    public static final String EXTRA_SYPNOSIS = "sypnosis";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_RELEASEDDATE = "releasedDate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        new FetchMovieTask(this, this, mRecyclerView).execute(POPULAR_PARAM);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try{
            String savedQueryParam = savedInstanceState.getString("savedQueryParam");
            int scrollPosition = savedInstanceState.getInt("scrollPosition");
            if (savedQueryParam.equals("")){
                setTitle(R.string.app_name_favorite);
                new QueryMovieTask(this,this,mRecyclerView).execute();
                mRecyclerView.scrollTo(0,scrollPosition);
            } else if (savedQueryParam.equals(POPULAR_PARAM)){
                setTitle(R.string.app_name);
                new FetchMovieTask(this, this, mRecyclerView).execute(savedQueryParam);
                mRecyclerView.scrollTo(0,scrollPosition);
            } else if (savedQueryParam.equals(TOP_RATED_PARAM)){
                setTitle(R.string.app_name_rating);
                new FetchMovieTask(this, this, mRecyclerView).execute(savedQueryParam);
                mRecyclerView.scrollTo(0,scrollPosition);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onListItemClick(int movieID, String movieTitle, String posterUrl, String sypnosis, double rating, String releasedDate) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIEID,movieID);
        intent.putExtra(EXTRA_TITLE, movieTitle);
        intent.putExtra(EXTRA_POSTERURL, posterUrl);
        intent.putExtra(EXTRA_SYPNOSIS, sypnosis);
        intent.putExtra(EXTRA_RATING, String.format("%.1f", rating));
        intent.putExtra(EXTRA_RELEASEDDATE, releasedDate);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case (R.id.action_sortByPopular):
                queryParam = POPULAR_PARAM;
                setTitle(R.string.app_name);
                new FetchMovieTask(this, this, mRecyclerView).execute(queryParam);
                break;
            case (R.id.action_sortByRating):
                queryParam = TOP_RATED_PARAM;
                setTitle(R.string.app_name_rating);
                new FetchMovieTask(this, this, mRecyclerView).execute(queryParam);
                break;
            case(R.id.action_sortByFavorites):
                queryParam = "";
                setTitle(R.string.app_name_favorite);
                new QueryMovieTask(this,this,mRecyclerView).execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int scrollPosition = mRecyclerView.getScrollY();
        outState.putString("savedQueryParam",queryParam);
        outState.putInt("scrollPosition",scrollPosition);

    }
}

