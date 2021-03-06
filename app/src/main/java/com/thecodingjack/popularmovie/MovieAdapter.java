package com.thecodingjack.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by lamkeong on 6/20/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movies> moviesList;

    private final MovieClickListener listener;

    public MovieAdapter(MovieClickListener movieClickListener, List<Movies> moviesList) {
        listener = movieClickListener;
    }

    public interface MovieClickListener {
        void onListItemClick(int movieID, String movieTitle, String posterUrl, String sypnosis, double rating, String releasedDate);
    }


    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;

        @Override
        public void onClick(View v) {
            Movies selectedMovie = moviesList.get(getAdapterPosition());
            int movieID = selectedMovie.getMovieID();
            String movieTitle = selectedMovie.getMovieTitle();
            String posterUrl = selectedMovie.getPosterURL();
            String sypnosis = selectedMovie.getSynopsis();
            double rating = selectedMovie.getRating();
            String releasedDate = selectedMovie.getReleasedDate();


            listener.onListItemClick(movieID,movieTitle, posterUrl, sypnosis, rating, releasedDate);
        }

        public MovieViewHolder(View itemView) {
            super(itemView);

            moviePoster = (ImageView) itemView.findViewById(R.id.tv_movie_poster);

            itemView.setOnClickListener(this);
        }

        void bind(Context context, int position) {
            Movies currentMovie = moviesList.get(position);
            Picasso.with(context).load(currentMovie.getPosterURL()).placeholder(R.drawable.loading_icon).error(R.drawable.error_icon).into(moviePoster);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        holder.bind(context, position);
    }

    @Override
    public int getItemCount() {
        if (moviesList == null) return 0;
        return moviesList.size();
    }
}
