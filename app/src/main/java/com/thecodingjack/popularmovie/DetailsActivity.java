package com.thecodingjack.popularmovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thecodingjack.popularmovie.data.MovieContract;
import com.thecodingjack.popularmovie.utilities.MovieUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.thecodingjack.popularmovie.MainActivity.EXTRA_MOVIEID;
import static com.thecodingjack.popularmovie.MainActivity.EXTRA_POSTERURL;
import static com.thecodingjack.popularmovie.MainActivity.EXTRA_RATING;
import static com.thecodingjack.popularmovie.MainActivity.EXTRA_RELEASEDDATE;
import static com.thecodingjack.popularmovie.MainActivity.EXTRA_SYPNOSIS;
import static com.thecodingjack.popularmovie.MainActivity.EXTRA_TITLE;

public class DetailsActivity extends AppCompatActivity {
    private ScrollView mScrollView;
    private TextView mMovieTitle;
    private ImageView mPosterImage;
    private TextView mSypnosis;
    private TextView mRating;
    private TextView mReleasedDate;
    private int selectedMovieID;
    private String titleInput, posterURL, sypnosisInput, ratingInput, dateInput;
    private AsyncTask trailerTask, reviewTask;
    private LinearLayout trailerLinearLayout, reviewLinearLayout;
    private int scrollPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.movie_details);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mMovieTitle = (TextView) findViewById(R.id.display_movie_title);
        mPosterImage = (ImageView) findViewById(R.id.display_movie_poster);
        mSypnosis = (TextView) findViewById(R.id.display_movie_sypnosis);
        mRating = (TextView) findViewById(R.id.display_movie_rating);
        mReleasedDate = (TextView) findViewById(R.id.display_movie_date);
        trailerLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_trailer);
        reviewLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_reviews);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIEID)) {
            selectedMovieID = intent.getIntExtra(EXTRA_MOVIEID, -1);
        }

        if (intent.hasExtra(EXTRA_TITLE)) {
            titleInput = intent.getStringExtra(EXTRA_TITLE);
            mMovieTitle.setText(titleInput);
        }
        if (intent.hasExtra(EXTRA_POSTERURL)) {
            posterURL = intent.getStringExtra(EXTRA_POSTERURL);
            Picasso.with(this).load(posterURL).into(mPosterImage);
        }
        if (intent.hasExtra(EXTRA_SYPNOSIS)) {
            sypnosisInput = intent.getStringExtra(EXTRA_SYPNOSIS);
            mSypnosis.setText(sypnosisInput);
        }
        if (intent.hasExtra(EXTRA_RATING)) {
            ratingInput = intent.getStringExtra(EXTRA_RATING);
            mRating.setText(ratingInput + "/10");
        }
        if (intent.hasExtra(EXTRA_RELEASEDDATE)) {
            dateInput = String.format(intent.getStringExtra(EXTRA_RELEASEDDATE));
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            Date date;
            try {
                date = df.parse(dateInput);
                dateInput = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mReleasedDate.setText(dateInput);
        }

        trailerTask = new AsyncTask<Void, Void, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Void[] params) {
                return MovieUtil.retrieveTrailer(selectedMovieID);
            }

            @Override
            protected void onPostExecute(ArrayList<String> lists) {
                for (int i = 0; i < lists.size(); i++) {

                    View view = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.list_trailer, trailerLinearLayout, false);
                    TextView trailerNumber = (TextView) view.findViewById(R.id.trailer_number);
                    trailerLinearLayout.addView(view);

                    String trailerKey = lists.get(i);
                    view.setTag(trailerKey);
                    int trailerCount = i + 1;
                    trailerNumber.setText("Trailer " + trailerCount);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String youTubeKEY = (String) v.getTag();
                            Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + youTubeKEY);
                            Intent trailerIntent = new Intent(Intent.ACTION_VIEW, uri);
                            if (trailerIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(trailerIntent);
                            }
                        }
                    });
                }
            }
        }.execute();

        reviewTask = new AsyncTask<Void, Void, ArrayList<String[]>>() {
            @Override
            protected ArrayList<String[]> doInBackground(Void[] params) {
                return MovieUtil.retrieveReview(selectedMovieID);
            }

            @Override
            protected void onPostExecute(ArrayList<String[]> lists) {
                for (int i = 0; i < lists.size(); i++) {

                    View view = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.list_review, null);
                    TextView authorTV = (TextView) view.findViewById(R.id.review_author_TV);
                    TextView contentTV = (TextView) view.findViewById(R.id.review_content_TV);
                    reviewLinearLayout.addView(view);
                    String author = lists.get(i)[0];
                    String content = lists.get(i)[1];
                    authorTV.setText("By: " + author);
                    contentTV.setText(content);
                }
            }
        }.execute();
        try {
            if (savedInstanceState.containsKey("scrollPositionY")) {
                int scrollPositionY = savedInstanceState.getInt("scrollPositionY");
                Log.v("TEST", "Saved Scroll Position =" + scrollPositionY);
                scrollPosition = scrollPositionY;
                mScrollView.scrollTo(100, scrollPositionY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScrollView.scrollTo(100, scrollPosition);

    }

    public void markFavorite(View view) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIEID, selectedMovieID);
            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIETITLE, titleInput);
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTERURL, posterURL);
            contentValues.put(MovieContract.MovieEntry.COLUMN_SYPNOSIS, sypnosisInput);
            contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, Double.parseDouble(ratingInput));
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASED_DATE, dateInput);

            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
            if (uri != null) {
                Toast.makeText(this, "Movie saved to Favorite!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Movie is already saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int scrollPositionY = mScrollView.getScrollY();
        Log.v("TEST", "Scroll positionXY: " + scrollPositionY);
        outState.putInt("scrollPositionY", scrollPositionY);
    }

}
