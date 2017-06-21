package com.thecodingjack.popularmovie;

import org.json.JSONObject;

/**
 * Created by lamkeong on 6/20/2017.
 */

public class Movies {
    private JSONObject fullMovieJsonData;
    private String movieTitle;
    private String posterURL;
    private String synopsis;
    private double rating;
    private String releasedDate;

    public Movies(JSONObject fullMovieJsonData, String movieTitle, String posterURL, String synopsis, double rating, String releasedDate) {
        this.fullMovieJsonData = fullMovieJsonData;
        this.movieTitle = movieTitle;
        this.posterURL = posterURL;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releasedDate = releasedDate;
    }

    public JSONObject getFullMovieJsonData() {
        return fullMovieJsonData;
    }

    public void setFullMovieJsonData(JSONObject fullMovieJsonData) {
        this.fullMovieJsonData = fullMovieJsonData;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }
}