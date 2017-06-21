package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    private String queryParam = "popular";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        new FetchMovieTask(this, this, mRecyclerView).execute(queryParam);
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
                queryParam = "popular";
                new FetchMovieTask(this, this, mRecyclerView).execute(queryParam);
                setTitle(R.string.app_name);
                break;
            case (R.id.action_sortByRating):
                queryParam = "top_rated";
                setTitle(R.string.app_name_rating);
                new FetchMovieTask(this, this, mRecyclerView).execute(queryParam);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

