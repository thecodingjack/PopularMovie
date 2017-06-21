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

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by lamkeong on 6/20/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private String[] mMovieNames;
    private String[] mMoviePosters;

    private final MovieClickListener listener;

    public MovieAdapter(MovieClickListener movieClickListener) {

        listener = movieClickListener;
    }

    public interface MovieClickListener{
        void onListItemClick(String movieDetails);
    };

    public void setMovieNames(String[] movieNames) {
        this.mMovieNames = movieNames;
        notifyDataSetChanged();
    }

    public void setMoviePosters(String[] moviePosters) {
        this.mMoviePosters = moviePosters;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView movieNameTextView;
        ImageView moviePoster;

        @Override
        public void onClick(View v) {
            String value = mMovieNames[getAdapterPosition()];
            listener.onListItemClick(value);
        }

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieNameTextView = (TextView) itemView.findViewById(R.id.tv_movie_name);
            moviePoster = (ImageView) itemView.findViewById(R.id.tv_movie_poster);

            itemView.setOnClickListener(this);
        }

        void bind(Context context, int position) {
            movieNameTextView.setText(mMovieNames[position]);
            Picasso.with(context).load(mMoviePosters[position]).into(moviePoster);
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
        holder.bind(context,position);
    }

    @Override
    public int getItemCount() {
        if (mMovieNames == null) return 0;
        return mMovieNames.length;
    }
}
