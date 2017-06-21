package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private TextView mMovieTitle;
    private ImageView mPosterImage;
    private TextView mSypnosis;
    private TextView mRating;
    private TextView mReleasedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.movie_details);

        mMovieTitle = (TextView) findViewById(R.id.display_movie_title);
        mPosterImage = (ImageView) findViewById(R.id.display_movie_poster);
        mSypnosis = (TextView)findViewById(R.id.display_movie_sypnosis);
        mRating = (TextView) findViewById(R.id.display_movie_rating);
        mReleasedDate = (TextView)findViewById(R.id.display_movie_date);

        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            String displayInput = intent.getStringExtra("title");
            mMovieTitle.setText(displayInput);
        }
        if(intent.hasExtra("posterURL")){
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
            try{
                date = df.parse(displayInput);
                displayInput=df.format(date);
            }catch (ParseException e){
                e.printStackTrace();
            }
            mReleasedDate.setText(displayInput);
        }


    }
}
