package com.thecodingjack.popularmovie.utilities;

import android.net.Uri;
import android.text.TextUtils;

import com.thecodingjack.popularmovie.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by lamkeong on 6/20/2017.
 */

public final class NetworkUtil {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie";
    private final static String API_KEY = "1f7e017cadb0f3c96db924c8672a3328";
    private final static String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public static URL buildUrl(String input) {
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(input).appendQueryParameter("api_key", API_KEY).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()){
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

    public static List<Movies> extractJSONData(String jsonString)throws JSONException{
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        List<Movies> moviesList= new ArrayList<>();

        JSONObject movieJson = new JSONObject(jsonString);
        JSONArray movieArray = movieJson.getJSONArray("results");
//        String [] parsedMovieData = new String[movieArray.length()];

        for(int i = 0; i<movieArray.length();i++){
            JSONObject movieObject = movieArray.getJSONObject(i);
            String movieTitle = movieObject.getString("title");
            String posterPath = POSTER_BASE_URL + movieObject.getString("poster_path");
            String sypnosis = movieObject.getString("overview");
            double rating = movieObject.getDouble("vote_average");
            String releasedDate= movieObject.getString("release_date");
            Movies newMovie = new Movies(movieObject,movieTitle,posterPath,sypnosis,rating,releasedDate);
            moviesList.add(newMovie);
//            parsedMovieData[i]=posterPath +"," +movieTitle;


        }
        return moviesList;
//        return parsedMovieData;
    }
}
