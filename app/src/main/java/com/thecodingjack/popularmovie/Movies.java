package com.thecodingjack.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lamkeong on 6/20/2017.
 */

public class Movies implements Parcelable {
    private int movieID;
    private String movieTitle;
    private String posterURL;
    private String synopsis;
    private double rating;
    private String releasedDate;
    private String[] trailerKeyArray;

    public Movies(int movieID, String movieTitle, String posterURL, String synopsis, double rating, String releasedDate) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.posterURL = posterURL;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releasedDate = releasedDate;
    }

    public int getMovieID() {return movieID; }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return rating;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieID);
        dest.writeString(this.movieTitle);
        dest.writeString(this.posterURL);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.rating);
        dest.writeString(this.releasedDate);
    }

    protected Movies(Parcel in) {
        this.movieID = in.readInt();
        this.movieTitle = in.readString();
        this.posterURL = in.readString();
        this.synopsis = in.readString();
        this.rating = in.readDouble();
        this.releasedDate = in.readString();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
