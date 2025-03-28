package com.example.moviesearchapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearchapp.MovieDetailsActivity;
import com.example.moviesearchapp.databinding.ItemMovieBinding;
import com.example.moviesearchapp.models.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movies;
    private final Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.binding.movieTitle.setText(movie.getTitle());
        holder.binding.movieYear.setText("Year: " + movie.getYear());
        holder.binding.movieRating.setText("Rating: " + movie.getRating());

        Picasso.get().load(movie.getPosterUrl()).into(holder.binding.moviePoster);

        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("rating", movie.getRating());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("posterUrl", movie.getPosterUrl());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("runtime", movie.getRuntime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
