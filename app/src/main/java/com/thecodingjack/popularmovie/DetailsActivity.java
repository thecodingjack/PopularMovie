package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thecodingjack.popularmovie.utilities.MovieUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.thecodingjack.popularmovie.MainActivity.EXTRA_MOVIEID;

public class DetailsActivity extends AppCompatActivity {
    private TextView mMovieTitle;
    private ImageView mPosterImage;
    private TextView mSypnosis;
    private TextView mRating;
    private TextView mReleasedDate;
    private int selectedMovieID;
    private AsyncTask trailerTask, reviewTask;
    private LinearLayout trailerLinearLayout, reviewLinearLayout;
private Button favoriteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.movie_details);

        mMovieTitle = (TextView) findViewById(R.id.display_movie_title);
        mPosterImage = (ImageView) findViewById(R.id.display_movie_poster);
        mSypnosis = (TextView) findViewById(R.id.display_movie_sypnosis);
        mRating = (TextView) findViewById(R.id.display_movie_rating);
        mReleasedDate = (TextView) findViewById(R.id.display_movie_date);
        trailerLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_trailer);
        reviewLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_reviews);
        favoriteButton = (Button)findViewById(R.id.action_favorite);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIEID)) {
            selectedMovieID = intent.getIntExtra(EXTRA_MOVIEID, -1);
        }

        if (intent.hasExtra("title")) {
            String displayInput = intent.getStringExtra("title");
            mMovieTitle.setText(displayInput);
        }
        if (intent.hasExtra("posterURL")) {
            String posterURL = intent.getStringExtra("posterURL");
            Picasso.with(this).load(posterURL).into(mPosterImage);
        }
        if (intent.hasExtra("sypnosis")) {
            String displayInput = intent.getStringExtra("sypnosis");
            mSypnosis.setText(displayInput);
        }
        if (intent.hasExtra("rating")) {
            String displayInput = intent.getStringExtra("rating") + "/10";
            mRating.setText(displayInput);
        }
        if (intent.hasExtra("releasedDate")) {
            String displayInput = String.format(intent.getStringExtra("releasedDate"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            Date date;
            try {
                date = df.parse(displayInput);
                displayInput = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mReleasedDate.setText(displayInput);
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

                    Log.v("TEST", "TAG for position: " + i + view.getTag());

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String youTubeKEY = (String) v.getTag();
                            Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + youTubeKEY);
                            Intent trailerIntent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(trailerIntent);
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
                    TextView authorTV = (TextView)view.findViewById(R.id.review_author_TV);
                    TextView contentTV = (TextView)view.findViewById(R.id.review_content_TV);
                    reviewLinearLayout.addView(view);
                    String author = lists.get(i)[0];
                    String content = lists.get(i)[1];
                    authorTV.setText("By: " + author);
                    contentTV.setText(content);
                }
            }
        }.execute();


    }

    public void markFavorite(View view){
        Toast.makeText(this,"Movie saved to Favorite!",Toast.LENGTH_SHORT).show();
    }
}
