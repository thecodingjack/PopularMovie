package com.thecodingjack.popularmovie.utilities;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static com.thecodingjack.popularmovie.utilities.NetworkUtil.REVIEW_PARAM;
import static com.thecodingjack.popularmovie.utilities.NetworkUtil.VIDEO_PARAM;

/**
 * Created by lamkeong on 6/29/2017.
 */

public final class MovieUtil {
    public static ArrayList<String> retrieveTrailer(int movieID){
        String movieIDStr = String.valueOf(movieID);
        URL url = NetworkUtil.buildUrl(movieIDStr,VIDEO_PARAM);
        try {
            String jsonResponse = NetworkUtil.getResponseFromHttp(url);
            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }
            JSONObject movieJson = new JSONObject(jsonResponse);
            JSONArray movieArray = movieJson.getJSONArray("results");
            ArrayList<String> trailerArray = new ArrayList<>();
            for (int i = 0; i<movieArray.length();i++ ){
                JSONObject trailerObj = movieArray.getJSONObject(i);
                String trailerKey = trailerObj.getString("key");
                trailerArray.add(trailerKey);
            }
            return trailerArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> retrieveReview(int movieID){
        String movieIDStr = String.valueOf(movieID);
        URL url = NetworkUtil.buildUrl(movieIDStr,REVIEW_PARAM);
        try {
            String jsonResponse = NetworkUtil.getResponseFromHttp(url);
            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }
            JSONObject movieJson = new JSONObject(jsonResponse);
            JSONArray movieArray = movieJson.getJSONArray("results");
            ArrayList<String[]> reviewArray = new ArrayList<>();
            for (int i = 0; i<movieArray.length();i++ ){
                JSONObject reviewObj = movieArray.getJSONObject(i);
                String author = reviewObj.getString("author");
                String content = reviewObj.getString("content");
                String[] review = {author,content};
                reviewArray.add(review);
            }
            return reviewArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
