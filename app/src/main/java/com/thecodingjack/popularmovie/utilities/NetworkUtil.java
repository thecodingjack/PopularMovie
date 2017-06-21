package com.thecodingjack.popularmovie.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by lamkeong on 6/20/2017.
 */

public final class NetworkUtil {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private final static String API_KEY = "";//get apikey from themoviedb.org
    private final static String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public static URL buildUrl(String input) {
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("api_key", API_KEY).build();
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

    public static String[]extractJSONData(String jsonString)throws JSONException{
        JSONObject movieJson = new JSONObject(jsonString);
        JSONArray movieArray = movieJson.getJSONArray("results");
        String [] parsedMovieData = new String[movieArray.length()];

        for(int i = 0; i<movieArray.length();i++){
            JSONObject movieObject = movieArray.getJSONObject(i);
            String movieTitle = movieObject.getString("title");
            String posterPath = POSTER_BASE_URL + movieObject.getString("poster_path");
            parsedMovieData[i]=posterPath +"," +movieTitle;


        }
        return parsedMovieData;
    }
}
