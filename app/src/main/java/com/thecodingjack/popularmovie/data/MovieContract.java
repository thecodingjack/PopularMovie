package com.thecodingjack.popularmovie.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lamkeong on 6/29/2017.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.thecodingjack.popularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";


    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIEID = "movieID";
        public static final String COLUMN_MOVIETITLE = "movieTitle";
        public static final String COLUMN_POSTERURL = "posterURL";
        public static final String COLUMN_SYPNOSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASED_DATE = "releasedDate";


    }
}
