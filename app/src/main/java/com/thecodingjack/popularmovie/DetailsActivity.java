package com.thecodingjack.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
private TextView mMovieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovieDetails = (TextView)findViewById(R.id.display_movie_data);

        Intent intent=getIntent();
        if (intent.hasExtra("input")){
            String displayInput = intent.getStringExtra("input");
            mMovieDetails.setText(displayInput);
        }


    }
}
