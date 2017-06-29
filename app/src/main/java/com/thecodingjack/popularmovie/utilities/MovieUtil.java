package com.thecodingjack.popularmovie.utilities;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.thecodingjack.popularmovie.Movies;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;
import static com.thecodingjack.popularmovie.utilities.NetworkUtil.VIDEO_PARAM;

/**
 * Created by lamkeong on 6/29/2017.
 */

public final class MovieUtil {
    public static ArrayList<String> retrieveTrailer(int movieID){
        String movieIDStr = String.valueOf(movieID);
        URL url = NetworkUtil.buildUrl(movieIDStr,VIDEO_PARAM);

        Log.v("TEST","URL: " +url);
        try {
            String jsonResponse = NetworkUtil.getResponseFromHttp(url);
            Log.v ("TEST",jsonResponse);
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


            Log.v("TEST",Arrays.toString(trailerArray.toArray()));


            return trailerArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
