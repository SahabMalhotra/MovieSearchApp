package com.example.moviesearchapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.moviesearchapp.adapters.MovieAdapter;
import com.example.moviesearchapp.databinding.ActivityMainBinding;
import com.example.moviesearchapp.models.Movie;
import com.example.moviesearchapp.viewmodels.MovieViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieViewModel movieViewModel;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        binding.movieRecyclerView.setAdapter(movieAdapter);


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovieLiveData().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                movieList.clear();
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No movies found!", Toast.LENGTH_SHORT).show();
            }
        });


        binding.searchButton.setOnClickListener(v -> {
            String title = binding.searchEditText.getText().toString().trim();
            if (!title.isEmpty()) {
                movieViewModel.fetchMovieDetails(title);
            } else {
                Toast.makeText(this, "Enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
